package IC.lir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import IC.UnaryOps;
import IC.AST.ArrayLocation;
import IC.AST.Assignment;
import IC.AST.Break;
import IC.AST.CallStatement;
import IC.AST.Continue;
import IC.AST.ExpressionBlock;
import IC.AST.Field;
import IC.AST.Formal;
import IC.AST.ICClass;
import IC.AST.If;
import IC.AST.Length;
import IC.AST.LibraryMethod;
import IC.AST.Literal;
import IC.AST.LocalVariable;
import IC.AST.LogicalBinaryOp;
import IC.AST.LogicalUnaryOp;
import IC.AST.MathBinaryOp;
import IC.AST.MathUnaryOp;
import IC.AST.Method;
import IC.AST.NewArray;
import IC.AST.NewClass;
import IC.AST.PrimitiveType;
import IC.AST.Program;
import IC.AST.Return;
import IC.AST.Statement;
import IC.AST.StatementsBlock;
import IC.AST.StaticCall;
import IC.AST.StaticMethod;
import IC.AST.This;
import IC.AST.UserType;
import IC.AST.VariableLocation;
import IC.AST.VirtualCall;
import IC.AST.VirtualMethod;
import IC.AST.Visitor;
import IC.AST.While;
import IC.Types.Type;
import IC.lir.Instructions.ArrayLengthInstr;
import IC.lir.Instructions.BinOpInstr;
import IC.lir.Instructions.CompareInstr;
import IC.lir.Instructions.Cond;
import IC.lir.Instructions.CondJumpInstr;
import IC.lir.Instructions.Immediate;
import IC.lir.Instructions.Instruction;
import IC.lir.Instructions.JumpInstr;
import IC.lir.Instructions.LabelInstr;
import IC.lir.Instructions.Memory;
import IC.lir.Instructions.MoveInstr;
import IC.lir.Instructions.Operator;
import IC.lir.Instructions.Reg;
import IC.lir.Instructions.ReturnInstr;
import IC.lir.Instructions.UnaryOpInstr;

public class TranslationVisitor implements Visitor{
	int target;
	int labels;
	StringLiterals stringLiterals;
	StringBuilder emitted;
    String _currentClass;
    
    //class layouts
    Map<ICClass, ClassLayout> classLayouts;
    
    //Instructions
    List<Instruction> instructions;
    
	// errors
    private boolean[] _hasErrors;
    private final String[] _errorStrings = {
            "Runtime error: Null pointer dereference!",
            "Runtime error: Array index out of bounds!",
            "Runtime error: Array allocation with negative array size!",
            "Runtime error: Division by zero!"
    };
    
    // registers
    //private Map<String, Integer> _registers;
    private Registers registers;
    private int _nextRegisterNum;
    //labels
    private Labels labelHandler;
    private Stack<String> _whileLabelStack;
    private Stack<String> _endWhileLabelStack;
	
	public TranslationVisitor() {
		this.target = 0;
		this.labels = 0;
		this.classLayouts = new HashMap<ICClass,ClassLayout>();
		this.stringLiterals = new StringLiterals();
		this.emitted = new StringBuilder();
		_hasErrors = new boolean[4];
		this.instructions = new ArrayList<Instruction>();
		this.registers = new Registers();
		this.labelHandler = new Labels();
		
		this._whileLabelStack = new Stack<String>();
		this._endWhileLabelStack = new Stack<String>();
	}
	
	public void printInstructions() {
		for (Instruction inst : instructions)
			System.out.println(inst.toString());
	}
	
	@Override
	public Object visit(Program program) {
		for (ICClass cls : program.getClasses()) {
			_currentClass = cls.getName();
			if (!(Boolean)cls.accept(this))
				return false;
		}
		return true;
	}

	@Override
	public Object visit(ICClass icClass) {
		ClassLayout cl = new ClassLayout();
		
		for (Field field : icClass.getFields()) {
			if(!(Boolean)field.accept(this))
				return false;
			cl.addField(field);
		}
		
		for (Method method : icClass.getMethods()) {
			if (!(Boolean)method.accept(this))
				return false;
			cl.addMethod(method);
		}
		
		classLayouts.put(icClass, cl);
		return true;
	}

	@Override
	public Object visit(Field field) {
		//System.out.println("1");
		return null;
	}

	@Override
	public Object visit(VirtualMethod method) {
		//System.out.println("2");
		return visitMethod(method);
	}

	@Override
	public Object visit(StaticMethod method) {
		//System.out.println("3");
		return visitMethod(method);
	}

	@Override
	public Object visit(LibraryMethod method) {
		//System.out.println("4");
		return true;
	}
	
	private Object visitMethod(Method method)
	{
		int startLine = target;

        // add method label
        String fullMethodName = getMethodName(_currentClass, method.getName());
        //emit(fullMethodName+":");
        instructions.add(new LabelInstr(labelHandler.request(fullMethodName+":")));
        
        // add new registers for this method
    //    _registers = new HashMap<>();
        _nextRegisterNum = 0;


        for (Formal formal : method.getFormals()) {
            formal.accept(this);
            target++;
        }

        // add all statements
        for (Statement stmt : method.getStatements()) {
            stmt.accept(this);
        }

        // if in non-returning function, add a dummy return
        if (method.doesHaveFlowWithoutReturn())
            //not sure about the value 
        	//emit("Return dummy");
        	instructions.add(new ReturnInstr(new Immediate(0)));

		return true;
	}

	@Override
	public Object visit(Formal formal) {
		instructions.add(new MoveInstr(new Memory(formal.getName()), registers.request(target)));
		return true;
	}

	@Override
	public Object visit(PrimitiveType type) {
		return null;
	}

	@Override
	public Object visit(UserType type) {
		return null;
	}

	@Override
	public Object visit(Assignment assignment) {
		assignment.getAssignment().accept(this);
		instructions.add(new MoveInstr(registers.request(target), new Memory(((VariableLocation)assignment.getVariable()).getName()))); //TODO: what if variable is external?
		return true;
	}

	@Override
	public Object visit(CallStatement callStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Return returnStatement) {
		returnStatement.getValue().accept(this);
		instructions.add(new ReturnInstr(registers.request(target)));
		return true;
	}

	@Override
	public Object visit(If ifStatement) {
		//System.out.println("bla "+ifStatement.getCondition()+"\n");
		ifStatement.getCondition().accept(this);
		labels++;
		int ifLabel = labels;
		instructions.add(new CompareInstr(new Immediate(1), registers.request(target)));
		String jumpingLabel = ifStatement.hasElse() ? "_false_label" : "_end_label";
		instructions.add(new CondJumpInstr(labelHandler.request(jumpingLabel+ifLabel), Cond.False));
		ifStatement.getOperation().accept(this);
		if (ifStatement.hasElse()) {
			instructions.add(new JumpInstr(labelHandler.request("_end_label"+ifLabel)));
			instructions.add(new LabelInstr(labelHandler.request("_false_label"+ifLabel)));
			ifStatement.getElseOperation().accept(this);
		}
		instructions.add(new LabelInstr(labelHandler.request("_end_label"+ifLabel)));
		
		return true;
	}

	@Override
	public Object visit(While whileStatement) {
		labels++;
		int whileLabel = labels;
		instructions.add(new LabelInstr(labelHandler.request("_test_label"+whileLabel)));
		whileStatement.getCondition().accept(this);
		instructions.add(new CompareInstr(new Immediate(1), registers.request(target)));
		instructions.add(new CondJumpInstr(labelHandler.request("_end_label"+whileLabel), Cond.False));
		
		this._whileLabelStack.add("_test_label"+whileLabel);
		this._endWhileLabelStack.add("_end_label"+whileLabel);
		whileStatement.getOperation().accept(this);
		this._whileLabelStack.pop();
		this._endWhileLabelStack.pop();
		instructions.add(new JumpInstr(labelHandler.request("_test_label"+whileLabel)));
		instructions.add(new LabelInstr(labelHandler.request("_end_label"+whileLabel)));
		return null;
	}

	@Override
	public Object visit(Break breakStatement) {
		instructions.add(new JumpInstr(labelHandler.request(this._endWhileLabelStack.lastElement())));
		return null;
	}

	@Override
	public Object visit(Continue continueStatement) {
		instructions.add(new JumpInstr(labelHandler.request(this._whileLabelStack.lastElement())));
		return null;
	}

	@Override
	public Object visit(StatementsBlock statementsBlock) {
		for(Statement stmnt : statementsBlock.getStatements())
			stmnt.accept(this);
		return null;
	}

	@Override
	public Object visit(LocalVariable localVariable) {
		if (localVariable.hasInitValue()) {
			localVariable.getInitValue().accept(this);
			instructions.add(new MoveInstr(registers.request(target), new Memory(localVariable.getName())));
		}
		// TODO what if there isn't an init value???
		return null;
	}

	@Override
	public Object visit(VariableLocation location) {
		if (location.isExternal()) {
			// TODO ???
		}
		instructions.add(new MoveInstr(new Memory(location.getName()), registers.request(target)));
		return null;
	}

	@Override
	public Object visit(ArrayLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(StaticCall call) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(VirtualCall call) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(This thisExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(NewClass newClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(NewArray newArray) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Length length) {
		target++;
		length.getArray().accept(this);
		target--;
		//emit("ArrayLength R"+(target+1)+",R"+target);
		instructions.add(new ArrayLengthInstr(registers.request(target+1), registers.request(target)));
		return null;
	}

	@Override
	public Object visit(MathBinaryOp binaryOp) {
		binaryOp.getFirstOperand().accept(this);
		target++;
		binaryOp.getSecondOperand().accept(this);
		//String instruction="";
		Operator op;
		switch(binaryOp.getOperator()) {
		case PLUS: //TODO: what about __stringCat ?
			//instruction="Add";
			op = Operator.ADD;
			break;
		case MINUS:
			//instruction="Sub";
			op = Operator.SUB;
			break;
		case DIVIDE:
			//instruction="Div";
			op = Operator.DIV;
			break;
		case MULTIPLY:
			//instruction="Mul";
			op = Operator.MUL;
			break;
		case MOD:
			//instruction="Mod";
			op = Operator.MOD;
			break;
		default:
			return false;
		}
		//emit(instruction+" R"+target+",R"+(--target));
		instructions.add(new BinOpInstr(registers.request(target), registers.request(--target), op));
		return true;
	}

	@Override
	public Object visit(LogicalBinaryOp binaryOp) {
		
		//target++;
		//binaryOp.getSecondOperand().accept(this);
		labels++;
		switch(binaryOp.getOperator()) {
		case GT:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare R,"+target+",R"+(--target));
			instructions.add(new CompareInstr(registers.request(target), registers.request(--target)));
			//emit("JumpG _true_label"+labels);
			instructions.add(new CondJumpInstr(labelHandler.request("_true_label"+labels), Cond.G));
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			//emit("Jump _end_label"+labels);
			instructions.add(new JumpInstr(labelHandler.request("_end_label"+labels)));
			emit("_true_label"+labels);
			instructions.add(new LabelInstr(labelHandler.request("_true_label"+labels)));
			emit("Move 1,R"+target);
			instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			//emit("_end_label"+labels);
			instructions.add(new LabelInstr(labelHandler.request("_end_label"+labels)));
			break;
		case GTE:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare R,"+target+",R"+(--target));
			instructions.add(new CompareInstr(registers.request(target), registers.request(--target)));
			//emit("JumpGE _true_label"+labels);
			instructions.add(new CondJumpInstr(labelHandler.request("_true_label"+labels), Cond.GE));
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			//emit("Jump _end_label"+labels);
			instructions.add(new JumpInstr(labelHandler.request("_end_label"+labels)));
			//emit("_true_label"+labels);
			instructions.add(new LabelInstr(labelHandler.request("_true_label"+labels)));
			//emit("Move 1,R"+target);
			instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			//emit("_end_label"+labels);
			instructions.add(new LabelInstr(labelHandler.request("_end_label"+labels)));
			break;
		case EQUAL:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare R,"+target+",R"+(--target));
			instructions.add(new CompareInstr(registers.request(target), registers.request(--target)));
			//emit("JumpFalse _true_label"+labels);
			instructions.add(new CondJumpInstr(labelHandler.request("_true_label"+labels), Cond.False));
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			//emit("Jump _end_label"+labels);
			instructions.add(new JumpInstr(labelHandler.request("_end_label"+labels)));
			//emit("_true_label"+labels);
			instructions.add(new LabelInstr(labelHandler.request("_true_label"+labels)));
			//emit("Move 1,R"+target);
			instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			//emit("_end_label"+labels);
			instructions.add(new LabelInstr(labelHandler.request("_end_label"+labels)));
			break;
		case NEQUAL:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare R,"+target+",R"+(--target));
			instructions.add(new CompareInstr(registers.request(target), registers.request(--target)));
			//emit("JumpFalse _false_label"+labels);
			instructions.add(new CondJumpInstr(labelHandler.request("_false_label"+labels), Cond.False));
			//emit("Move 1,R"+target);
			instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			//emit("Jump _end_label"+labels);
			instructions.add(new JumpInstr(labelHandler.request("_end_label"+labels)));
			//emit("_false_label"+labels);
			instructions.add(new LabelInstr(labelHandler.request("_false_label"+labels)));
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			//emit("_end_label"+labels);
			instructions.add(new LabelInstr(labelHandler.request("_end_label"+labels)));
			break;
		case LT:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare R,"+target+",R"+(--target));
			instructions.add(new CompareInstr(registers.request(target), registers.request(--target)));
			//emit("JumpL _true_label"+labels);
			instructions.add(new CondJumpInstr(labelHandler.request("_true_label"+labels), Cond.L));
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			//emit("Jump _end_label"+labels);
			instructions.add(new JumpInstr(labelHandler.request("_end_label"+labels)));
			//emit("_true_label"+labels);
			instructions.add(new LabelInstr(labelHandler.request("_true_label"+labels)));
			//emit("Move 1,R"+target);
			instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			//emit("_end_label"+labels);
			instructions.add(new LabelInstr(labelHandler.request("_end_label"+labels)));
			break;
		case LTE:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare R,"+target+",R"+(--target));
			instructions.add(new CompareInstr(registers.request(target), registers.request(--target)));
			//emit("JumpLE _true_label"+labels);
			instructions.add(new CondJumpInstr(labelHandler.request("_true_label"+labels), Cond.LE));
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			//emit("Jump _end_label"+labels);
			instructions.add(new JumpInstr(labelHandler.request("_end_label"+labels)));;
			//emit("_true_label"+labels);
			instructions.add(new LabelInstr(labelHandler.request("_true_label"+labels)));
			//emit("Move 1,R"+target);
			instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			//emit("_end_label"+labels);
			instructions.add(new LabelInstr(labelHandler.request("_end_label"+labels)));
			break;
		case LAND:
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare 0,R"+target);
			//emit("JumpTrue _end_label"+labels);
			instructions.add(new CompareInstr(new Immediate(0), registers.request(target)));
			instructions.add(new CondJumpInstr(labelHandler.request("_end_label"+labels), Cond.True));
			target++;
			binaryOp.getSecondOperand().accept(this);
			//emit("And R"+target+",R"+(--target));
			//emit("_end_label"+labels);
			instructions.add(new BinOpInstr(registers.request(target), registers.request(--target), Operator.AND));
			break;
		case LOR:
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare 1,R"+target);
			//emit("JumpTrue _end_label"+labels);
			instructions.add(new CompareInstr(new Immediate(1), registers.request(target)));
			instructions.add(new CondJumpInstr(labelHandler.request("_end_label"+labels), Cond.True));
			target++;
			binaryOp.getSecondOperand().accept(this);
			//emit("Or R"+target+",R"+(--target));
			//emit("_end_label"+labels);
			instructions.add(new BinOpInstr(registers.request(target), registers.request(--target), Operator.OR));
			instructions.add(new LabelInstr(labelHandler.request("_end_label"+labels)));
			break;
		default:
			
		}
		

		return true;
	}

	@Override
	public Object visit(MathUnaryOp unaryOp) {
		if (unaryOp.getOperator() == UnaryOps.UMINUS) {
			unaryOp.getOperand().accept(this);
			//emit("Mult -1,R"+target);
			instructions.add(new UnaryOpInstr(registers.request(target), Operator.NEG));
			return true;
		}
			
		return false;
	}

	@Override
	public Object visit(LogicalUnaryOp unaryOp) {
		if(unaryOp.getOperator() == UnaryOps.LNEG) {
			//target++;
			unaryOp.getOperand().accept(this);
			//target--;
			//emit("Move 1,R"+target);
			//emit("Sub R"+(target+1)+",R"+target);
			//instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			//instructions.add(new BinOpInstr(registers.request(target+1), registers.request(target), Operator.NEG)); // TODO unaryOPinst?
			instructions.add(new UnaryOpInstr(registers.request(target), Operator.NOT));
			return true;
		}
		return false;
	}

	@Override
	public Object visit(Literal literal) {
		switch(literal.getType()) {
		case STRING:
			//emit("Move str"+stringLiterals.add((String)literal.getValue())+",R"+target); //catelog the string and emit it
			instructions.add(new MoveInstr(new Memory("str"+stringLiterals.add((String)literal.getValue())), registers.request(target)));
			break;
		case INTEGER:
			//emit("Move "+((Integer)literal.getValue())+",R"+target);
			instructions.add(new MoveInstr(new Immediate((Integer)literal.getValue()), registers.request(target)));
			break;
		case TRUE:
			//emit("Move 1,R"+target);
			instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			break;
		case FALSE:
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			break;
		case NULL:
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			break;
		default:
			
		}
		return null; // TODO - return null or boolean?
	}

	@Override
	public Object visit(ExpressionBlock expressionBlock) {
		if (!(Boolean)expressionBlock.getExpression().accept(this))
			return false;
		return true;
	}
	
	public void emit(String s) {
		emitted.append(s+"\n");
	}
	
    private String getMethodName(String className, String name) {
        if (name.equals("main"))
            return "_ic_main";

        if (className.equals("Library"))
            return name;

        return className + "_" + name;
    }
    
    public String getEmissionString()
    {
    	return this.emitted.toString();
    }

}

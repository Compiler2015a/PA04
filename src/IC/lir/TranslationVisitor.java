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
import IC.lir.Instructions.Immediate;
import IC.lir.Instructions.Instruction;
import IC.lir.Instructions.Label;
import IC.lir.Instructions.MoveInstr;
import IC.lir.Instructions.Reg;

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
    private Map<String, Integer> _registers;
    private int _nextRegisterNum;
    //labels
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
        emit(fullMethodName+":");

        // add new registers for this method
        _registers = new HashMap<>();
        _nextRegisterNum = 0;


        for (Formal formal : method.getFormals()) {
            formal.accept(this);
        }

        // add all statements
        for (Statement stmt : method.getStatements()) {
            stmt.accept(this);
        }

        // if in non-returning function, add a dummy return
        if (method.doesHaveFlowWithoutReturn())
            //not sure about the value 
        	emit("Return dummy");

		return true;
	}

	@Override
	public Object visit(Formal formal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(PrimitiveType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(UserType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Assignment assignment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(CallStatement callStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Return returnStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(If ifStatement) {
		//System.out.println("bla "+ifStatement.getCondition()+"\n");
		if (!(Boolean)ifStatement.getCondition().accept(this))
			return false;
		Type typeCondition = ifStatement.getCondition().getEntryType();
		emit("R"+target+" := "+ typeCondition);
		emit("Compare 0,R"+target);
		
		String endIfLabel="_end_label"+labels++;
		if (ifStatement.hasElse()) 
		{
			String elseLabel ="_false_label"+labels++;
			emit("JumpTrue "+elseLabel);
			// print "then"
	        ifStatement.getOperation().accept(this);
	        emit("Jump "+endIfLabel);
	        emit(elseLabel+":");
	        // print else
		}
		else
		{
			emit("JumpTrue "+endIfLabel);
			// print "then"
	        ifStatement.getOperation().accept(this);
		}
		// end-if
		emit(endIfLabel+":");
		return true;
	}

	@Override
	public Object visit(While whileStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Break breakStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Continue continueStatement) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(VariableLocation location) {
		// TODO Auto-generated method stub
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
		emit("ArrayLength R"+(target+1)+",R"+target);
		return null;
	}

	@Override
	public Object visit(MathBinaryOp binaryOp) {
		binaryOp.getFirstOperand().accept(this);
		target++;
		binaryOp.getSecondOperand().accept(this);
		String instruction="";
		switch(binaryOp.getOperator()) {
		case PLUS: //TODO: what about __stringCat ?
			instruction="Add";
			break;
		case MINUS:
			instruction="Sub";
			break;
		case DIVIDE:
			instruction="Div";
			break;
		case MULTIPLY:
			instruction="Mul";
			break;
		case MOD:
			instruction="Mod";
			break;
		default:
			
		}
		emit(instruction+" R"+target+",R"+(--target));
		return true;
	}

	@Override
	public Object visit(LogicalBinaryOp binaryOp) {
		
		//target++;
		//binaryOp.getSecondOperand().accept(this);
		
		switch(binaryOp.getOperator()) {
		case GT:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			emit("Compare R,"+target+",R"+(--target));
			emit("JumpG _true_label"+labels);
			emit("Move 0,R"+target);
			emit("Jump _end_label"+labels);
			emit("_true_label"+labels);
			emit("Move 1,R"+target);
			emit("_end_label"+labels);
			labels++;
			break;
		case GTE:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			emit("Compare R,"+target+",R"+(--target));
			emit("JumpGE _true_label"+labels);
			emit("Move 0,R"+target);
			emit("Jump _end_label"+labels);
			emit("_true_label"+labels);
			emit("Move 1,R"+target);
			emit("_end_label"+labels);
			labels++;
			break;
		case EQUAL:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			emit("Compare R,"+target+",R"+(--target));
			emit("JumpFalse _true_label"+labels);
			emit("Move 0,R"+target);
			emit("Jump _end_label"+labels);
			emit("_true_label"+labels);
			emit("Move 1,R"+target);
			emit("_end_label"+labels);
			labels++;
			break;
		case NEQUAL:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			emit("Compare R,"+target+",R"+(--target));
			emit("JumpFalse _false_label"+labels);
			emit("Move 1,R"+target);
			emit("Jump _end_label"+labels);
			emit("_false_label"+labels);
			emit("Move 0,R"+target);
			emit("_end_label"+labels);
			labels++;
			break;
		case LT:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			emit("Compare R,"+target+",R"+(--target));
			emit("JumpL _true_label"+labels);
			emit("Move 0,R"+target);
			emit("Jump _end_label"+labels);
			emit("_true_label"+labels);
			emit("Move 1,R"+target);
			emit("_end_label"+labels);
			labels++;
			break;
		case LTE:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			emit("Compare R,"+target+",R"+(--target));
			emit("JumpLE _true_label"+labels);
			emit("Move 0,R"+target);
			emit("Jump _end_label"+labels);
			emit("_true_label"+labels);
			emit("Move 1,R"+target);
			emit("_end_label"+labels);
			labels++;
			break;
		case LAND:
			binaryOp.getFirstOperand().accept(this);
			emit("Compare 0,R"+target);
			emit("JumpTrue _end_label"+labels);
			target++;
			binaryOp.getSecondOperand().accept(this);
			emit("And R"+target+",R"+(--target));
			emit("_end_label"+labels);
			labels++;
			break;
		case LOR:
			binaryOp.getFirstOperand().accept(this);
			emit("Compare 1,R"+target);
			emit("JumpTrue _end_label"+labels);
			target++;
			binaryOp.getSecondOperand().accept(this);
			emit("Or R"+target+",R"+(--target));
			emit("_end_label"+labels);
			labels++;
			break;
		default:
			
		}
		

		return true;
	}

	@Override
	public Object visit(MathUnaryOp unaryOp) {
		if (unaryOp.getOperator() == UnaryOps.UMINUS) {
			unaryOp.getOperand().accept(this);
			emit("Mult -1,R"+target);
			return true;
		}
			
		return false;
	}

	@Override
	public Object visit(LogicalUnaryOp unaryOp) {
		if(unaryOp.getOperator() == UnaryOps.LNEG) {
			target++;
			unaryOp.getOperand().accept(this);
			target--;
			emit("Move 1,R"+target);
			emit("Sub R"+(target-1)+",R"+target);
			return true;
		}
		return false;
	}

	@Override
	public Object visit(Literal literal) {
		switch(literal.getType()) {
		case STRING:
			emit("Move str"+stringLiterals.add((String)literal.getValue())+",R"+target); //catelog the string and emit it
			instructions.add(new MoveInstr(new Label("str"+stringLiterals.add((String)literal.getValue())), new Reg("R"+target)));
			break;
		case INTEGER:
			//emit("Move "+((Integer)literal.getValue())+",R"+target);
			instructions.add(new MoveInstr(new Immediate((Integer)literal.getValue()), new Reg("R"+target)));
			break;
		case TRUE:
			//emit("Move 1,R"+target);
			instructions.add(new MoveInstr(new Immediate(1), new Reg("R"+target)));
			break;
		case FALSE:
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), new Reg("R"+target)));
			break;
		case NULL:
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), new Reg("R"+target)));
			break;
		default:
			
		}
		return null;
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

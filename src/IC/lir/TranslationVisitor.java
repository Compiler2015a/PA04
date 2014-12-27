package IC.lir;

import java.util.Map;

import IC.AST.*;
import IC.Types.Type;
import IC.BinaryOps;

public class TranslationVisitor implements Visitor{
	int target;
	int labels;
	ClassLayout classLayout;
	StringLiterals stringLiterals;
	StringBuilder emitted;
    
	// errors
    private boolean[] _hasErrors;
    private final String[] _errorStrings = {
            "Runtime error: Null pointer dereference!",
            "Runtime error: Array index out of bounds!",
            "Runtime error: Array allocation with negative array size!",
            "Runtime error: Division by zero!"
    };
	
	public TranslationVisitor() {
		this.target = 0;
		this.labels = 0;
		this.classLayout = new ClassLayout();
		this.stringLiterals = new StringLiterals();
		this.emitted = new StringBuilder();
		_hasErrors = new boolean[4];
	}
	
	@Override
	public Object visit(Program program) {
		for (ICClass cls : program.getClasses()) {
			if (!(Boolean)cls.accept(this))
				return false;
		}
		return true;
	}

	@Override
	public Object visit(ICClass icClass) {
		for (Method method : icClass.getMethods()) 
			if (!(Boolean)method.accept(this))
				return false;
		
		return true;
	}

	@Override
	public Object visit(Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(VirtualMethod method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(StaticMethod method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(LibraryMethod method) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
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
		

		return null;
	}

	@Override
	public Object visit(MathUnaryOp unaryOp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(LogicalUnaryOp unaryOp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Literal literal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ExpressionBlock expressionBlock) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void emit(String s) {
		emitted.append(s+"\n");
	}

}

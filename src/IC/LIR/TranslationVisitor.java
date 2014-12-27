package IC.lir;

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
import IC.BinaryOps;

public class TranslationVisitor implements Visitor{
	int target;
	int labels;
	ClassLayout classLayout;
	StringLiterals stringLiterals;
	StringBuilder emitted;
	
	public TranslationVisitor() {
		this.target = 0;
		this.labels = 0;
		this.classLayout = new ClassLayout();
		this.stringLiterals = new StringLiterals();
		this.emitted = new StringBuilder();
	}
	
	@Override
	public Object visit(Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ICClass icClass) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		
		switch(binaryOp.getOperator()) {
		case PLUS: //TODO: what about __stringCat ?
			emit("Add R"+target+",R"+(--target));
			break;
		case MINUS:
			emit("Sub R"+target+",R"+(--target));
			break;
		case DIVIDE:
			emit("Div R"+target+",R"+(--target));
			break;
		case MULTIPLY:
			emit("Mul R"+target+",R"+(--target));
			break;
		case MOD:
			emit("Mod R"+target+",R"+(--target));
			break;
		default:
			
		}
		
		return null;
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

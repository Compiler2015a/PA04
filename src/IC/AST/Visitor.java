package IC.AST;

/**
 * AST visitor interface. Declares methods for visiting each type of AST node.
 * 
 * @author Tovi Almozlino
 */
public interface Visitor {

	public Object visit(Program program);

	public Object visit(ICClass icClass);

	public Object visit(Field field);

	public Object visit(VirtualMethod method);

	public Object visit(StaticMethod method);

	public Object visit(LibraryMethod method);

	public Object visit(Formal formal);

	public Object visit(PrimitiveType type);

	public Object visit(UserType type);

	public Object visit(Assignment assignment);

	public Object visit(CallStatement callStatement);

	public Object visit(Return returnStatement);

	public Object visit(If ifStatement);

	public Object visit(While whileStatement);

	public Object visit(Break breakStatement);

	public Object visit(Continue continueStatement);

	public Object visit(StatementsBlock statementsBlock);

	public Object visit(LocalVariable localVariable);

	public Object visit(VariableLocation location);

	public Object visit(ArrayLocation location);

	public Object visit(StaticCall call);

	public Object visit(VirtualCall call);

	public Object visit(This thisExpression);

	public Object visit(NewClass newClass);

	public Object visit(NewArray newArray);

	public Object visit(Length length);

	public Object visit(MathBinaryOp binaryOp);

	public Object visit(LogicalBinaryOp binaryOp);

	public Object visit(MathUnaryOp unaryOp);

	public Object visit(LogicalUnaryOp unaryOp);

	public Object visit(Literal literal);

	public Object visit(ExpressionBlock expressionBlock);
}

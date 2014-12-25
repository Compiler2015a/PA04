package IC.AST;

/**
 * While statement AST node.
 * 
 * @author Tovi Almozlino
 */
public class While extends Statement {

	private Expression condition;

	private Statement operation;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a While statement node.
	 * 
	 * @param condition
	 *            Condition of the While statement.
	 * @param operation
	 *            Operation to perform while condition is true.
	 */
	public While(Expression condition, Statement operation) {
		super(condition.getLine());
		this.condition = condition;
		this.operation = operation;
	}
	
	public Expression getCondition() {
		return condition;
	}

	public Statement getOperation() {
		return operation;
	}

}

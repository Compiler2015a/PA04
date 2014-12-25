package IC.AST;

/**
 * Return statement AST node.
 * 
 * @author Tovi Almozlino
 */
public class Return extends Statement {

	private Expression value = null;
	private IC.Types.Type methodType;
	
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new return statement node, with no return value.
	 * 
	 * @param line
	 *            Line number of return statement.
	 */
	public Return(int line) {
		super(line);
	}

	/**
	 * Constructs a new return statement node.
	 * 
	 * @param line
	 *            Line number of return statement.
	 * @param value
	 *            Return value.
	 */
	public Return(int line, Expression value) {
		this(line);
		this.value = value;
	}

	public boolean hasValue() {
		return (value != null);
	}

	public Expression getValue() {
		return value;
	}

	public IC.Types.Type getMethodType() {
		return methodType;
	}

	public void setMethodType(IC.Types.Type methodType) {
		this.methodType = methodType;
	}
	
	
}

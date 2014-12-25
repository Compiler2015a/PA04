package IC.AST;

/**
 * Local variable declaration statement AST node.
 * 
 * @author Tovi Almozlino
 */
public class LocalVariable extends Statement {

	private Type type;

	private String name;

	private Expression initValue = null;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new local variable declaration statement node.
	 * 
	 * @param type
	 *            Data type of local variable.
	 * @param name
	 *            Name of local variable.
	 */
	public LocalVariable(Type type, String name) {
		super(type.getLine());
		this.type = type;
		this.name = name;
	}

	/**
	 * Constructs a new local variable declaration statement node, with an
	 * initial value.
	 * 
	 * @param type
	 *            Data type of local variable.
	 * @param name
	 *            Name of local variable.
	 * @param initValue
	 *            Initial value of local variable.
	 */
	public LocalVariable(Type type, String name, Expression initValue) {
		this(type, name);
		this.initValue = initValue;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public boolean hasInitValue() {
		return (initValue != null);
	}

	public Expression getInitValue() {
		return initValue;
	}

}

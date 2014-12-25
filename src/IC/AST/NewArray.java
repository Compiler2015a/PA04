package IC.AST;

/**
 * Array creation AST node.
 * 
 * @author Tovi Almozlino
 */
public class NewArray extends New {

	private Type type;

	private Expression size;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new array creation expression node.
	 * 
	 * @param type
	 *            Data type of new array.
	 * @param size
	 *            Size of new array.
	 */
	public NewArray(Type type, Expression size) {
		super(type.getLine());
		this.type = type;
		this.size = size;
	}

	public Type getType() {
		return type;
	}

	public Expression getSize() {
		return size;
	}

}

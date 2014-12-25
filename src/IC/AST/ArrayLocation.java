package IC.AST;

/**
 * Array reference AST node.
 * 
 * @author Tovi Almozlino
 */
public class ArrayLocation extends Location {

	private Expression array;

	private Expression index;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new array reference node.
	 * 
	 * @param array
	 *            Expression representing an array.
	 * @param index
	 *            Expression representing a numeric index.
	 */
	public ArrayLocation(Expression array, Expression index) {
		super(array.getLine());
		this.array = array;
		this.index = index;
	}

	public Expression getArray() {
		return array;
	}

	public Expression getIndex() {
		return index;
	}
}
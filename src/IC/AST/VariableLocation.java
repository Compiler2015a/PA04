package IC.AST;

/**
 * Variable reference AST node.
 * 
 * @author Tovi Almozlino
 */
public class VariableLocation extends Location {

	private Expression location = null;

	private String name;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new variable reference node.
	 * 
	 * @param line
	 *            Line number of reference.
	 * @param name
	 *            Name of variable.
	 */
	public VariableLocation(int line, String name) {
		super(line);
		this.name = name;
	}

	/**
	 * Constructs a new variable reference node, for an external location.
	 * 
	 * @param line
	 *            Line number of reference.
	 * @param location
	 *            Location of variable.
	 * @param name
	 *            Name of variable.
	 */
	public VariableLocation(int line, Expression location, String name) {
		this(line, name);
		this.location = location;
	}

	public boolean isExternal() {
		return (location != null);
	}

	public Expression getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

}

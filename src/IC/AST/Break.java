package IC.AST;

/**
 * Break statement AST node.
 * 
 * @author Tovi Almozlino
 */
public class Break extends Statement {

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a break statement node.
	 * 
	 * @param line
	 *            Line number of break statement.
	 */
	public Break(int line) {
		super(line);
	}

}

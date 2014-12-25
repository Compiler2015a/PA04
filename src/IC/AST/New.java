package IC.AST;

/**
 * Abstract base class for object creation AST nodes.
 * 
 * @author Tovi Almozlino
 */
public abstract class New extends Expression {

	/**
	 * Constructs a new object creation expression node. Used by subclasses.
	 * 
	 * @param line
	 *            Line number of expression.
	 */
	protected New(int line) {
		super(line);
	}

}

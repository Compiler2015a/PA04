package IC.AST;

import java.util.List;

/**
 * Virtual method AST node.
 * 
 * @author Tovi Almozlino
 */
public class VirtualMethod extends Method {

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new virtual method node.
	 * 
	 * @param type
	 *            Data type returned by method.
	 * @param name
	 *            Name of method.
	 * @param formals
	 *            List of method parameters.
	 * @param statements
	 *            List of method's statements.
	 */
	public VirtualMethod(Type type, String name, List<Formal> formals,
			List<Statement> statements) {
		super(type, name, formals, statements);
	}

}

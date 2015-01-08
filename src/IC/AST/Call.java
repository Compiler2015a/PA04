package IC.AST;

import java.util.List;

/**
 * Abstract base class for method call AST nodes.
 * 
 * @author Tovi Almozlino
 */
public abstract class Call extends Expression {

	private String name;
	private List<Expression> arguments;
	private IC.Types.Type methodType;
	/**
	 * Constructs a new method call node. Used by subclasses.
	 * 
	 * @param line
	 *            Line number of call.
	 * @param name
	 *            Name of method.
	 * @param arguments
	 *            List of all method arguments.
	 */
	protected Call(int line, String name, List<Expression> arguments) {
		super(line);
		this.name = name;
		this.arguments = arguments;
	}

	public String getName() {
		return name;
	}

	public List<Expression> getArguments() {
		return arguments;
	}

	public IC.Types.Type getMethodType() {
		return methodType;
	}

	public void setMethodType(IC.Types.Type methodTtype) {
		this.methodType = methodTtype;
	}

}

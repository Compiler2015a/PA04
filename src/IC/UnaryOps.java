package IC;
/**
 * Enum of the IC language's unary operators.
 * 
 * @author Tovi Almozlino
 */
public enum UnaryOps {

	UMINUS("-", "unary subtraction"), 
	LNEG("!", "logical negation");

	private String operator;

	private String description;

	private UnaryOps(String operator, String description) {
		this.operator = operator;
		this.description = description;
	}

	/**
	 * Returns a string representation of the operator.
	 * 
	 * @return The string representation.
	 */
	public String getOperatorString() {
		return operator;
	}

	/**
	 * Returns a description of the operator.
	 * 
	 * @return The description.
	 */
	public String getDescription() {
		return description;
	}
}
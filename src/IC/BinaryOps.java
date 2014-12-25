package IC;
/**
 * Enum of the IC language's binary operators.
 * 
 * @author Tovi Almozlino
 */
public enum BinaryOps {

	PLUS("+", "addition"),
	MINUS("-", "subtraction"),
	MULTIPLY("*", "multiplication"),
	DIVIDE("/", "division"),
	MOD("%", "modulo"),
	LAND("&&", "logical and"),
	LOR("||", "logical or"),
	LT("<", "less than"),
	LTE("<=", "less than or equal to"),
	GT(">", "greater than"),
	GTE(">=", "greater than or equal to"),
	EQUAL("==", "equality"),
	NEQUAL("!=", "inequality");
	
	private String operator;
	
	private String description;

	private BinaryOps(String operator, String description) {
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
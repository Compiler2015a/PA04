package IC.AST;

import IC.UnaryOps;

/**
 * Abstract base class for unary operation AST nodes.
 * 
 * @author Tovi Almozlino
 */
public abstract class UnaryOp extends Expression {

	private UnaryOps operator;

	private Expression operand;

	/**
	 * Constructs a new unary operation node. Used by subclasses.
	 * 
	 * @param operator
	 *            The operator.
	 * @param operand
	 *            The operand.
	 */
	protected UnaryOp(UnaryOps operator, Expression operand) {
		super(operand.getLine());
		this.operator = operator;
		this.operand = operand;
	}

	public UnaryOps getOperator() {
		return operator;
	}

	public Expression getOperand() {
		return operand;
	}

}

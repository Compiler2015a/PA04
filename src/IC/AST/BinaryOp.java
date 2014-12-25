package IC.AST;

import IC.BinaryOps;

/**
 * Abstract base class for binary operation AST nodes.
 * 
 * @author Tovi Almozlino
 */
public abstract class BinaryOp extends Expression {

	private Expression operand1;

	private BinaryOps operator;

	private Expression operand2;

	/**
	 * Constructs a new binary operation node. Used by subclasses.
	 * 
	 * @param operand1
	 *            The first operand.
	 * @param operator
	 *            The operator.
	 * @param operand2
	 *            The second operand.
	 */
	protected BinaryOp(Expression operand1, BinaryOps operator,
			Expression operand2) {
		super(operand1.getLine());
		this.operand1 = operand1;
		this.operator = operator;
		this.operand2 = operand2;
	}

	public BinaryOps getOperator() {
		return operator;
	}

	public Expression getFirstOperand() {
		return operand1;
	}

	public Expression getSecondOperand() {
		return operand2;
	}

}

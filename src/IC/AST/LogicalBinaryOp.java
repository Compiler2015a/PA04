package IC.AST;

import IC.BinaryOps;

/**
 * Logical binary operation AST node.
 * 
 * @author Tovi Almozlino
 */
public class LogicalBinaryOp extends BinaryOp {

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new logical binary operation node.
	 * 
	 * @param operand1
	 *            The first operand.
	 * @param operator
	 *            The operator.
	 * @param operand2
	 *            The second operand.
	 */
	public LogicalBinaryOp(Expression operand1, BinaryOps operator,
			Expression operand2) {
		super(operand1, operator, operand2);
	}

}

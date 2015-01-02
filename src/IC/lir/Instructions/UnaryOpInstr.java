package IC.lir.Instructions;

/** A unary operation instruction.
 */
public class UnaryOpInstr extends Instruction {
	public final Operand dst;
	public final Operator op;
	
	public UnaryOpInstr( Operand dst, Operator op) {
		this.dst = dst;
		this.op = op;
	}
	
	public String toString() {
		return op + " " + dst;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
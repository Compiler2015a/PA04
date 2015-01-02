package IC.lir.Instructions;

/** A binary operation instruction.
 */
public class BinOpInstr extends Instruction {
	public final Operand src;
	public final Operand dst;
	public final Operator op;
	
	public BinOpInstr(Operand src, Operand dst, Operator op) {
		this.src = src;
		this.dst = dst;
		this.op = op;
	}
	
	public String toString() {
		return op + " " + src + "," + dst;
	}
	
	public void accept(Visitor v) {
		v.visit(this);		
	}
}
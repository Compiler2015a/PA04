package IC.lir.Instructions;

/** A move instruction instruction.
 */
public class MoveInstr extends Instruction {
	public final Operand src;
	public final Operand dst;
	
	public MoveInstr(Operand src, Operand dst) {
		this.src = src;
		this.dst = dst;
		if (src instanceof Memory && dst instanceof Memory) {
			throw new RuntimeException("Encountered " + this + " with two memory operands!");
		}
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
	
	public String toString() {
		return "Move " + src + "," + dst;
	}
}
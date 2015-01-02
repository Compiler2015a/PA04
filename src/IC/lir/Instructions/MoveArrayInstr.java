package IC.lir.Instructions;

/** A move array instruction.
 */
public class MoveArrayInstr extends Instruction {
	public final Operand base;
	public final Operand offset;
	public final Operand mem;
	public final boolean isLoad;
	
	public MoveArrayInstr(Operand base, Operand offset, Operand mem, boolean isLoad) {
		this.base = base;
		this.offset = offset;
		this.mem = mem;
		this.isLoad = isLoad;
	}
	
	public String toString() {
		if (isLoad) {
			return "MoveArray " + base + "[" + offset + "]," + mem;
		}
		else {
			return "MoveArray " + mem + "," + base + "[" + offset + "]";	
		}
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
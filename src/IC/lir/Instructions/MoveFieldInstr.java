package IC.lir.Instructions;

/** A move field instruction.
 */
public class MoveFieldInstr extends Instruction {
	public final Operand base;
	public final Operand offset;
	public final Operand mem;
	public final boolean isLoad;
	
	public MoveFieldInstr(Operand base, Operand offset, Operand mem, boolean isLoad) {
		this.base = base;
		this.offset = offset;
		this.mem = mem;
		this.isLoad = isLoad;
	}

	public String toString() {
		if (isLoad) {
			return "MoveField " + base + "." + offset + "," + mem;
		}
		else {
			return "MoveField " + mem + "," + base + "." + offset;	
		}
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
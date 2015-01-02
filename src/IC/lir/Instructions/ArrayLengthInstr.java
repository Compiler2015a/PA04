package IC.lir.Instructions;

/** An ArrayLength instruction that retrieves the length
 * of an array or a string.
 */
public class ArrayLengthInstr extends Instruction {
	public final Operand arr;
	public final Reg dst;
	
	public ArrayLengthInstr(Operand arr, Reg dst) {
		this.arr = arr;
		this.dst = dst;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
	
	public String toString() {
		return "ArrayLength " + arr + "," + dst;
	}
}
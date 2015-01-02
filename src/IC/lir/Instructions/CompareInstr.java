package IC.lir.Instructions;

/** A compare instruction.
 */
public class CompareInstr extends BinOpInstr {
	public CompareInstr(Operand src, Operand dst) {
		super(src, dst, Operator.SUB);
	}

	public String toString() {
		return "Compare " + src + "," + dst;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
package IC.lir.Instructions;

/** A conditional jump instruction.
 */
public class CondJumpInstr extends JumpInstr {
	public final Cond cond;
	
	public CondJumpInstr(Label label, Cond cond) {
		super(label);
		this.cond = cond;
	}
	
	public String toString() {
		return "Jump" + cond + " " + label;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
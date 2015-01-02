package IC.lir.Instructions;

/** A jump instruction.
 */
public class JumpInstr extends Instruction {
	public final Label label;
	
	public JumpInstr(Label label) {
		this.label = label;
	}
	
	public String toString() {
		return "Jump " + label;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
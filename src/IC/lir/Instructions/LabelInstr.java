package IC.lir.Instructions;

/** A label instruction, i.e., 'name:'.
 */
public class LabelInstr extends Instruction {
	public final Label label;
	
	public LabelInstr(Label label) {
		this.label = label;
	}
	
	public String toString() {
		return label + ":";
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
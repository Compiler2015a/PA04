package IC.lir.Instructions;

/** Prints a LIR program.
 */
public class Printer {
	private final Program program;
	
	public Printer(Program program) {
		this.program = program;
	}
	
	public void print() {
		for (Object data : program.data) {
			System.out.println(data);
		}
		System.out.println();
		for (Instruction instr : program.instructions) {
			System.out.println(instr);
		}
	}
}
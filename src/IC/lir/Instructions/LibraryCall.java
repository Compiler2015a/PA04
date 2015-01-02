package IC.lir.Instructions;

import java.util.*;

/** A library call instruction.
 */
public class LibraryCall extends Instruction {
	public final Label func;
	public final List<Operand> args;
	public final Reg dst;
	
	public LibraryCall(Label func, List<Operand> args, Reg dst) {
		this.func = func;
		this.args = args;
		this.dst = dst;
	}
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Library " + func + "(");
		for (Iterator<Operand> argIter = args.iterator(); argIter.hasNext(); ) {
			result.append(argIter.next());
			if (argIter.hasNext())
				result.append(", ");
		}
		result.append(")," + dst);
		return result.toString();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
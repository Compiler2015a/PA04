package IC.lir.Instructions;

import java.util.*;

/** A 'VirtualCall' instruction.
 */
public class VirtualCall extends Instruction {
	public final Operand func;
	public final List<ParamOpPair> args;
	public final Reg dst;
	public final Reg obj;
	
	public VirtualCall(Reg obj, Operand func, List<ParamOpPair> args, Reg dst) {
		this.func = func;
		this.args = args;
		this.dst = dst;
		this.obj = obj;
	}
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("VirtualCall " + obj + "." + func + "(");
		for (Iterator<ParamOpPair> argIter = args.iterator(); argIter.hasNext(); ) {
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
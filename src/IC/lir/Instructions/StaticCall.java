package IC.lir.Instructions;

import java.util.*;

/** A 'StaticCall' instruction.
 */
public class StaticCall extends Instruction {
	public final Label func;
	public final List<ParamOpPair> args;
	public final Reg dst;
	
	public StaticCall(Label func, List<ParamOpPair> args, Reg dst) {
		this.func = func;
		this.args = args;
		this.dst = dst;
	}
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("StaticCall " + func + "(");
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
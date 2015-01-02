package IC.lir.Instructions;

/** A pair of (formal) parameter name and an operand,
 * appearing in calls to static and virtual functions.
 */
public class ParamOpPair {
	public final Memory param;
	public final Operand op;
	
	public ParamOpPair(Memory param, Operand reg) {
		this.param = param;
		this.op = reg;
	}
	
	public String toString() {
		return param + "=" + op;
	}
}
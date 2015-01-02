package IC.lir.Instructions;

/** An integer constant in a program.
 */
public class Immediate extends Operand {
	public final int val;
	
	protected static int numberOfImmeidates = 0;
	
	public Immediate(int val) {
		this.val = val;
		++numberOfImmeidates;
	}
	
	/** Returns the total number of immediates created so far.
	 */
	public static int getNumberOfImmediates() {
		return numberOfImmeidates;
	}

	public String toString() {
		return new Integer(val).toString();
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + val;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Immediate other = (Immediate) obj;
		if (val != other.val)
			return false;
		return true;
	}
}
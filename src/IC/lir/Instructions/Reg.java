package IC.lir.Instructions;

/** A register in a LIR program.
 */
public class Reg extends Operand {
	public final String name;
	
	protected static int numberOfRegisters = 0;
	
	public Reg(String name) {
		this.name = name;
		++numberOfRegisters;
	}
	
	/** Returns the total number of registers created so far.
	 */
	public static int getNumberOfRegisters() {
		return numberOfRegisters;
	}
	
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
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
		final Reg other = (Reg) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
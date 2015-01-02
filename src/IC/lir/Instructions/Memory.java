package IC.lir.Instructions;

/** A variable in a LIR program.
 */
public class Memory extends Operand {
	public final String name;
	
	protected static int numberOfVars = 0;
	
	public Memory(String name) {
		this.name = name;
		++numberOfVars;
	}
	
	/** Returns the total number of variables created so far.
	 */
	public static int getNumberOfVars() {
		return numberOfVars;
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
		final Memory other = (Memory) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}	
}
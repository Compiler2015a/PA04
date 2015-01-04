package IC.lir.Instructions;

/** A label (not a label instruction).
 */
public class Label extends Operand {
	public final String name;
	
	protected static int numberOfLabels = 0;
	
	public Label(String name) {
		this.name = name;
		++numberOfLabels;
	}
	
	/** Returns the total number of labels created so far.
	 */
	public static int getNumberOfLabels() {
		return numberOfLabels;
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
		final Label other = (Label) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}


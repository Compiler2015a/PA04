package IC.lir.Instructions;

import java.util.*;

/** A dispatch vector.
 */
public class DispatchVector {
	public final Label name;
	public final List<String> labels;
	
	protected static int numberOfTables;
	
	public DispatchVector(Label name, List<String> labels) {
		this.name = name;
		this.labels = labels;
		++numberOfTables;
	}
	
	public static int getNumberOfDispatchTables() {
		return numberOfTables;
	}
	
	public String toString() {
		return name + ": " + labels;
	}
}
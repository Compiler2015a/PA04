package IC.lir;

import java.util.ArrayList;
import java.util.List;

public class StringLiterals {
	List<String> literals;
	
	public StringLiterals() {
		literals = new ArrayList<String>();
	}
	
	/**
	 * @param s string to add to string literals
	 * @return index of string in string literals
	 */
	public int add(String s) {
		literals.add(s);
		return literals.indexOf(s);
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		
		for(int i=0; i<literals.size(); i++) {
			res.append("str"+i+": \""+literals.get(i)+"\"\n");
		}
		
		return res.toString();
	}
}

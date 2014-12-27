package IC.LIR;

import java.util.ArrayList;
import java.util.List;

public class StringLiterals {
	List<String> literals;
	
	public StringLiterals() {
		literals = new ArrayList<String>();
	}
	
	public void add(String s) {
		literals.add(s);
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

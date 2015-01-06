package IC.lir;

import java.util.ArrayList;
import java.util.List;

import IC.lir.Instructions.Instruction;
import IC.lir.Instructions.LabelInstr;
import IC.lir.Instructions.StringLiteral;

public class StringLiterals {
	List<String> literals;
	List<StringLiteral> sLiterals;

	public StringLiterals() {
		literals = new ArrayList<String>();
		sLiterals = new ArrayList<StringLiteral>();
	}

	/**
	 * @param s string to add to string literals
	 * @return index of string in string literals
	 */
	public int add(String s) {
		if(!literals.contains(s)) {
			literals.add(s);
			StringLiteral sl = new StringLiteral("str"+literals.indexOf(s),"\""+ s+"\"");
			sl.assignAddress(literals.indexOf(s));
			sLiterals.add(sl);
		}
		return literals.indexOf(s);
	}

	/**
	 * @return all strings literals in a form of a list of string literals.
	 */
	public List<StringLiteral> toStringLiteralList() {
		return sLiterals;
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

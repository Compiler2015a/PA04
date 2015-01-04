package IC.lir;

import java.util.HashMap;
import java.util.Map;

import IC.lir.Instructions.Reg;

public class Registers {
	Map<Integer, Reg> regs;
	
	public Registers() {
		this.regs = new HashMap<Integer,Reg>();
		regs.put(-1,new Reg("Rdummy"));
	}
	
	/**
	 * 
	 * @param index index of register requested
	 * @return Reg corresponding to index
	 */
	public Reg request(int index) {
		if (regs.containsKey(index))
			return regs.get(index);
		else {
			regs.put(index,new Reg("R"+index));
			return regs.get(index);
		}
	}
}

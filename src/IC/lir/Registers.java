package IC.lir;

import java.util.HashMap;
import java.util.Map;

import IC.lir.Instructions.Reg;

public class Registers {
	Map<Integer, Reg> regs;
	
	public Registers() {
		this.regs = new HashMap<Integer,Reg>();
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
			
		}
	}
}

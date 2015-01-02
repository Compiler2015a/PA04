package IC.lir.Instructions;

import java.util.*;

/** This class enables performing different queries on LIR instructions.
 */
public class InstructionInfo implements Visitor {
	/** The set of registers in the instruction last processed.
	 */
	public Set<Reg> registers;

	/** The set of labels in the instruction last processed.
	 */
	public Set<Label> labels;
	
	/** The set of variables in the instruction last processed.
	 */
	public Set<Memory> variables;
	
	/** The set of constants in the instruction last processed.
	 */
	public Set<Immediate> constants;
	
	/** Classifies each element of the given instruction and
	 * stores it in the correct operand set according to its type.
	 * 
	 * @param instr An instruction.
	 */
	public void getInfo(Instruction instr) {
		registers = new HashSet<Reg>();
		labels = new HashSet<Label>();
		variables = new HashSet<Memory>();
		constants = new HashSet<Immediate>();
		
		instr.accept(this);
	}
	
	/** Identifies the class type of each operand and adds it
	 * to the corresponding operand type set.
	 * 
	 * @param op An operand.
	 */
	public void processOperand(Operand op) {
		if (op instanceof Reg)
			registers.add((Reg) op);
		else if (op instanceof Memory)
			variables.add((Memory) op);
		else if (op instanceof Immediate)
			constants.add((Immediate) op);
		else if (op instanceof Label)
			labels.add((Label) op);
		else
			throw new RuntimeException("Encountered unknown type of operand: " + 
					op.getClass() + " " + op + "!");
	}
	
	public void visit(MoveInstr instr) {
		processOperand(instr.src);
		processOperand(instr.dst);
	}

	public void visit(BinOpInstr instr) {
		processOperand(instr.src);
		processOperand(instr.dst);
	}

	public void visit(CompareInstr instr) {
		processOperand(instr.src);
		processOperand(instr.dst);
	}

	public void visit(UnaryOpInstr instr) {
		processOperand(instr.dst);
	}

	public void visit(LabelInstr instr) {
		processOperand(instr.label);
	}

	public void visit(MoveArrayInstr instr) {
		processOperand(instr.base);
		processOperand(instr.mem);
		processOperand(instr.offset);
	}

	public void visit(MoveFieldInstr instr) {
		processOperand(instr.base);
		processOperand(instr.mem);
		processOperand(instr.offset);
	}
	
	public void visit(ArrayLengthInstr instr) {
		processOperand(instr.arr);
		processOperand(instr.dst);
	}	

	public void visit(JumpInstr instr) {
		processOperand(instr.label);
	}

	public void visit(CondJumpInstr instr) {
		processOperand(instr.label);
	}

	public void visit(StaticCall instr) {
		processOperand(instr.dst);
		processOperand(instr.func);
		for (ParamOpPair pop : instr.args) {
			processOperand(pop.param);
			processOperand(pop.op);
		}
	}

	public void visit(VirtualCall instr) {
		processOperand(instr.obj);
		processOperand(instr.dst);
		processOperand(instr.func);
		for (ParamOpPair pop : instr.args) {
			processOperand(pop.param);
			processOperand(pop.op);
		}
	}

	public void visit(LibraryCall instr) {
		processOperand(instr.dst);
		processOperand(instr.func);
		for (Operand op : instr.args) {
			processOperand(op);
		}
	}

	public void visit(ReturnInstr instr) {
		processOperand(instr.dst);
	}
}
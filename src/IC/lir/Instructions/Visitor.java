package IC.lir.Instructions;

/** A visitor for LIR instructions.
 */
public interface Visitor {
	public void visit(MoveInstr instr);
	public void visit(BinOpInstr instr);
	public void visit(CompareInstr instr);
	public void visit(UnaryOpInstr instr);
	public void visit(LabelInstr instr);
	public void visit(MoveArrayInstr instr);
	public void visit(MoveFieldInstr instr);
	public void visit(ArrayLengthInstr instr);
	public void visit(JumpInstr instr);
	public void visit(CondJumpInstr instr);
	public void visit(StaticCall instr);
	public void visit(VirtualCall instr);
	public void visit(LibraryCall instr);
	public void visit(ReturnInstr instr);
}
package IC.lir.Instructions;

/** An enumeration for all types of binary and unary instructions.
 */
public enum Operator {
	ADD, SUB, MUL, DIV, MOD, AND, OR, XOR,
	INC, DEC, NEG, NOT;
	
	public String toString() {
		switch(this) {
		case ADD: return "Add";
		case SUB: return "Sub";
		case MUL: return "Mul";
		case DIV: return "Div";
		case MOD: return "Mod";
		case AND: return "And";
		case OR: return "Or";
		case XOR: return "Xor";
		case INC: return "Inc";
		case DEC: return "Dec";
		case NEG: return "Neg";
		case NOT: return "Not";
		default:
			throw new RuntimeException("Encountered unknown operator: " + this);
		}
	}
}
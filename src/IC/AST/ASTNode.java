package IC.AST;

import IC.SymbolsTable.*;

/**
 * Abstract AST node base class.
 * 
 * @author Tovi Almozlino
 */
public abstract class ASTNode {

	private int line;
	private SymbolTable symbolsTable;
	
	private IC.Types.Type entryType;
	private IC.SymbolsTable.SymbolEntry symbolEntry;
	
	/**
	 * Double dispatch method, to allow a visitor to visit a specific subclass.
	 * 
	 * @param visitor
	 *            The visitor.
	 * @return A value propagated by the visitor.
	 */
	public abstract Object accept(Visitor visitor);

	/**
	 * Constructs an AST node corresponding to a line number in the original
	 * code. Used by subclasses.
	 * 
	 * @param line
	 *            The line number.
	 */
	protected ASTNode(int line) {
		this.symbolsTable = null;
		this.line = line;
		this.symbolEntry = null;
	}

	public int getLine() {
		return line;
	}
	
	public SymbolTable getSymbolsTable() {
		return symbolsTable;
	}
	
	public void setSymbolsTable(SymbolTable table) {
		this.symbolsTable = table;
	}
	
	public IC.Types.Type getEntryType() {
		return (this.symbolEntry == null) ? entryType : this.symbolEntry.getType();
	}

	public void setEntryType(IC.Types.Type entryType) {
		this.entryType = entryType;
	}

	public IC.SymbolsTable.SymbolEntry getSymbolEntry() {
		return symbolEntry;
	}

	public void setSymbolEntry(IC.SymbolsTable.SymbolEntry symbolEntry) {
		this.symbolEntry = symbolEntry;
	}
	
	
}

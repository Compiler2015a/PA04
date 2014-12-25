package IC.Parser;
import java_cup.runtime.Symbol;

public class Token extends Symbol {
	private int id, line, column;
	private Object value;
	
	public Token(int id, Object value, int line, int column) {
		super(id, line, column, value);
		this.id=id;
		this.value=value;
		this.line=line;
		this.column = column;
	}
	
	public int getId() {return id;};
	public Object getValue() {return value;};
	public int getLine() {return line;};
	public int getColumn() {return column;};

	public String getTag() {
		switch (sym) {
		
			case IC.Parser.sym.ID:
				return "ID";
			case IC.Parser.sym.CLASS_ID:
				return "CLASS_ID";
			case IC.Parser.sym.INTEGER_LITERAL:
				return "INTEGER";
			case IC.Parser.sym.STRING_LITERAL:
				return "STRING";
				
		}
		
		return value.toString();
	}
}
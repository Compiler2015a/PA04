package IC.SemanticAnalysis;

public class SemanticErrorThrower {
	
	private int line;
	private String msg;
	
	public SemanticErrorThrower(int line, String msg) {
		this.line = line;
		this.msg  = msg;
	}
	
	public void execute() throws SemanticError {
		throw new SemanticError(line, msg);
	}
}

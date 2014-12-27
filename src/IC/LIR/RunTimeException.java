package IC.LIR;

public class RunTimeException extends RuntimeException {
	private int line;
	private int column;
	    
    public RunTimeException(String message, int line) {
    	super(message);
        this.line = line;
        this.column = -1;
    }
    
    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}

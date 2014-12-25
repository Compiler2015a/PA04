package IC.Parser;

import java.io.IOException;

public class LexicalError extends IOException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LexicalError(String token, int line , int column) {
    	super("" + line + ":" + column + " Lexical error: " + token);
    }   
}
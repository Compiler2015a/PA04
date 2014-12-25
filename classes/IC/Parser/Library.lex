package IC.Parser;

import java_cup.runtime.*;
import IC.Parser.Token;
import IC.Parser.LexicalError;

%%
%public
%class LibLexer
%type Token
%unicode
%cup
%line
%column
%{

  private Token token(int type, Object value) {
    return new Token(type, value, yyline+1, yycolumn+1);
  }
  
  private Token stringToken(int type, String value) {
	return new Token(type, value, yyline+1, yycolumn-value.length());
  }
  
%}
 LineTerminator = \r|\n|\r\n

WhiteSpace     = {LineTerminator} | [ \t\f]

Lowercase = [a-z]
Uppercase = [A-Z]
Letters = [A-Za-z] 

ClassIdentifier = {Uppercase}({Letters} | {DecIntegerLiteral} | _)*
Identifier = {Lowercase}({Letters} | {DecIntegerLiteral} | _)*

DecIntegerLiteral = 0+ | [1-9][0-9]*
%state BCOMMENTS
%state LCOMMENTS

%%
 /* keywords */
<YYINITIAL> "class"           	 { return token(Libsym.CLASS_KEYWORD, yytext()); }
<YYINITIAL> "static"           	 { return token(Libsym.STATIC_KEYWORD, yytext()); }
<YYINITIAL> "void"           	 { return token(Libsym.VOID_KEYWORD, yytext()); }
<YYINITIAL> "int"           	 { return token(Libsym.INT_KEYWORD, yytext()); }
<YYINITIAL> "boolean"            { return token(Libsym.BOOLEAN_KEYWORD, yytext()); }
<YYINITIAL> "string"           	 { return token(Libsym.STRING_KEYWORD, yytext()); }
<YYINITIAL> ";"           		 { return token(Libsym.SEMICOLON, yytext()); }
<YYINITIAL> ","           		 { return token(Libsym.COMMA, yytext()); }

<YYINITIAL> {

  /* identifiers */ 
  {ClassIdentifier}              { return token(Libsym.CLASS_ID, yytext()); }	
  {Identifier}                   { return token(Libsym.ID, yytext()); }
 
  
  /* operators */
  "("							 { return token(Libsym.LP, yytext()); }
  ")"							 { return token(Libsym.RP, yytext()); }
  "["							 { return token(Libsym.LC, yytext()); }
  "]"							 { return token(Libsym.RC, yytext()); }
  "{"							 { return token(Libsym.LB, yytext()); }
  "}"							 { return token(Libsym.RB, yytext()); }
 }
 
 <YYINITIAL> {
  /* comments */
  
  "//" 							 { yybegin(LCOMMENTS); }

  "/*" 							 { yybegin(BCOMMENTS); }

 
  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}

 <LCOMMENTS> {
  ["\r"]?["\n"] 			  		 { yybegin(YYINITIAL); }
  [^\n] 							 { }
}

 <BCOMMENTS> {
  "*/"  						 { yybegin(YYINITIAL); }
  [^]							 {}
}
 


 /* error fallback */
	[^]                              { throw new LexicalError(yytext(), yyline+1, yycolumn+1); }
	
<<EOF>> 	
{ 
	if (yystate() == BCOMMENTS)
		throw new LexicalError("Unexpected end of comment", yyline+1, yycolumn+1);
	return token(sym.EOF, "EOF"); 
}
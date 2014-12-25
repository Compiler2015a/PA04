/*  1:   */ package java_cup.runtime;
/*  2:   */ 
/*  3:   */ public class DefaultSymbolFactory
/*  4:   */   implements SymbolFactory
/*  5:   */ {
/*  6:   */   public Symbol newSymbol(String name, int id, Symbol left, Symbol right, Object value)
/*  7:   */   {
/*  8:32 */     return new Symbol(id, left, right, value);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public Symbol newSymbol(String name, int id, Symbol left, Symbol right)
/* 12:   */   {
/* 13:35 */     return new Symbol(id, left, right);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Symbol newSymbol(String name, int id, int left, int right, Object value)
/* 17:   */   {
/* 18:38 */     return new Symbol(id, left, right, value);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Symbol newSymbol(String name, int id, int left, int right)
/* 22:   */   {
/* 23:41 */     return new Symbol(id, left, right);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Symbol startSymbol(String name, int id, int state)
/* 27:   */   {
/* 28:44 */     return new Symbol(id, state);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Symbol newSymbol(String name, int id)
/* 32:   */   {
/* 33:47 */     return new Symbol(id);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Symbol newSymbol(String name, int id, Object value)
/* 37:   */   {
/* 38:50 */     return new Symbol(id, value);
/* 39:   */   }
/* 40:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.runtime.DefaultSymbolFactory
 * JD-Core Version:    0.7.0.1
 */
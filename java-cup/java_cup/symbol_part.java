/*  1:   */ package java_cup;
/*  2:   */ 
/*  3:   */ public class symbol_part
/*  4:   */   extends production_part
/*  5:   */ {
/*  6:   */   protected symbol _the_symbol;
/*  7:   */   
/*  8:   */   public symbol_part(symbol sym, String lab)
/*  9:   */     throws internal_error
/* 10:   */   {
/* 11:23 */     super(lab);
/* 12:25 */     if (sym == null) {
/* 13:26 */       throw new internal_error("Attempt to construct a symbol_part with a null symbol");
/* 14:   */     }
/* 15:28 */     this._the_symbol = sym;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public symbol_part(symbol sym)
/* 19:   */     throws internal_error
/* 20:   */   {
/* 21:38 */     this(sym, null);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public symbol the_symbol()
/* 25:   */   {
/* 26:49 */     return this._the_symbol;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean is_action()
/* 30:   */   {
/* 31:56 */     return false;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public boolean equals(symbol_part other)
/* 35:   */   {
/* 36:63 */     return (other != null) && (super.equals(other)) && (the_symbol().equals(other.the_symbol()));
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean equals(Object other)
/* 40:   */   {
/* 41:72 */     if (!(other instanceof symbol_part)) {
/* 42:73 */       return false;
/* 43:   */     }
/* 44:75 */     return equals((symbol_part)other);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public int hashCode()
/* 48:   */   {
/* 49:83 */     return super.hashCode() ^ (the_symbol() == null ? 0 : the_symbol().hashCode());
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String toString()
/* 53:   */   {
/* 54:92 */     if (the_symbol() != null) {
/* 55:93 */       return super.toString() + the_symbol();
/* 56:   */     }
/* 57:95 */     return super.toString() + "$$MISSING-SYMBOL$$";
/* 58:   */   }
/* 59:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.symbol_part
 * JD-Core Version:    0.7.0.1
 */
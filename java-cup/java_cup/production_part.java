/*  1:   */ package java_cup;
/*  2:   */ 
/*  3:   */ public abstract class production_part
/*  4:   */ {
/*  5:   */   protected String _label;
/*  6:   */   
/*  7:   */   public production_part(String lab)
/*  8:   */   {
/*  9:22 */     this._label = lab;
/* 10:   */   }
/* 11:   */   
/* 12:   */   public String label()
/* 13:   */   {
/* 14:37 */     return this._label;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public abstract boolean is_action();
/* 18:   */   
/* 19:   */   public boolean equals(production_part other)
/* 20:   */   {
/* 21:53 */     if (other == null) {
/* 22:53 */       return false;
/* 23:   */     }
/* 24:56 */     if (label() != null) {
/* 25:57 */       return label().equals(other.label());
/* 26:   */     }
/* 27:59 */     return other.label() == null;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean equals(Object other)
/* 31:   */   {
/* 32:67 */     if (!(other instanceof production_part)) {
/* 33:68 */       return false;
/* 34:   */     }
/* 35:70 */     return equals((production_part)other);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int hashCode()
/* 39:   */   {
/* 40:78 */     return label() == null ? 0 : label().hashCode();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String toString()
/* 44:   */   {
/* 45:86 */     if (label() != null) {
/* 46:87 */       return label() + ":";
/* 47:   */     }
/* 48:89 */     return " ";
/* 49:   */   }
/* 50:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.production_part
 * JD-Core Version:    0.7.0.1
 */
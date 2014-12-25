/*  1:   */ package java_cup;
/*  2:   */ 
/*  3:   */ public class reduce_action
/*  4:   */   extends parse_action
/*  5:   */ {
/*  6:   */   protected production _reduce_with;
/*  7:   */   
/*  8:   */   public reduce_action(production prod)
/*  9:   */     throws internal_error
/* 10:   */   {
/* 11:23 */     if (prod == null) {
/* 12:24 */       throw new internal_error("Attempt to create a reduce_action with a null production");
/* 13:   */     }
/* 14:27 */     this._reduce_with = prod;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public production reduce_with()
/* 18:   */   {
/* 19:38 */     return this._reduce_with;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int kind()
/* 23:   */   {
/* 24:45 */     return 2;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean equals(reduce_action other)
/* 28:   */   {
/* 29:52 */     return (other != null) && (other.reduce_with() == reduce_with());
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean equals(Object other)
/* 33:   */   {
/* 34:60 */     if ((other instanceof reduce_action)) {
/* 35:61 */       return equals((reduce_action)other);
/* 36:   */     }
/* 37:63 */     return false;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int hashCode()
/* 41:   */   {
/* 42:72 */     return reduce_with().hashCode();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String toString()
/* 46:   */   {
/* 47:79 */     return "REDUCE(with prod " + reduce_with().index() + ")";
/* 48:   */   }
/* 49:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.reduce_action
 * JD-Core Version:    0.7.0.1
 */
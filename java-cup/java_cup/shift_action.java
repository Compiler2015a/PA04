/*  1:   */ package java_cup;
/*  2:   */ 
/*  3:   */ public class shift_action
/*  4:   */   extends parse_action
/*  5:   */ {
/*  6:   */   protected lalr_state _shift_to;
/*  7:   */   
/*  8:   */   public shift_action(lalr_state shft_to)
/*  9:   */     throws internal_error
/* 10:   */   {
/* 11:23 */     if (shft_to == null) {
/* 12:24 */       throw new internal_error("Attempt to create a shift_action to a null state");
/* 13:   */     }
/* 14:27 */     this._shift_to = shft_to;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public lalr_state shift_to()
/* 18:   */   {
/* 19:38 */     return this._shift_to;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int kind()
/* 23:   */   {
/* 24:45 */     return 1;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean equals(shift_action other)
/* 28:   */   {
/* 29:52 */     return (other != null) && (other.shift_to() == shift_to());
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean equals(Object other)
/* 33:   */   {
/* 34:60 */     if ((other instanceof shift_action)) {
/* 35:61 */       return equals((shift_action)other);
/* 36:   */     }
/* 37:63 */     return false;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int hashCode()
/* 41:   */   {
/* 42:72 */     return shift_to().hashCode();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String toString()
/* 46:   */   {
/* 47:78 */     return "SHIFT(to state " + shift_to().index() + ")";
/* 48:   */   }
/* 49:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.shift_action
 * JD-Core Version:    0.7.0.1
 */
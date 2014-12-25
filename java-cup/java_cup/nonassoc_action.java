/*  1:   */ package java_cup;
/*  2:   */ 
/*  3:   */ public class nonassoc_action
/*  4:   */   extends parse_action
/*  5:   */ {
/*  6:   */   public nonassoc_action()
/*  7:   */     throws internal_error
/*  8:   */   {}
/*  9:   */   
/* 10:   */   public int kind()
/* 11:   */   {
/* 12:31 */     return 3;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean equals(parse_action other)
/* 16:   */   {
/* 17:38 */     return (other != null) && (other.kind() == 3);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean equals(Object other)
/* 21:   */   {
/* 22:46 */     if ((other instanceof parse_action)) {
/* 23:47 */       return equals((parse_action)other);
/* 24:   */     }
/* 25:49 */     return false;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int hashCode()
/* 29:   */   {
/* 30:58 */     return 212853537;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String toString()
/* 34:   */   {
/* 35:66 */     return "NONASSOC";
/* 36:   */   }
/* 37:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.nonassoc_action
 * JD-Core Version:    0.7.0.1
 */
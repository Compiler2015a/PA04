/*  1:   */ package java_cup;
/*  2:   */ 
/*  3:   */ public class parse_action
/*  4:   */ {
/*  5:   */   public static final int ERROR = 0;
/*  6:   */   public static final int SHIFT = 1;
/*  7:   */   public static final int REDUCE = 2;
/*  8:   */   public static final int NONASSOC = 3;
/*  9:   */   
/* 10:   */   public int kind()
/* 11:   */   {
/* 12:55 */     return 0;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean equals(parse_action other)
/* 16:   */   {
/* 17:63 */     return (other != null) && (other.kind() == 0);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean equals(Object other)
/* 21:   */   {
/* 22:71 */     if ((other instanceof parse_action)) {
/* 23:72 */       return equals((parse_action)other);
/* 24:   */     }
/* 25:74 */     return false;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int hashCode()
/* 29:   */   {
/* 30:82 */     return 212853027;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String toString()
/* 34:   */   {
/* 35:88 */     return "ERROR";
/* 36:   */   }
/* 37:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.parse_action
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package java_cup;
/*  2:   */ 
/*  3:   */ public class parse_reduce_row
/*  4:   */ {
/*  5:   */   public parse_reduce_row()
/*  6:   */   {
/*  7:18 */     if (_size <= 0) {
/*  8:18 */       _size = non_terminal.number();
/*  9:   */     }
/* 10:21 */     this.under_non_term = new lalr_state[size()];
/* 11:   */   }
/* 12:   */   
/* 13:29 */   protected static int _size = 0;
/* 14:   */   public lalr_state[] under_non_term;
/* 15:   */   
/* 16:   */   public static int size()
/* 17:   */   {
/* 18:32 */     return _size;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static void clear()
/* 22:   */   {
/* 23:36 */     _size = 0;
/* 24:   */   }
/* 25:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.parse_reduce_row
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package java_cup;
/*  2:   */ 
/*  3:   */ public class internal_error
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   public internal_error(String msg)
/*  7:   */   {
/*  8:10 */     super(msg);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void crash()
/* 12:   */   {
/* 13:17 */     ErrorManager.getManager().emit_fatal("JavaCUP Internal Error Detected: " + getMessage());
/* 14:18 */     printStackTrace();
/* 15:19 */     System.exit(-1);
/* 16:   */   }
/* 17:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.internal_error
 * JD-Core Version:    0.7.0.1
 */
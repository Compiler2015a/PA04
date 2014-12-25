/*  1:   */ package java_cup.runtime;
/*  2:   */ 
/*  3:   */ import java.util.Collections;
/*  4:   */ import java.util.LinkedList;
/*  5:   */ import java.util.List;
/*  6:   */ 
/*  7:   */ public class ScannerBuffer
/*  8:   */   implements Scanner
/*  9:   */ {
/* 10:   */   private Scanner inner;
/* 11: 9 */   private List<Symbol> buffer = new LinkedList();
/* 12:   */   
/* 13:   */   public ScannerBuffer(Scanner inner)
/* 14:   */   {
/* 15:15 */     this.inner = inner;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public List<Symbol> getBuffered()
/* 19:   */   {
/* 20:22 */     return Collections.unmodifiableList(this.buffer);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Symbol next_token()
/* 24:   */     throws Exception
/* 25:   */   {
/* 26:26 */     Symbol buffered = this.inner.next_token();
/* 27:27 */     this.buffer.add(buffered);
/* 28:28 */     return buffered;
/* 29:   */   }
/* 30:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.runtime.ScannerBuffer
 * JD-Core Version:    0.7.0.1
 */
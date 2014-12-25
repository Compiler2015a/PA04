/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ public abstract class symbol
/*   4:    */ {
/*   5:    */   protected String _name;
/*   6:    */   protected String _stack_type;
/*   7:    */   
/*   8:    */   public symbol(String nm, String tp)
/*   9:    */   {
/*  10: 28 */     if (nm == null) {
/*  11: 28 */       nm = "";
/*  12:    */     }
/*  13: 31 */     if (tp == null) {
/*  14: 31 */       tp = "Object";
/*  15:    */     }
/*  16: 33 */     this._name = nm;
/*  17: 34 */     this._stack_type = tp;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public symbol(String nm)
/*  21:    */   {
/*  22: 44 */     this(nm, null);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String name()
/*  26:    */   {
/*  27: 55 */     return this._name;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String stack_type()
/*  31:    */   {
/*  32: 63 */     return this._stack_type;
/*  33:    */   }
/*  34:    */   
/*  35: 68 */   protected int _use_count = 0;
/*  36:    */   protected int _index;
/*  37:    */   
/*  38:    */   public int use_count()
/*  39:    */   {
/*  40: 71 */     return this._use_count;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void note_use()
/*  44:    */   {
/*  45: 74 */     this._use_count += 1;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int index()
/*  49:    */   {
/*  50: 88 */     return this._index;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public abstract boolean is_non_term();
/*  54:    */   
/*  55:    */   public String toString()
/*  56:    */   {
/*  57:102 */     return name();
/*  58:    */   }
/*  59:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.symbol
 * JD-Core Version:    0.7.0.1
 */
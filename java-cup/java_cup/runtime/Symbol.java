/*   1:    */ package java_cup.runtime;
/*   2:    */ 
/*   3:    */ public class Symbol
/*   4:    */ {
/*   5:    */   public int sym;
/*   6:    */   public int parse_state;
/*   7:    */   
/*   8:    */   public Symbol(int id, Symbol left, Symbol right, Object o)
/*   9:    */   {
/*  10: 32 */     this(id, left.left, right.right, o);
/*  11:    */   }
/*  12:    */   
/*  13:    */   public Symbol(int id, Symbol left, Symbol right)
/*  14:    */   {
/*  15: 35 */     this(id, left.left, right.right);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public Symbol(int id, int l, int r, Object o)
/*  19:    */   {
/*  20: 42 */     this(id);
/*  21: 43 */     this.left = l;
/*  22: 44 */     this.right = r;
/*  23: 45 */     this.value = o;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Symbol(int id, Object o)
/*  27:    */   {
/*  28: 53 */     this(id, -1, -1, o);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Symbol(int id, int l, int r)
/*  32:    */   {
/*  33: 61 */     this(id, l, r, null);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Symbol(int sym_num)
/*  37:    */   {
/*  38: 69 */     this(sym_num, -1);
/*  39: 70 */     this.left = -1;
/*  40: 71 */     this.right = -1;
/*  41:    */   }
/*  42:    */   
/*  43:    */   Symbol(int sym_num, int state)
/*  44:    */   {
/*  45: 79 */     this.sym = sym_num;
/*  46: 80 */     this.parse_state = state;
/*  47:    */   }
/*  48:    */   
/*  49: 97 */   boolean used_by_parser = false;
/*  50:    */   public int left;
/*  51:    */   public int right;
/*  52:    */   public Object value;
/*  53:    */   
/*  54:    */   public String toString()
/*  55:    */   {
/*  56:109 */     return "#" + this.sym;
/*  57:    */   }
/*  58:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.runtime.Symbol
 * JD-Core Version:    0.7.0.1
 */
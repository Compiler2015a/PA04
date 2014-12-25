/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ public class parse_action_row
/*   4:    */ {
/*   5:    */   public parse_action_row()
/*   6:    */   {
/*   7: 19 */     if (_size <= 0) {
/*   8: 19 */       _size = terminal.number();
/*   9:    */     }
/*  10: 22 */     this.under_term = new parse_action[size()];
/*  11: 25 */     for (int i = 0; i < _size; i++) {
/*  12: 26 */       this.under_term[i] = new parse_action();
/*  13:    */     }
/*  14:    */   }
/*  15:    */   
/*  16: 34 */   protected static int _size = 0;
/*  17:    */   
/*  18:    */   public static int size()
/*  19:    */   {
/*  20: 37 */     return _size;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static void clear()
/*  24:    */   {
/*  25: 41 */     _size = 0;
/*  26: 42 */     reduction_count = null;
/*  27:    */   }
/*  28:    */   
/*  29: 48 */   protected static int[] reduction_count = null;
/*  30:    */   public parse_action[] under_term;
/*  31:    */   public int default_reduce;
/*  32:    */   
/*  33:    */   public void compute_default()
/*  34:    */   {
/*  35: 81 */     if (reduction_count == null) {
/*  36: 82 */       reduction_count = new int[production.number()];
/*  37:    */     }
/*  38: 85 */     for (int i = 0; i < production.number(); i++) {
/*  39: 86 */       reduction_count[i] = 0;
/*  40:    */     }
/*  41: 87 */     int max_prod = -1;
/*  42: 88 */     int max_red = 0;
/*  43: 91 */     for (i = 0; i < size(); i++) {
/*  44: 92 */       if (this.under_term[i].kind() == 2)
/*  45:    */       {
/*  46: 96 */         int prod = ((reduce_action)this.under_term[i]).reduce_with().index();
/*  47: 97 */         reduction_count[prod] += 1;
/*  48: 98 */         if (reduction_count[prod] > max_red)
/*  49:    */         {
/*  50:100 */           max_red = reduction_count[prod];
/*  51:101 */           max_prod = prod;
/*  52:    */         }
/*  53:    */       }
/*  54:    */     }
/*  55:106 */     this.default_reduce = max_prod;
/*  56:    */   }
/*  57:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.parse_action_row
 * JD-Core Version:    0.7.0.1
 */
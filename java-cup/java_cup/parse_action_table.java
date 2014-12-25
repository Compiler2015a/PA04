/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ 
/*   5:    */ public class parse_action_table
/*   6:    */ {
/*   7:    */   protected int _num_states;
/*   8:    */   public parse_action_row[] under_state;
/*   9:    */   
/*  10:    */   public parse_action_table()
/*  11:    */   {
/*  12: 29 */     this._num_states = lalr_state.number();
/*  13:    */     
/*  14:    */ 
/*  15: 32 */     this.under_state = new parse_action_row[this._num_states];
/*  16: 33 */     for (int i = 0; i < this._num_states; i++) {
/*  17: 34 */       this.under_state[i] = new parse_action_row();
/*  18:    */     }
/*  19:    */   }
/*  20:    */   
/*  21:    */   public int num_states()
/*  22:    */   {
/*  23: 45 */     return this._num_states;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void check_reductions()
/*  27:    */     throws internal_error
/*  28:    */   {
/*  29: 67 */     for (int row = 0; row < num_states(); row++) {
/*  30: 69 */       for (int col = 0; col < parse_action_row.size(); col++)
/*  31:    */       {
/*  32: 72 */         parse_action act = this.under_state[row].under_term[col];
/*  33: 73 */         if ((act != null) && (act.kind() == 2)) {
/*  34: 76 */           ((reduce_action)act).reduce_with().note_reduction_use();
/*  35:    */         }
/*  36:    */       }
/*  37:    */     }
/*  38: 82 */     for (Enumeration p = production.all(); p.hasMoreElements();)
/*  39:    */     {
/*  40: 84 */       production prod = (production)p.nextElement();
/*  41: 87 */       if ((prod.num_reductions() == 0) && 
/*  42:    */       
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47: 93 */         (!emit.nowarn)) {
/*  48: 96 */         ErrorManager.getManager().emit_warning("*** Production \"" + prod.to_simple_string() + "\" never reduced");
/*  49:    */       }
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String toString()
/*  54:    */   {
/*  55:111 */     String result = "-------- ACTION_TABLE --------\n";
/*  56:112 */     for (int row = 0; row < num_states(); row++)
/*  57:    */     {
/*  58:114 */       result = result + "From state #" + row + "\n";
/*  59:115 */       int cnt = 0;
/*  60:116 */       for (int col = 0; col < parse_action_row.size(); col++) {
/*  61:119 */         if (this.under_state[row].under_term[col].kind() != 0)
/*  62:    */         {
/*  63:121 */           result = result + " [term " + col + ":" + this.under_state[row].under_term[col] + "]";
/*  64:    */           
/*  65:    */ 
/*  66:124 */           cnt++;
/*  67:125 */           if (cnt == 2)
/*  68:    */           {
/*  69:127 */             result = result + "\n";
/*  70:128 */             cnt = 0;
/*  71:    */           }
/*  72:    */         }
/*  73:    */       }
/*  74:133 */       if (cnt != 0) {
/*  75:133 */         result = result + "\n";
/*  76:    */       }
/*  77:    */     }
/*  78:135 */     result = result + "------------------------------";
/*  79:    */     
/*  80:137 */     return result;
/*  81:    */   }
/*  82:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.parse_action_table
 * JD-Core Version:    0.7.0.1
 */
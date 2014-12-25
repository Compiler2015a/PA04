/*  1:   */ package java_cup;
/*  2:   */ 
/*  3:   */ public class parse_reduce_table
/*  4:   */ {
/*  5:   */   protected int _num_states;
/*  6:   */   public parse_reduce_row[] under_state;
/*  7:   */   
/*  8:   */   public parse_reduce_table()
/*  9:   */   {
/* 10:28 */     this._num_states = lalr_state.number();
/* 11:   */     
/* 12:   */ 
/* 13:31 */     this.under_state = new parse_reduce_row[this._num_states];
/* 14:32 */     for (int i = 0; i < this._num_states; i++) {
/* 15:33 */       this.under_state[i] = new parse_reduce_row();
/* 16:   */     }
/* 17:   */   }
/* 18:   */   
/* 19:   */   public int num_states()
/* 20:   */   {
/* 21:45 */     return this._num_states;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String toString()
/* 25:   */   {
/* 26:63 */     String result = "-------- REDUCE_TABLE --------\n";
/* 27:64 */     for (int row = 0; row < num_states(); row++)
/* 28:   */     {
/* 29:66 */       result = result + "From state #" + row + "\n";
/* 30:67 */       int cnt = 0;
/* 31:68 */       for (int col = 0; col < parse_reduce_row.size(); col++)
/* 32:   */       {
/* 33:71 */         lalr_state goto_st = this.under_state[row].under_non_term[col];
/* 34:74 */         if (goto_st != null)
/* 35:   */         {
/* 36:76 */           result = result + " [non term " + col + "->";
/* 37:77 */           result = result + "state " + goto_st.index() + "]";
/* 38:   */           
/* 39:   */ 
/* 40:80 */           cnt++;
/* 41:81 */           if (cnt == 3)
/* 42:   */           {
/* 43:83 */             result = result + "\n";
/* 44:84 */             cnt = 0;
/* 45:   */           }
/* 46:   */         }
/* 47:   */       }
/* 48:89 */       if (cnt != 0) {
/* 49:89 */         result = result + "\n";
/* 50:   */       }
/* 51:   */     }
/* 52:91 */     result = result + "-----------------------------";
/* 53:   */     
/* 54:93 */     return result;
/* 55:   */   }
/* 56:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.parse_reduce_table
 * JD-Core Version:    0.7.0.1
 */
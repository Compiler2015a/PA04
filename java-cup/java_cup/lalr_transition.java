/*  1:   */ package java_cup;
/*  2:   */ 
/*  3:   */ public class lalr_transition
/*  4:   */ {
/*  5:   */   protected symbol _on_symbol;
/*  6:   */   protected lalr_state _to_state;
/*  7:   */   protected lalr_transition _next;
/*  8:   */   
/*  9:   */   public lalr_transition(symbol on_sym, lalr_state to_st, lalr_transition nxt)
/* 10:   */     throws internal_error
/* 11:   */   {
/* 12:28 */     if (on_sym == null) {
/* 13:29 */       throw new internal_error("Attempt to create transition on null symbol");
/* 14:   */     }
/* 15:30 */     if (to_st == null) {
/* 16:31 */       throw new internal_error("Attempt to create transition to null state");
/* 17:   */     }
/* 18:34 */     this._on_symbol = on_sym;
/* 19:35 */     this._to_state = to_st;
/* 20:36 */     this._next = nxt;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public lalr_transition(symbol on_sym, lalr_state to_st)
/* 24:   */     throws internal_error
/* 25:   */   {
/* 26:47 */     this(on_sym, to_st, null);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public symbol on_symbol()
/* 30:   */   {
/* 31:58 */     return this._on_symbol;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public lalr_state to_state()
/* 35:   */   {
/* 36:66 */     return this._to_state;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public lalr_transition next()
/* 40:   */   {
/* 41:74 */     return this._next;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String toString()
/* 45:   */   {
/* 46:85 */     String result = "transition on " + on_symbol().name() + " to state [";
/* 47:86 */     result = result + this._to_state.index();
/* 48:87 */     result = result + "]";
/* 49:   */     
/* 50:89 */     return result;
/* 51:   */   }
/* 52:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.lalr_transition
 * JD-Core Version:    0.7.0.1
 */
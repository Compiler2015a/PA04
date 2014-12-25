/*  1:   */ package java_cup;
/*  2:   */ 
/*  3:   */ public class action_production
/*  4:   */   extends production
/*  5:   */ {
/*  6:   */   private int indexOfIntermediateResult;
/*  7:   */   protected production _base_production;
/*  8:   */   
/*  9:   */   public action_production(production base, non_terminal lhs_sym, production_part[] rhs_parts, int rhs_len, String action_str, int indexOfIntermediateResult)
/* 10:   */     throws internal_error
/* 11:   */   {
/* 12:30 */     super(lhs_sym, rhs_parts, rhs_len, action_str);
/* 13:31 */     this._base_production = base;
/* 14:32 */     this.indexOfIntermediateResult = indexOfIntermediateResult;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int getIndexOfIntermediateResult()
/* 18:   */   {
/* 19:39 */     return this.indexOfIntermediateResult;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public production base_production()
/* 23:   */   {
/* 24:47 */     return this._base_production;
/* 25:   */   }
/* 26:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.action_production
 * JD-Core Version:    0.7.0.1
 */
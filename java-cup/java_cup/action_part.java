/*  1:   */ package java_cup;
/*  2:   */ 
/*  3:   */ public class action_part
/*  4:   */   extends production_part
/*  5:   */ {
/*  6:   */   protected String _code_string;
/*  7:   */   
/*  8:   */   public action_part(String code_str)
/*  9:   */   {
/* 10:26 */     super(null);
/* 11:27 */     this._code_string = code_str;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String code_string()
/* 15:   */   {
/* 16:40 */     return this._code_string;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void set_code_string(String new_str)
/* 20:   */   {
/* 21:45 */     this._code_string = new_str;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean is_action()
/* 25:   */   {
/* 26:52 */     return true;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean equals(action_part other)
/* 30:   */   {
/* 31:60 */     return (other != null) && (super.equals(other)) && (other.code_string().equals(code_string()));
/* 32:   */   }
/* 33:   */   
/* 34:   */   public boolean equals(Object other)
/* 35:   */   {
/* 36:69 */     if (!(other instanceof action_part)) {
/* 37:70 */       return false;
/* 38:   */     }
/* 39:72 */     return equals((action_part)other);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public int hashCode()
/* 43:   */   {
/* 44:80 */     return super.hashCode() ^ (code_string() == null ? 0 : code_string().hashCode());
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String toString()
/* 48:   */   {
/* 49:89 */     return super.toString() + "{" + code_string() + "}";
/* 50:   */   }
/* 51:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.action_part
 * JD-Core Version:    0.7.0.1
 */
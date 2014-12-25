/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ public class lr_item_core
/*   4:    */ {
/*   5:    */   protected production _the_production;
/*   6:    */   protected int _dot_pos;
/*   7:    */   protected int _core_hash_cache;
/*   8:    */   
/*   9:    */   public lr_item_core(production prod, int pos)
/*  10:    */     throws internal_error
/*  11:    */   {
/*  12: 35 */     symbol after_dot = null;
/*  13: 38 */     if (prod == null) {
/*  14: 39 */       throw new internal_error("Attempt to create an lr_item_core with a null production");
/*  15:    */     }
/*  16: 42 */     this._the_production = prod;
/*  17: 44 */     if ((pos < 0) || (pos > this._the_production.rhs_length())) {
/*  18: 45 */       throw new internal_error("Attempt to create an lr_item_core with a bad dot position");
/*  19:    */     }
/*  20: 48 */     this._dot_pos = pos;
/*  21:    */     
/*  22:    */ 
/*  23: 51 */     this._core_hash_cache = (13 * this._the_production.hashCode() + pos);
/*  24: 54 */     if (this._dot_pos < this._the_production.rhs_length())
/*  25:    */     {
/*  26: 56 */       production_part part = this._the_production.rhs(this._dot_pos);
/*  27: 57 */       if (!part.is_action()) {
/*  28: 58 */         this._symbol_after_dot = ((symbol_part)part).the_symbol();
/*  29:    */       }
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public lr_item_core(production prod)
/*  34:    */     throws internal_error
/*  35:    */   {
/*  36: 69 */     this(prod, 0);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public production the_production()
/*  40:    */   {
/*  41: 80 */     return this._the_production;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int dot_pos()
/*  45:    */   {
/*  46: 94 */     return this._dot_pos;
/*  47:    */   }
/*  48:    */   
/*  49:104 */   protected symbol _symbol_after_dot = null;
/*  50:    */   
/*  51:    */   public boolean dot_at_end()
/*  52:    */   {
/*  53:111 */     return this._dot_pos >= this._the_production.rhs_length();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public symbol symbol_after_dot()
/*  57:    */   {
/*  58:121 */     return this._symbol_after_dot;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public non_terminal dot_before_nt()
/*  62:    */   {
/*  63:134 */     symbol sym = symbol_after_dot();
/*  64:137 */     if ((sym != null) && (sym.is_non_term())) {
/*  65:138 */       return (non_terminal)sym;
/*  66:    */     }
/*  67:140 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public lr_item_core shift_core()
/*  71:    */     throws internal_error
/*  72:    */   {
/*  73:150 */     if (dot_at_end()) {
/*  74:151 */       throw new internal_error("Attempt to shift past end of an lr_item_core");
/*  75:    */     }
/*  76:154 */     return new lr_item_core(this._the_production, this._dot_pos + 1);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean core_equals(lr_item_core other)
/*  80:    */   {
/*  81:164 */     return (other != null) && (this._the_production.equals(other._the_production)) && (this._dot_pos == other._dot_pos);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean equals(lr_item_core other)
/*  85:    */   {
/*  86:172 */     return core_equals(other);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean equals(Object other)
/*  90:    */   {
/*  91:179 */     if (!(other instanceof lr_item_core)) {
/*  92:180 */       return false;
/*  93:    */     }
/*  94:182 */     return equals((lr_item_core)other);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int core_hashCode()
/*  98:    */   {
/*  99:190 */     return this._core_hash_cache;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int hashCode()
/* 103:    */   {
/* 104:198 */     return this._core_hash_cache;
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected int obj_hash()
/* 108:    */   {
/* 109:208 */     return super.hashCode();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String to_simple_string()
/* 113:    */     throws internal_error
/* 114:    */   {
/* 115:    */     String result;
/* 116:221 */     if ((this._the_production.lhs() != null) && (this._the_production.lhs().the_symbol() != null) && (this._the_production.lhs().the_symbol().name() != null)) {
/* 117:224 */       result = this._the_production.lhs().the_symbol().name();
/* 118:    */     } else {
/* 119:226 */       result = "$$NULL$$";
/* 120:    */     }
/* 121:228 */     String result = result + " ::= ";
/* 122:230 */     for (int i = 0; i < this._the_production.rhs_length(); i++)
/* 123:    */     {
/* 124:233 */       if (i == this._dot_pos) {
/* 125:234 */         result = result + "(*) ";
/* 126:    */       }
/* 127:237 */       if (this._the_production.rhs(i) == null)
/* 128:    */       {
/* 129:239 */         result = result + "$$NULL$$ ";
/* 130:    */       }
/* 131:    */       else
/* 132:    */       {
/* 133:243 */         production_part part = this._the_production.rhs(i);
/* 134:244 */         if (part == null) {
/* 135:245 */           result = result + "$$NULL$$ ";
/* 136:246 */         } else if (part.is_action()) {
/* 137:247 */           result = result + "{ACTION} ";
/* 138:248 */         } else if ((((symbol_part)part).the_symbol() != null) && (((symbol_part)part).the_symbol().name() != null)) {
/* 139:250 */           result = result + ((symbol_part)part).the_symbol().name() + " ";
/* 140:    */         } else {
/* 141:252 */           result = result + "$$NULL$$ ";
/* 142:    */         }
/* 143:    */       }
/* 144:    */     }
/* 145:257 */     if (this._dot_pos == this._the_production.rhs_length()) {
/* 146:258 */       result = result + "(*) ";
/* 147:    */     }
/* 148:260 */     return result;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String toString()
/* 152:    */   {
/* 153:    */     try
/* 154:    */     {
/* 155:270 */       return to_simple_string();
/* 156:    */     }
/* 157:    */     catch (internal_error e)
/* 158:    */     {
/* 159:272 */       e.crash();
/* 160:    */     }
/* 161:273 */     return null;
/* 162:    */   }
/* 163:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.lr_item_core
 * JD-Core Version:    0.7.0.1
 */
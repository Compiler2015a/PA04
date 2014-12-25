/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ import java.util.Stack;
/*   4:    */ 
/*   5:    */ public class lalr_item
/*   6:    */   extends lr_item_core
/*   7:    */ {
/*   8:    */   protected terminal_set _lookahead;
/*   9:    */   protected Stack _propagate_items;
/*  10:    */   protected boolean needs_propagation;
/*  11:    */   
/*  12:    */   public lalr_item(production prod, int pos, terminal_set look)
/*  13:    */     throws internal_error
/*  14:    */   {
/*  15: 46 */     super(prod, pos);
/*  16: 47 */     this._lookahead = look;
/*  17: 48 */     this._propagate_items = new Stack();
/*  18: 49 */     this.needs_propagation = true;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public lalr_item(production prod, terminal_set look)
/*  22:    */     throws internal_error
/*  23:    */   {
/*  24: 60 */     this(prod, 0, look);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public lalr_item(production prod)
/*  28:    */     throws internal_error
/*  29:    */   {
/*  30: 70 */     this(prod, 0, new terminal_set());
/*  31:    */   }
/*  32:    */   
/*  33:    */   public terminal_set lookahead()
/*  34:    */   {
/*  35: 81 */     return this._lookahead;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Stack propagate_items()
/*  39:    */   {
/*  40: 89 */     return this._propagate_items;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void add_propagate(lalr_item prop_to)
/*  44:    */   {
/*  45:103 */     this._propagate_items.push(prop_to);
/*  46:104 */     this.needs_propagation = true;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void propagate_lookaheads(terminal_set incoming)
/*  50:    */     throws internal_error
/*  51:    */   {
/*  52:117 */     boolean change = false;
/*  53:120 */     if ((!this.needs_propagation) && ((incoming == null) || (incoming.empty()))) {
/*  54:121 */       return;
/*  55:    */     }
/*  56:124 */     if (incoming != null) {
/*  57:127 */       change = lookahead().add(incoming);
/*  58:    */     }
/*  59:131 */     if ((change) || (this.needs_propagation))
/*  60:    */     {
/*  61:134 */       this.needs_propagation = false;
/*  62:137 */       for (int i = 0; i < propagate_items().size(); i++) {
/*  63:138 */         ((lalr_item)propagate_items().elementAt(i)).propagate_lookaheads(lookahead());
/*  64:    */       }
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public lalr_item shift()
/*  69:    */     throws internal_error
/*  70:    */   {
/*  71:153 */     if (dot_at_end()) {
/*  72:154 */       throw new internal_error("Attempt to shift past end of an lalr_item");
/*  73:    */     }
/*  74:157 */     lalr_item result = new lalr_item(the_production(), dot_pos() + 1, new terminal_set(lookahead()));
/*  75:    */     
/*  76:    */ 
/*  77:    */ 
/*  78:161 */     add_propagate(result);
/*  79:    */     
/*  80:163 */     return result;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public terminal_set calc_lookahead(terminal_set lookahead_after)
/*  84:    */     throws internal_error
/*  85:    */   {
/*  86:182 */     if (dot_at_end()) {
/*  87:183 */       throw new internal_error("Attempt to calculate a lookahead set with a completed item");
/*  88:    */     }
/*  89:187 */     terminal_set result = new terminal_set();
/*  90:190 */     for (int pos = dot_pos() + 1; pos < the_production().rhs_length(); pos++)
/*  91:    */     {
/*  92:192 */       production_part part = the_production().rhs(pos);
/*  93:195 */       if (!part.is_action())
/*  94:    */       {
/*  95:197 */         symbol sym = ((symbol_part)part).the_symbol();
/*  96:200 */         if (!sym.is_non_term())
/*  97:    */         {
/*  98:202 */           result.add((terminal)sym);
/*  99:203 */           return result;
/* 100:    */         }
/* 101:208 */         result.add(((non_terminal)sym).first_set());
/* 102:211 */         if (!((non_terminal)sym).nullable()) {
/* 103:212 */           return result;
/* 104:    */         }
/* 105:    */       }
/* 106:    */     }
/* 107:219 */     result.add(lookahead_after);
/* 108:220 */     return result;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean lookahead_visible()
/* 112:    */     throws internal_error
/* 113:    */   {
/* 114:239 */     if (dot_at_end()) {
/* 115:239 */       return true;
/* 116:    */     }
/* 117:242 */     for (int pos = dot_pos() + 1; pos < the_production().rhs_length(); pos++)
/* 118:    */     {
/* 119:244 */       production_part part = the_production().rhs(pos);
/* 120:247 */       if (!part.is_action())
/* 121:    */       {
/* 122:249 */         symbol sym = ((symbol_part)part).the_symbol();
/* 123:252 */         if (!sym.is_non_term()) {
/* 124:252 */           return false;
/* 125:    */         }
/* 126:255 */         if (!((non_terminal)sym).nullable()) {
/* 127:255 */           return false;
/* 128:    */         }
/* 129:    */       }
/* 130:    */     }
/* 131:260 */     return true;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean equals(lalr_item other)
/* 135:    */   {
/* 136:271 */     if (other == null) {
/* 137:271 */       return false;
/* 138:    */     }
/* 139:272 */     return super.equals(other);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean equals(Object other)
/* 143:    */   {
/* 144:280 */     if (!(other instanceof lalr_item)) {
/* 145:281 */       return false;
/* 146:    */     }
/* 147:283 */     return equals((lalr_item)other);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int hashCode()
/* 151:    */   {
/* 152:293 */     return super.hashCode();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String toString()
/* 156:    */   {
/* 157:301 */     String result = "";
/* 158:    */     
/* 159:    */ 
/* 160:    */ 
/* 161:305 */     result = result + "[";
/* 162:306 */     result = result + super.toString();
/* 163:307 */     result = result + ", ";
/* 164:308 */     if (lookahead() != null)
/* 165:    */     {
/* 166:310 */       result = result + "{";
/* 167:311 */       for (int t = 0; t < terminal.number(); t++) {
/* 168:312 */         if (lookahead().contains(t)) {
/* 169:313 */           result = result + terminal.find(t).name() + " ";
/* 170:    */         }
/* 171:    */       }
/* 172:314 */       result = result + "}";
/* 173:    */     }
/* 174:    */     else
/* 175:    */     {
/* 176:317 */       result = result + "NULL LOOKAHEAD!!";
/* 177:    */     }
/* 178:318 */     result = result + "]";
/* 179:    */     
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:327 */     return result;
/* 188:    */   }
/* 189:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.lalr_item
 * JD-Core Version:    0.7.0.1
 */
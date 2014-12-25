/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ 
/*   6:    */ public class lalr_item_set
/*   7:    */ {
/*   8:    */   public lalr_item_set() {}
/*   9:    */   
/*  10:    */   public lalr_item_set(lalr_item_set other)
/*  11:    */     throws internal_error
/*  12:    */   {
/*  13: 38 */     not_null(other);
/*  14: 39 */     this._all = ((Hashtable)other._all.clone());
/*  15:    */   }
/*  16:    */   
/*  17: 49 */   protected Hashtable _all = new Hashtable(11);
/*  18:    */   
/*  19:    */   public Enumeration all()
/*  20:    */   {
/*  21: 52 */     return this._all.elements();
/*  22:    */   }
/*  23:    */   
/*  24: 57 */   protected Integer hashcode_cache = null;
/*  25:    */   
/*  26:    */   public int size()
/*  27:    */   {
/*  28: 62 */     return this._all.size();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean contains(lalr_item itm)
/*  32:    */   {
/*  33: 71 */     return this._all.containsKey(itm);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public lalr_item find(lalr_item itm)
/*  37:    */   {
/*  38: 79 */     return (lalr_item)this._all.get(itm);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean is_subset_of(lalr_item_set other)
/*  42:    */     throws internal_error
/*  43:    */   {
/*  44: 88 */     not_null(other);
/*  45: 91 */     for (Enumeration e = all(); e.hasMoreElements();) {
/*  46: 92 */       if (!other.contains((lalr_item)e.nextElement())) {
/*  47: 93 */         return false;
/*  48:    */       }
/*  49:    */     }
/*  50: 96 */     return true;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean is_superset_of(lalr_item_set other)
/*  54:    */     throws internal_error
/*  55:    */   {
/*  56:106 */     not_null(other);
/*  57:107 */     return other.is_subset_of(this);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public lalr_item add(lalr_item itm)
/*  61:    */     throws internal_error
/*  62:    */   {
/*  63:121 */     not_null(itm);
/*  64:    */     
/*  65:    */ 
/*  66:124 */     lalr_item other = (lalr_item)this._all.get(itm);
/*  67:127 */     if (other != null)
/*  68:    */     {
/*  69:129 */       other.lookahead().add(itm.lookahead());
/*  70:130 */       return other;
/*  71:    */     }
/*  72:136 */     this.hashcode_cache = null;
/*  73:    */     
/*  74:138 */     this._all.put(itm, itm);
/*  75:139 */     return itm;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void remove(lalr_item itm)
/*  79:    */     throws internal_error
/*  80:    */   {
/*  81:150 */     not_null(itm);
/*  82:    */     
/*  83:    */ 
/*  84:153 */     this.hashcode_cache = null;
/*  85:    */     
/*  86:    */ 
/*  87:156 */     this._all.remove(itm);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void add(lalr_item_set other)
/*  91:    */     throws internal_error
/*  92:    */   {
/*  93:167 */     not_null(other);
/*  94:170 */     for (Enumeration e = other.all(); e.hasMoreElements();) {
/*  95:171 */       add((lalr_item)e.nextElement());
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void remove(lalr_item_set other)
/* 100:    */     throws internal_error
/* 101:    */   {
/* 102:181 */     not_null(other);
/* 103:184 */     for (Enumeration e = other.all(); e.hasMoreElements();) {
/* 104:185 */       remove((lalr_item)e.nextElement());
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public lalr_item get_one()
/* 109:    */     throws internal_error
/* 110:    */   {
/* 111:196 */     Enumeration the_set = all();
/* 112:197 */     if (the_set.hasMoreElements())
/* 113:    */     {
/* 114:199 */       lalr_item result = (lalr_item)the_set.nextElement();
/* 115:200 */       remove(result);
/* 116:201 */       return result;
/* 117:    */     }
/* 118:204 */     return null;
/* 119:    */   }
/* 120:    */   
/* 121:    */   protected void not_null(Object obj)
/* 122:    */     throws internal_error
/* 123:    */   {
/* 124:217 */     if (obj == null) {
/* 125:218 */       throw new internal_error("Null object used in set operation");
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void compute_closure()
/* 130:    */     throws internal_error
/* 131:    */   {
/* 132:252 */     this.hashcode_cache = null;
/* 133:    */     
/* 134:    */ 
/* 135:255 */     lalr_item_set consider = new lalr_item_set(this);
/* 136:    */     lalr_item itm;
/* 137:    */     terminal_set new_lookaheads;
/* 138:    */     boolean need_prop;
/* 139:    */     Enumeration p;
/* 140:258 */     while (consider.size() > 0)
/* 141:    */     {
/* 142:261 */       itm = consider.get_one();
/* 143:    */       
/* 144:    */ 
/* 145:264 */       non_terminal nt = itm.dot_before_nt();
/* 146:265 */       if (nt != null)
/* 147:    */       {
/* 148:268 */         new_lookaheads = itm.calc_lookahead(itm.lookahead());
/* 149:    */         
/* 150:    */ 
/* 151:271 */         need_prop = itm.lookahead_visible();
/* 152:274 */         for (p = nt.productions(); p.hasMoreElements();)
/* 153:    */         {
/* 154:276 */           production prod = (production)p.nextElement();
/* 155:    */           
/* 156:    */ 
/* 157:279 */           lalr_item new_itm = new lalr_item(prod, new terminal_set(new_lookaheads));
/* 158:    */           
/* 159:    */ 
/* 160:    */ 
/* 161:283 */           lalr_item add_itm = add(new_itm);
/* 162:285 */           if (need_prop) {
/* 163:286 */             itm.add_propagate(add_itm);
/* 164:    */           }
/* 165:289 */           if (add_itm == new_itm) {
/* 166:292 */             consider.add(new_itm);
/* 167:    */           }
/* 168:    */         }
/* 169:    */       }
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public boolean equals(lalr_item_set other)
/* 174:    */   {
/* 175:304 */     if ((other == null) || (other.size() != size())) {
/* 176:304 */       return false;
/* 177:    */     }
/* 178:    */     try
/* 179:    */     {
/* 180:308 */       return is_subset_of(other);
/* 181:    */     }
/* 182:    */     catch (internal_error e)
/* 183:    */     {
/* 184:311 */       e.crash();
/* 185:    */     }
/* 186:312 */     return false;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean equals(Object other)
/* 190:    */   {
/* 191:322 */     if (!(other instanceof lalr_item_set)) {
/* 192:323 */       return false;
/* 193:    */     }
/* 194:325 */     return equals((lalr_item_set)other);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public int hashCode()
/* 198:    */   {
/* 199:333 */     int result = 0;
/* 200:338 */     if (this.hashcode_cache == null)
/* 201:    */     {
/* 202:344 */       Enumeration e = all();
/* 203:344 */       for (int cnt = 0; e.hasMoreElements(); cnt++) {
/* 204:345 */         result ^= ((lalr_item)e.nextElement()).hashCode();
/* 205:    */       }
/* 206:347 */       this.hashcode_cache = new Integer(result);
/* 207:    */     }
/* 208:350 */     return this.hashcode_cache.intValue();
/* 209:    */   }
/* 210:    */   
/* 211:    */   public String toString()
/* 212:    */   {
/* 213:358 */     StringBuffer result = new StringBuffer();
/* 214:    */     
/* 215:360 */     result.append("{\n");
/* 216:361 */     for (Enumeration e = all(); e.hasMoreElements();) {
/* 217:363 */       result.append("  " + (lalr_item)e.nextElement() + "\n");
/* 218:    */     }
/* 219:365 */     result.append("}");
/* 220:    */     
/* 221:367 */     return result.toString();
/* 222:    */   }
/* 223:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.lalr_item_set
 * JD-Core Version:    0.7.0.1
 */
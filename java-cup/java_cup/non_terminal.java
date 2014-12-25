/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ 
/*   6:    */ public class non_terminal
/*   7:    */   extends symbol
/*   8:    */ {
/*   9:    */   public non_terminal(String nm, String tp)
/*  10:    */   {
/*  11: 28 */     super(nm, tp);
/*  12:    */     
/*  13:    */ 
/*  14: 31 */     Object conflict = _all.put(nm, this);
/*  15: 32 */     if (conflict != null) {
/*  16: 37 */       new internal_error("Duplicate non-terminal (" + nm + ") created").crash();
/*  17:    */     }
/*  18: 40 */     this._index = (next_index++);
/*  19:    */     
/*  20:    */ 
/*  21: 43 */     _all_by_index.put(new Integer(this._index), this);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public non_terminal(String nm)
/*  25:    */   {
/*  26: 53 */     this(nm, null);
/*  27:    */   }
/*  28:    */   
/*  29: 63 */   protected static Hashtable _all = new Hashtable();
/*  30:    */   
/*  31:    */   public static void clear()
/*  32:    */   {
/*  33: 67 */     _all.clear();
/*  34: 68 */     _all_by_index.clear();
/*  35: 69 */     next_index = 0;
/*  36: 70 */     next_nt = 0;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static Enumeration all()
/*  40:    */   {
/*  41: 74 */     return _all.elements();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static non_terminal find(String with_name)
/*  45:    */   {
/*  46: 79 */     if (with_name == null) {
/*  47: 80 */       return null;
/*  48:    */     }
/*  49: 82 */     return (non_terminal)_all.get(with_name);
/*  50:    */   }
/*  51:    */   
/*  52: 88 */   protected static Hashtable _all_by_index = new Hashtable();
/*  53:    */   
/*  54:    */   public static non_terminal find(int indx)
/*  55:    */   {
/*  56: 93 */     Integer the_indx = new Integer(indx);
/*  57:    */     
/*  58: 95 */     return (non_terminal)_all_by_index.get(the_indx);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static int number()
/*  62:    */   {
/*  63:101 */     return _all.size();
/*  64:    */   }
/*  65:    */   
/*  66:106 */   protected static int next_index = 0;
/*  67:111 */   protected static int next_nt = 0;
/*  68:116 */   public static final non_terminal START_nt = new non_terminal("$START");
/*  69:121 */   public boolean is_embedded_action = false;
/*  70:    */   
/*  71:    */   static non_terminal create_new(String prefix)
/*  72:    */     throws internal_error
/*  73:    */   {
/*  74:133 */     return create_new(prefix, null);
/*  75:    */   }
/*  76:    */   
/*  77:    */   static non_terminal create_new()
/*  78:    */     throws internal_error
/*  79:    */   {
/*  80:141 */     return create_new(null);
/*  81:    */   }
/*  82:    */   
/*  83:    */   static non_terminal create_new(String prefix, String type)
/*  84:    */     throws internal_error
/*  85:    */   {
/*  86:147 */     if (prefix == null) {
/*  87:147 */       prefix = "NT$";
/*  88:    */     }
/*  89:148 */     return new non_terminal(prefix + next_nt++, type);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static void compute_nullability()
/*  93:    */     throws internal_error
/*  94:    */   {
/*  95:155 */     boolean change = true;
/*  96:    */     Enumeration e;
/*  97:161 */     while (change)
/*  98:    */     {
/*  99:164 */       change = false;
/* 100:167 */       for (e = all(); e.hasMoreElements();)
/* 101:    */       {
/* 102:169 */         non_terminal nt = (non_terminal)e.nextElement();
/* 103:172 */         if ((!nt.nullable()) && 
/* 104:    */         
/* 105:174 */           (nt.looks_nullable()))
/* 106:    */         {
/* 107:176 */           nt._nullable = true;
/* 108:177 */           change = true;
/* 109:    */         }
/* 110:    */       }
/* 111:    */     }
/* 112:184 */     for (Enumeration e = production.all(); e.hasMoreElements();)
/* 113:    */     {
/* 114:186 */       production prod = (production)e.nextElement();
/* 115:187 */       prod.set_nullable(prod.check_nullable());
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static void compute_first_sets()
/* 120:    */     throws internal_error
/* 121:    */   {
/* 122:198 */     boolean change = true;
/* 123:    */     Enumeration n;
/* 124:206 */     if (change)
/* 125:    */     {
/* 126:209 */       change = false;
/* 127:212 */       for (n = all(); n.hasMoreElements();)
/* 128:    */       {
/* 129:214 */         nt = (non_terminal)n.nextElement();
/* 130:217 */         for (p = nt.productions(); p.hasMoreElements();)
/* 131:    */         {
/* 132:219 */           production prod = (production)p.nextElement();
/* 133:    */           
/* 134:    */ 
/* 135:222 */           terminal_set prod_first = prod.check_first_set();
/* 136:225 */           if (!prod_first.is_subset_of(nt._first_set))
/* 137:    */           {
/* 138:227 */             change = true;
/* 139:228 */             nt._first_set.add(prod_first);
/* 140:    */           }
/* 141:    */         }
/* 142:    */       }
/* 143:    */     }
/* 144:    */     non_terminal nt;
/* 145:    */     Enumeration p;
/* 146:    */   }
/* 147:    */   
/* 148:240 */   protected Hashtable _productions = new Hashtable(11);
/* 149:    */   protected boolean _nullable;
/* 150:    */   
/* 151:    */   public Enumeration productions()
/* 152:    */   {
/* 153:243 */     return this._productions.elements();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int num_productions()
/* 157:    */   {
/* 158:248 */     return this._productions.size();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void add_production(production prod)
/* 162:    */     throws internal_error
/* 163:    */   {
/* 164:256 */     if ((prod == null) || (prod.lhs() == null) || (prod.lhs().the_symbol() != this)) {
/* 165:257 */       throw new internal_error("Attempt to add invalid production to non terminal production table");
/* 166:    */     }
/* 167:261 */     this._productions.put(prod, prod);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean nullable()
/* 171:    */   {
/* 172:270 */     return this._nullable;
/* 173:    */   }
/* 174:    */   
/* 175:275 */   protected terminal_set _first_set = new terminal_set();
/* 176:    */   
/* 177:    */   public terminal_set first_set()
/* 178:    */   {
/* 179:278 */     return this._first_set;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean is_non_term()
/* 183:    */   {
/* 184:287 */     return true;
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected boolean looks_nullable()
/* 188:    */     throws internal_error
/* 189:    */   {
/* 190:296 */     for (Enumeration e = productions(); e.hasMoreElements();) {
/* 191:298 */       if (((production)e.nextElement()).check_nullable()) {
/* 192:299 */         return true;
/* 193:    */       }
/* 194:    */     }
/* 195:302 */     return false;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String toString()
/* 199:    */   {
/* 200:310 */     return super.toString() + "[" + index() + "]" + (nullable() ? "*" : "");
/* 201:    */   }
/* 202:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.non_terminal
 * JD-Core Version:    0.7.0.1
 */
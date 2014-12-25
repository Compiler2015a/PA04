/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ 
/*   6:    */ public class symbol_set
/*   7:    */ {
/*   8:    */   public symbol_set() {}
/*   9:    */   
/*  10:    */   public symbol_set(symbol_set other)
/*  11:    */     throws internal_error
/*  12:    */   {
/*  13: 28 */     not_null(other);
/*  14: 29 */     this._all = ((Hashtable)other._all.clone());
/*  15:    */   }
/*  16:    */   
/*  17: 38 */   protected Hashtable _all = new Hashtable(11);
/*  18:    */   
/*  19:    */   public Enumeration all()
/*  20:    */   {
/*  21: 41 */     return this._all.elements();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public int size()
/*  25:    */   {
/*  26: 44 */     return this._all.size();
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected void not_null(Object obj)
/*  30:    */     throws internal_error
/*  31:    */   {
/*  32: 56 */     if (obj == null) {
/*  33: 57 */       throw new internal_error("Null object used in set operation");
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean contains(symbol sym)
/*  38:    */   {
/*  39: 65 */     return this._all.containsKey(sym.name());
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean is_subset_of(symbol_set other)
/*  43:    */     throws internal_error
/*  44:    */   {
/*  45: 74 */     not_null(other);
/*  46: 77 */     for (Enumeration e = all(); e.hasMoreElements();) {
/*  47: 78 */       if (!other.contains((symbol)e.nextElement())) {
/*  48: 79 */         return false;
/*  49:    */       }
/*  50:    */     }
/*  51: 82 */     return true;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean is_superset_of(symbol_set other)
/*  55:    */     throws internal_error
/*  56:    */   {
/*  57: 92 */     not_null(other);
/*  58: 93 */     return other.is_subset_of(this);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean add(symbol sym)
/*  62:    */     throws internal_error
/*  63:    */   {
/*  64:106 */     not_null(sym);
/*  65:    */     
/*  66:    */ 
/*  67:109 */     Object previous = this._all.put(sym.name(), sym);
/*  68:    */     
/*  69:    */ 
/*  70:112 */     return previous == null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void remove(symbol sym)
/*  74:    */     throws internal_error
/*  75:    */   {
/*  76:122 */     not_null(sym);
/*  77:123 */     this._all.remove(sym.name());
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean add(symbol_set other)
/*  81:    */     throws internal_error
/*  82:    */   {
/*  83:134 */     boolean result = false;
/*  84:    */     
/*  85:136 */     not_null(other);
/*  86:139 */     for (Enumeration e = other.all(); e.hasMoreElements();) {
/*  87:140 */       result = (add((symbol)e.nextElement())) || (result);
/*  88:    */     }
/*  89:142 */     return result;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void remove(symbol_set other)
/*  93:    */     throws internal_error
/*  94:    */   {
/*  95:152 */     not_null(other);
/*  96:155 */     for (Enumeration e = other.all(); e.hasMoreElements();) {
/*  97:156 */       remove((symbol)e.nextElement());
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean equals(symbol_set other)
/* 102:    */   {
/* 103:164 */     if ((other == null) || (other.size() != size())) {
/* 104:164 */       return false;
/* 105:    */     }
/* 106:    */     try
/* 107:    */     {
/* 108:168 */       return is_subset_of(other);
/* 109:    */     }
/* 110:    */     catch (internal_error e)
/* 111:    */     {
/* 112:171 */       e.crash();
/* 113:    */     }
/* 114:172 */     return false;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean equals(Object other)
/* 118:    */   {
/* 119:181 */     if (!(other instanceof symbol_set)) {
/* 120:182 */       return false;
/* 121:    */     }
/* 122:184 */     return equals((symbol_set)other);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int hashCode()
/* 126:    */   {
/* 127:192 */     int result = 0;
/* 128:    */     
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:197 */     Enumeration e = all();
/* 133:197 */     for (int cnt = 0; (e.hasMoreElements()) && (cnt < 5); cnt++) {
/* 134:198 */       result ^= ((symbol)e.nextElement()).hashCode();
/* 135:    */     }
/* 136:200 */     return result;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String toString()
/* 140:    */   {
/* 141:211 */     String result = "{";
/* 142:212 */     boolean comma_flag = false;
/* 143:213 */     for (Enumeration e = all(); e.hasMoreElements();)
/* 144:    */     {
/* 145:215 */       if (comma_flag) {
/* 146:216 */         result = result + ", ";
/* 147:    */       } else {
/* 148:218 */         comma_flag = true;
/* 149:    */       }
/* 150:220 */       result = result + ((symbol)e.nextElement()).name();
/* 151:    */     }
/* 152:222 */     result = result + "}";
/* 153:    */     
/* 154:224 */     return result;
/* 155:    */   }
/* 156:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.symbol_set
 * JD-Core Version:    0.7.0.1
 */
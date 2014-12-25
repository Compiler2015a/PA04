/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ import java.util.BitSet;
/*   4:    */ 
/*   5:    */ public class terminal_set
/*   6:    */ {
/*   7:    */   public terminal_set()
/*   8:    */   {
/*   9: 20 */     this._elements = new BitSet(terminal.number());
/*  10:    */   }
/*  11:    */   
/*  12:    */   public terminal_set(terminal_set other)
/*  13:    */     throws internal_error
/*  14:    */   {
/*  15: 31 */     not_null(other);
/*  16: 32 */     this._elements = ((BitSet)other._elements.clone());
/*  17:    */   }
/*  18:    */   
/*  19: 40 */   public static final terminal_set EMPTY = new terminal_set();
/*  20:    */   protected BitSet _elements;
/*  21:    */   
/*  22:    */   protected void not_null(Object obj)
/*  23:    */     throws internal_error
/*  24:    */   {
/*  25: 59 */     if (obj == null) {
/*  26: 60 */       throw new internal_error("Null object used in set operation");
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean empty()
/*  31:    */   {
/*  32: 68 */     return equals(EMPTY);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean contains(terminal sym)
/*  36:    */     throws internal_error
/*  37:    */   {
/*  38: 79 */     not_null(sym);
/*  39: 80 */     return this._elements.get(sym.index());
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean contains(int indx)
/*  43:    */   {
/*  44: 90 */     return this._elements.get(indx);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean is_subset_of(terminal_set other)
/*  48:    */     throws internal_error
/*  49:    */   {
/*  50:101 */     not_null(other);
/*  51:    */     
/*  52:    */ 
/*  53:104 */     BitSet copy_other = (BitSet)other._elements.clone();
/*  54:    */     
/*  55:    */ 
/*  56:107 */     copy_other.or(this._elements);
/*  57:    */     
/*  58:    */ 
/*  59:110 */     return copy_other.equals(other._elements);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean is_superset_of(terminal_set other)
/*  63:    */     throws internal_error
/*  64:    */   {
/*  65:121 */     not_null(other);
/*  66:122 */     return other.is_subset_of(this);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean add(terminal sym)
/*  70:    */     throws internal_error
/*  71:    */   {
/*  72:136 */     not_null(sym);
/*  73:    */     
/*  74:    */ 
/*  75:139 */     boolean result = this._elements.get(sym.index());
/*  76:142 */     if (!result) {
/*  77:143 */       this._elements.set(sym.index());
/*  78:    */     }
/*  79:145 */     return result;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void remove(terminal sym)
/*  83:    */     throws internal_error
/*  84:    */   {
/*  85:156 */     not_null(sym);
/*  86:157 */     this._elements.clear(sym.index());
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean add(terminal_set other)
/*  90:    */     throws internal_error
/*  91:    */   {
/*  92:169 */     not_null(other);
/*  93:    */     
/*  94:    */ 
/*  95:172 */     BitSet copy = (BitSet)this._elements.clone();
/*  96:    */     
/*  97:    */ 
/*  98:175 */     this._elements.or(other._elements);
/*  99:    */     
/* 100:    */ 
/* 101:178 */     return !this._elements.equals(copy);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean intersects(terminal_set other)
/* 105:    */     throws internal_error
/* 106:    */   {
/* 107:189 */     not_null(other);
/* 108:190 */     return this._elements.intersects(other._elements);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean equals(terminal_set other)
/* 112:    */   {
/* 113:198 */     if (other == null) {
/* 114:199 */       return false;
/* 115:    */     }
/* 116:201 */     return this._elements.equals(other._elements);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean equals(Object other)
/* 120:    */   {
/* 121:209 */     if (!(other instanceof terminal_set)) {
/* 122:210 */       return false;
/* 123:    */     }
/* 124:212 */     return equals((terminal_set)other);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String toString()
/* 128:    */   {
/* 129:223 */     String result = "{";
/* 130:224 */     boolean comma_flag = false;
/* 131:225 */     for (int t = 0; t < terminal.number(); t++) {
/* 132:227 */       if (this._elements.get(t))
/* 133:    */       {
/* 134:229 */         if (comma_flag) {
/* 135:230 */           result = result + ", ";
/* 136:    */         } else {
/* 137:232 */           comma_flag = true;
/* 138:    */         }
/* 139:234 */         result = result + terminal.find(t).name();
/* 140:    */       }
/* 141:    */     }
/* 142:237 */     result = result + "}";
/* 143:    */     
/* 144:239 */     return result;
/* 145:    */   }
/* 146:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.terminal_set
 * JD-Core Version:    0.7.0.1
 */
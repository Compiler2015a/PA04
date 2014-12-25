/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ 
/*   6:    */ public class terminal
/*   7:    */   extends symbol
/*   8:    */ {
/*   9:    */   private int _precedence_num;
/*  10:    */   private int _precedence_side;
/*  11:    */   
/*  12:    */   public terminal(String nm, String tp, int precedence_side, int precedence_num)
/*  13:    */   {
/*  14: 29 */     super(nm, tp);
/*  15:    */     
/*  16:    */ 
/*  17: 32 */     Object conflict = _all.put(nm, this);
/*  18: 33 */     if (conflict != null) {
/*  19: 38 */       new internal_error("Duplicate terminal (" + nm + ") created").crash();
/*  20:    */     }
/*  21: 41 */     this._index = (next_index++);
/*  22:    */     
/*  23:    */ 
/*  24: 44 */     this._precedence_num = precedence_num;
/*  25: 45 */     this._precedence_side = precedence_side;
/*  26:    */     
/*  27:    */ 
/*  28: 48 */     _all_by_index.put(new Integer(this._index), this);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public terminal(String nm, String tp)
/*  32:    */   {
/*  33: 58 */     this(nm, tp, -1, -1);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public terminal(String nm)
/*  37:    */   {
/*  38: 68 */     this(nm, null);
/*  39:    */   }
/*  40:    */   
/*  41: 85 */   protected static Hashtable _all = new Hashtable();
/*  42:    */   
/*  43:    */   public static void clear()
/*  44:    */   {
/*  45: 89 */     _all.clear();
/*  46: 90 */     _all_by_index.clear();
/*  47: 91 */     next_index = 0;
/*  48: 92 */     EOF = new terminal("EOF");
/*  49: 93 */     error = new terminal("error");
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static Enumeration all()
/*  53:    */   {
/*  54: 97 */     return _all.elements();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static terminal find(String with_name)
/*  58:    */   {
/*  59:102 */     if (with_name == null) {
/*  60:103 */       return null;
/*  61:    */     }
/*  62:105 */     return (terminal)_all.get(with_name);
/*  63:    */   }
/*  64:    */   
/*  65:112 */   protected static Hashtable _all_by_index = new Hashtable();
/*  66:    */   
/*  67:    */   public static terminal find(int indx)
/*  68:    */   {
/*  69:117 */     Integer the_indx = new Integer(indx);
/*  70:    */     
/*  71:119 */     return (terminal)_all_by_index.get(the_indx);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static int number()
/*  75:    */   {
/*  76:125 */     return _all.size();
/*  77:    */   }
/*  78:    */   
/*  79:130 */   protected static int next_index = 0;
/*  80:135 */   public static terminal EOF = new terminal("EOF");
/*  81:140 */   public static terminal error = new terminal("error");
/*  82:    */   
/*  83:    */   public boolean is_non_term()
/*  84:    */   {
/*  85:149 */     return false;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String toString()
/*  89:    */   {
/*  90:157 */     return super.toString() + "[" + index() + "]";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int precedence_num()
/*  94:    */   {
/*  95:164 */     return this._precedence_num;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int precedence_side()
/*  99:    */   {
/* 100:167 */     return this._precedence_side;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void set_precedence(int p, int new_prec)
/* 104:    */   {
/* 105:172 */     this._precedence_side = p;
/* 106:173 */     this._precedence_num = new_prec;
/* 107:    */   }
/* 108:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.terminal
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ import java.util.Stack;
/*   4:    */ import java_cup.runtime.ComplexSymbolFactory;
/*   5:    */ import java_cup.runtime.Scanner;
/*   6:    */ import java_cup.runtime.Symbol;
/*   7:    */ import java_cup.runtime.SymbolFactory;
/*   8:    */ import java_cup.runtime.lr_parser;
/*   9:    */ 
/*  10:    */ public class parser
/*  11:    */   extends lr_parser
/*  12:    */ {
/*  13:    */   public parser() {}
/*  14:    */   
/*  15:    */   public parser(Scanner s)
/*  16:    */   {
/*  17: 22 */     super(s);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public parser(Scanner s, SymbolFactory sf)
/*  21:    */   {
/*  22: 25 */     super(s, sf);
/*  23:    */   }
/*  24:    */   
/*  25: 28 */   protected static final short[][] _production_table = unpackFromStrings(new String[] { "" });
/*  26:    */   
/*  27:    */   public short[][] production_table()
/*  28:    */   {
/*  29: 69 */     return _production_table;
/*  30:    */   }
/*  31:    */   
/*  32: 72 */   protected static final short[][] _action_table = unpackFromStrings(new String[] { "" });
/*  33:    */   
/*  34:    */   public short[][] action_table()
/*  35:    */   {
/*  36:289 */     return _action_table;
/*  37:    */   }
/*  38:    */   
/*  39:292 */   protected static final short[][] _reduce_table = unpackFromStrings(new String[] { "" });
/*  40:    */   protected CUP.parser.actions action_obj;
/*  41:    */   protected Lexer lexer;
/*  42:    */   
/*  43:    */   public short[][] reduce_table()
/*  44:    */   {
/*  45:375 */     return _reduce_table;
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected void init_actions()
/*  49:    */   {
/*  50:383 */     this.action_obj = new CUP.parser.actions(this);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Symbol do_action(int act_num, lr_parser parser, Stack stack, int top)
/*  54:    */     throws Exception
/*  55:    */   {
/*  56:395 */     return this.action_obj.CUP$parser$do_action(act_num, parser, stack, top);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int start_state()
/*  60:    */   {
/*  61:399 */     return 0;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int start_production()
/*  65:    */   {
/*  66:401 */     return 0;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int EOF_sym()
/*  70:    */   {
/*  71:404 */     return 0;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int error_sym()
/*  75:    */   {
/*  76:407 */     return 1;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void user_init()
/*  80:    */     throws Exception
/*  81:    */   {
/*  82:414 */     ComplexSymbolFactory f = new ComplexSymbolFactory();
/*  83:415 */     this.symbolFactory = f;
/*  84:416 */     this.lexer = new Lexer(f);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Symbol scan()
/*  88:    */     throws Exception
/*  89:    */   {
/*  90:425 */     return this.lexer.next_token();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void report_fatal_error(String message, Object info)
/*  94:    */   {
/*  95:437 */     done_parsing();
/*  96:438 */     if ((info instanceof Symbol)) {
/*  97:438 */       ErrorManager.getManager().emit_fatal(message + "\nCan't recover from previous error(s), giving up.", (Symbol)info);
/*  98:    */     } else {
/*  99:439 */       ErrorManager.getManager().emit_fatal(message + "\nCan't recover from previous error(s), giving up.", this.cur_token);
/* 100:    */     }
/* 101:440 */     System.exit(1);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void report_error(String message, Object info)
/* 105:    */   {
/* 106:445 */     if ((info instanceof Symbol)) {
/* 107:446 */       ErrorManager.getManager().emit_error(message, (Symbol)info);
/* 108:    */     } else {
/* 109:448 */       ErrorManager.getManager().emit_error(message, this.cur_token);
/* 110:    */     }
/* 111:    */   }
/* 112:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.parser
 * JD-Core Version:    0.7.0.1
 */
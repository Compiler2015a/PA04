/*  1:   */ package java_cup;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.lang.reflect.Field;
/*  5:   */ import java.lang.reflect.Modifier;
/*  6:   */ import java_cup.runtime.Symbol;
/*  7:   */ 
/*  8:   */ public class ErrorManager
/*  9:   */ {
/* 10: 8 */   private int errors = 0;
/* 11: 9 */   private int warnings = 0;
/* 12:10 */   private int fatals = 0;
/* 13:   */   
/* 14:   */   public int getFatalCount()
/* 15:   */   {
/* 16:11 */     return this.fatals;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public int getErrorCount()
/* 20:   */   {
/* 21:12 */     return this.errors;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int getWarningCount()
/* 25:   */   {
/* 26:13 */     return this.warnings;
/* 27:   */   }
/* 28:   */   
/* 29:15 */   private static ErrorManager errorManager = new ErrorManager();
/* 30:   */   
/* 31:   */   public static ErrorManager getManager()
/* 32:   */   {
/* 33:17 */     return errorManager;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void emit_fatal(String message)
/* 37:   */   {
/* 38:28 */     System.err.println("Fatal : " + message);
/* 39:29 */     this.fatals += 1;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void emit_fatal(String message, Symbol sym)
/* 43:   */   {
/* 44:33 */     System.err.println("Fatal: " + message + " @ " + sym);
/* 45:34 */     this.fatals += 1;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void emit_warning(String message)
/* 49:   */   {
/* 50:37 */     System.err.println("Warning : " + message);
/* 51:38 */     this.warnings += 1;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void emit_warning(String message, Symbol sym)
/* 55:   */   {
/* 56:42 */     System.err.println("Fatal: " + message + " @ " + sym);
/* 57:43 */     this.warnings += 1;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void emit_error(String message)
/* 61:   */   {
/* 62:46 */     System.err.println("Error : " + message);
/* 63:47 */     this.errors += 1;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public void emit_error(String message, Symbol sym)
/* 67:   */   {
/* 68:51 */     System.err.println("Error: " + message + " @ " + sym);
/* 69:52 */     this.errors += 1;
/* 70:   */   }
/* 71:   */   
/* 72:   */   private static String convSymbol(Symbol symbol)
/* 73:   */   {
/* 74:55 */     String result = " (\"" + symbol.value.toString() + "\")";
/* 75:56 */     Field[] fields = sym.class.getFields();
/* 76:57 */     for (int i = 0; i < fields.length; i++) {
/* 77:58 */       if (Modifier.isPublic(fields[i].getModifiers())) {
/* 78:   */         try
/* 79:   */         {
/* 80:60 */           if (fields[i].getInt(null) == symbol.sym) {
/* 81:60 */             return fields[i].getName() + result;
/* 82:   */           }
/* 83:   */         }
/* 84:   */         catch (Exception ex) {}
/* 85:   */       }
/* 86:   */     }
/* 87:64 */     return symbol.toString() + result;
/* 88:   */   }
/* 89:   */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.ErrorManager
 * JD-Core Version:    0.7.0.1
 */
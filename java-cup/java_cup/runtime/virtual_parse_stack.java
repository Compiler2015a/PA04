/*   1:    */ package java_cup.runtime;
/*   2:    */ 
/*   3:    */ import java.util.Stack;
/*   4:    */ 
/*   5:    */ public class virtual_parse_stack
/*   6:    */ {
/*   7:    */   protected Stack real_stack;
/*   8:    */   protected int real_next;
/*   9:    */   protected Stack vstack;
/*  10:    */   
/*  11:    */   public virtual_parse_stack(Stack shadowing_stack)
/*  12:    */     throws Exception
/*  13:    */   {
/*  14: 31 */     if (shadowing_stack == null) {
/*  15: 32 */       throw new Exception("Internal parser error: attempt to create null virtual stack");
/*  16:    */     }
/*  17: 36 */     this.real_stack = shadowing_stack;
/*  18: 37 */     this.vstack = new Stack();
/*  19: 38 */     this.real_next = 0;
/*  20:    */     
/*  21:    */ 
/*  22: 41 */     get_from_real();
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected void get_from_real()
/*  26:    */   {
/*  27: 84 */     if (this.real_next >= this.real_stack.size()) {
/*  28: 84 */       return;
/*  29:    */     }
/*  30: 87 */     Symbol stack_sym = (Symbol)this.real_stack.elementAt(this.real_stack.size() - 1 - this.real_next);
/*  31:    */     
/*  32:    */ 
/*  33: 90 */     this.real_next += 1;
/*  34:    */     
/*  35:    */ 
/*  36: 93 */     this.vstack.push(new Integer(stack_sym.parse_state));
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean empty()
/*  40:    */   {
/*  41:103 */     return this.vstack.empty();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int top()
/*  45:    */     throws Exception
/*  46:    */   {
/*  47:111 */     if (this.vstack.empty()) {
/*  48:112 */       throw new Exception("Internal parser error: top() called on empty virtual stack");
/*  49:    */     }
/*  50:115 */     return ((Integer)this.vstack.peek()).intValue();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void pop()
/*  54:    */     throws Exception
/*  55:    */   {
/*  56:123 */     if (this.vstack.empty()) {
/*  57:124 */       throw new Exception("Internal parser error: pop from empty virtual stack");
/*  58:    */     }
/*  59:128 */     this.vstack.pop();
/*  60:131 */     if (this.vstack.empty()) {
/*  61:132 */       get_from_real();
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void push(int state_num)
/*  66:    */   {
/*  67:140 */     this.vstack.push(new Integer(state_num));
/*  68:    */   }
/*  69:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.runtime.virtual_parse_stack
 * JD-Core Version:    0.7.0.1
 */
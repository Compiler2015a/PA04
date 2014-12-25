/*    1:     */ package java_cup;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.util.Hashtable;
/*    5:     */ import java.util.Stack;
/*    6:     */ import java_cup.runtime.Symbol;
/*    7:     */ import java_cup.runtime.SymbolFactory;
/*    8:     */ import java_cup.runtime.lr_parser;
/*    9:     */ 
/*   10:     */ class CUP$parser$actions
/*   11:     */ {
/*   12:     */   protected production_part add_lab(production_part part, String lab)
/*   13:     */     throws internal_error
/*   14:     */   {
/*   15: 462 */     if ((lab == null) || (part.is_action())) {
/*   16: 462 */       return part;
/*   17:     */     }
/*   18: 465 */     return new symbol_part(((symbol_part)part).the_symbol(), lab);
/*   19:     */   }
/*   20:     */   
/*   21: 469 */   protected final int MAX_RHS = 200;
/*   22: 472 */   protected production_part[] rhs_parts = new production_part['È'];
/*   23: 475 */   protected int rhs_pos = 0;
/*   24:     */   
/*   25:     */   protected void new_rhs()
/*   26:     */   {
/*   27: 478 */     this.rhs_pos = 0;
/*   28:     */   }
/*   29:     */   
/*   30:     */   protected void add_rhs_part(production_part part)
/*   31:     */     throws Exception
/*   32:     */   {
/*   33: 483 */     if (this.rhs_pos >= 200) {
/*   34: 484 */       throw new Exception("Internal Error: Productions limited to 200 symbols and actions");
/*   35:     */     }
/*   36: 487 */     this.rhs_parts[this.rhs_pos] = part;
/*   37: 488 */     this.rhs_pos += 1;
/*   38:     */   }
/*   39:     */   
/*   40: 492 */   protected String multipart_name = new String();
/*   41: 493 */   protected Stack multipart_names = new Stack();
/*   42: 509 */   protected Hashtable symbols = new Hashtable();
/*   43: 512 */   protected Hashtable non_terms = new Hashtable();
/*   44: 515 */   protected non_terminal start_nt = null;
/*   45:     */   protected non_terminal lhs_nt;
/*   46: 521 */   int _cur_prec = 0;
/*   47: 524 */   int _cur_side = -1;
/*   48:     */   private final parser parser;
/*   49:     */   
/*   50:     */   protected void update_precedence(int p)
/*   51:     */   {
/*   52: 528 */     this._cur_side = p;
/*   53: 529 */     this._cur_prec += 1;
/*   54:     */   }
/*   55:     */   
/*   56:     */   protected void add_precedence(String term)
/*   57:     */   {
/*   58: 533 */     if (term == null)
/*   59:     */     {
/*   60: 534 */       System.err.println("Unable to add precedence to nonexistent terminal");
/*   61:     */     }
/*   62:     */     else
/*   63:     */     {
/*   64: 536 */       symbol_part sp = (symbol_part)this.symbols.get(term);
/*   65: 537 */       if (sp == null)
/*   66:     */       {
/*   67: 538 */         System.err.println("Could find terminal " + term + " while declaring precedence");
/*   68:     */       }
/*   69:     */       else
/*   70:     */       {
/*   71: 540 */         symbol sym = sp.the_symbol();
/*   72: 541 */         if ((sym instanceof terminal)) {
/*   73: 542 */           ((terminal)sym).set_precedence(this._cur_side, this._cur_prec);
/*   74:     */         } else {
/*   75: 543 */           System.err.println("Precedence declaration: Can't find terminal " + term);
/*   76:     */         }
/*   77:     */       }
/*   78:     */     }
/*   79:     */   }
/*   80:     */   
/*   81:     */   CUP$parser$actions(parser parser)
/*   82:     */   {
/*   83: 552 */     this.parser = parser;
/*   84:     */   }
/*   85:     */   
/*   86:     */   public final Symbol CUP$parser$do_action(int CUP$parser$act_num, lr_parser CUP$parser$parser, Stack CUP$parser$stack, int CUP$parser$top)
/*   87:     */     throws Exception
/*   88:     */   {
/*   89:     */     Symbol CUP$parser$result;
/*   90: 567 */     switch (CUP$parser$act_num)
/*   91:     */     {
/*   92:     */     case 116: 
/*   93: 572 */       Object RESULT = null;
/*   94:     */       
/*   95: 574 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("empty", 28, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*   96:     */       
/*   97: 576 */       return CUP$parser$result;
/*   98:     */     case 115: 
/*   99: 581 */       Object RESULT = null;
/*  100:     */       
/*  101: 583 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("opt_semi", 6, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  102:     */       
/*  103: 585 */       return CUP$parser$result;
/*  104:     */     case 114: 
/*  105: 590 */       Object RESULT = null;
/*  106:     */       
/*  107: 592 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("opt_semi", 6, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  108:     */       
/*  109: 594 */       return CUP$parser$result;
/*  110:     */     case 113: 
/*  111: 599 */       Object RESULT = null;
/*  112:     */       
/*  113: 601 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("non_terminal", 7, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  114:     */       
/*  115: 603 */       return CUP$parser$result;
/*  116:     */     case 112: 
/*  117: 608 */       Object RESULT = null;
/*  118:     */       
/*  119: 610 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("non_terminal", 7, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  120:     */       
/*  121: 612 */       return CUP$parser$result;
/*  122:     */     case 111: 
/*  123: 617 */       String RESULT = null;
/*  124:     */       
/*  125: 619 */       ErrorManager.getManager().emit_error("Illegal use of reserved word");
/*  126: 620 */       RESULT = "ILLEGAL";
/*  127:     */       
/*  128: 622 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  129:     */       
/*  130: 624 */       return CUP$parser$result;
/*  131:     */     case 110: 
/*  132: 629 */       String RESULT = null;
/*  133: 630 */       RESULT = "nonassoc";
/*  134: 631 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  135:     */       
/*  136: 633 */       return CUP$parser$result;
/*  137:     */     case 109: 
/*  138: 638 */       String RESULT = null;
/*  139: 639 */       RESULT = "right";
/*  140: 640 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  141:     */       
/*  142: 642 */       return CUP$parser$result;
/*  143:     */     case 108: 
/*  144: 647 */       String RESULT = null;
/*  145: 648 */       RESULT = "left";
/*  146: 649 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  147:     */       
/*  148: 651 */       return CUP$parser$result;
/*  149:     */     case 107: 
/*  150: 656 */       String RESULT = null;
/*  151: 657 */       RESULT = "precedence";
/*  152: 658 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  153:     */       
/*  154: 660 */       return CUP$parser$result;
/*  155:     */     case 106: 
/*  156: 665 */       String RESULT = null;
/*  157: 666 */       RESULT = "start";
/*  158: 667 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  159:     */       
/*  160: 669 */       return CUP$parser$result;
/*  161:     */     case 105: 
/*  162: 674 */       String RESULT = null;
/*  163: 675 */       RESULT = "with";
/*  164: 676 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  165:     */       
/*  166: 678 */       return CUP$parser$result;
/*  167:     */     case 104: 
/*  168: 683 */       String RESULT = null;
/*  169: 684 */       RESULT = "scan";
/*  170: 685 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  171:     */       
/*  172: 687 */       return CUP$parser$result;
/*  173:     */     case 103: 
/*  174: 692 */       String RESULT = null;
/*  175: 693 */       RESULT = "init";
/*  176: 694 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  177:     */       
/*  178: 696 */       return CUP$parser$result;
/*  179:     */     case 102: 
/*  180: 701 */       String RESULT = null;
/*  181: 702 */       RESULT = "nonterminal";
/*  182: 703 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  183:     */       
/*  184: 705 */       return CUP$parser$result;
/*  185:     */     case 101: 
/*  186: 710 */       String RESULT = null;
/*  187: 711 */       RESULT = "non";
/*  188: 712 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  189:     */       
/*  190: 714 */       return CUP$parser$result;
/*  191:     */     case 100: 
/*  192: 719 */       String RESULT = null;
/*  193: 720 */       RESULT = "terminal";
/*  194: 721 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  195:     */       
/*  196: 723 */       return CUP$parser$result;
/*  197:     */     case 99: 
/*  198: 728 */       String RESULT = null;
/*  199: 729 */       RESULT = "parser";
/*  200: 730 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  201:     */       
/*  202: 732 */       return CUP$parser$result;
/*  203:     */     case 98: 
/*  204: 737 */       String RESULT = null;
/*  205: 738 */       RESULT = "action";
/*  206: 739 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  207:     */       
/*  208: 741 */       return CUP$parser$result;
/*  209:     */     case 97: 
/*  210: 746 */       String RESULT = null;
/*  211: 747 */       RESULT = "code";
/*  212: 748 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  213:     */       
/*  214: 750 */       return CUP$parser$result;
/*  215:     */     case 96: 
/*  216: 755 */       String RESULT = null;
/*  217: 756 */       int the_idleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  218: 757 */       int the_idright = ((Symbol)CUP$parser$stack.peek()).right;
/*  219: 758 */       String the_id = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  220: 759 */       RESULT = the_id;
/*  221: 760 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("robust_id", 42, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  222:     */       
/*  223: 762 */       return CUP$parser$result;
/*  224:     */     case 95: 
/*  225: 767 */       String RESULT = null;
/*  226: 768 */       int the_idleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  227: 769 */       int the_idright = ((Symbol)CUP$parser$stack.peek()).right;
/*  228: 770 */       String the_id = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  229: 771 */       RESULT = the_id;
/*  230: 772 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("label_id", 38, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  231:     */       
/*  232: 774 */       return CUP$parser$result;
/*  233:     */     case 94: 
/*  234: 779 */       String RESULT = null;
/*  235:     */       
/*  236: 781 */       ErrorManager.getManager().emit_error("Illegal use of reserved word");
/*  237: 782 */       RESULT = "ILLEGAL";
/*  238:     */       
/*  239: 784 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("symbol_id", 37, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  240:     */       
/*  241: 786 */       return CUP$parser$result;
/*  242:     */     case 93: 
/*  243: 791 */       String RESULT = null;
/*  244: 792 */       int the_idleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  245: 793 */       int the_idright = ((Symbol)CUP$parser$stack.peek()).right;
/*  246: 794 */       String the_id = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  247: 795 */       RESULT = the_id;
/*  248: 796 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("symbol_id", 37, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  249:     */       
/*  250: 798 */       return CUP$parser$result;
/*  251:     */     case 92: 
/*  252: 803 */       String RESULT = null;
/*  253:     */       
/*  254: 805 */       ErrorManager.getManager().emit_error("Illegal use of reserved word");
/*  255: 806 */       RESULT = "ILLEGAL";
/*  256:     */       
/*  257: 808 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("nt_id", 36, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  258:     */       
/*  259: 810 */       return CUP$parser$result;
/*  260:     */     case 91: 
/*  261: 815 */       String RESULT = null;
/*  262: 816 */       int the_idleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  263: 817 */       int the_idright = ((Symbol)CUP$parser$stack.peek()).right;
/*  264: 818 */       String the_id = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  265: 819 */       RESULT = the_id;
/*  266: 820 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("nt_id", 36, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  267:     */       
/*  268: 822 */       return CUP$parser$result;
/*  269:     */     case 90: 
/*  270: 827 */       Object RESULT = null;
/*  271: 828 */       int non_term_idleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  272: 829 */       int non_term_idright = ((Symbol)CUP$parser$stack.peek()).right;
/*  273: 830 */       String non_term_id = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  274: 833 */       if (this.symbols.get(non_term_id) != null)
/*  275:     */       {
/*  276: 836 */         ErrorManager.getManager().emit_error("java_cup.runtime.Symbol \"" + non_term_id + "\" has already been declared");
/*  277:     */       }
/*  278:     */       else
/*  279:     */       {
/*  280: 841 */         if (this.multipart_name.equals("")) {
/*  281: 842 */           this.multipart_name = "Object";
/*  282:     */         }
/*  283: 845 */         non_terminal this_nt = new non_terminal(non_term_id, this.multipart_name);
/*  284:     */         
/*  285:     */ 
/*  286:     */ 
/*  287: 849 */         this.non_terms.put(non_term_id, this_nt);
/*  288:     */         
/*  289:     */ 
/*  290: 852 */         this.symbols.put(non_term_id, new symbol_part(this_nt));
/*  291:     */       }
/*  292: 855 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("new_non_term_id", 25, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  293:     */       
/*  294: 857 */       return CUP$parser$result;
/*  295:     */     case 89: 
/*  296: 862 */       Object RESULT = null;
/*  297: 863 */       int term_idleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  298: 864 */       int term_idright = ((Symbol)CUP$parser$stack.peek()).right;
/*  299: 865 */       String term_id = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  300: 868 */       if (this.symbols.get(term_id) != null)
/*  301:     */       {
/*  302: 871 */         ErrorManager.getManager().emit_error("java_cup.runtime.Symbol \"" + term_id + "\" has already been declared");
/*  303:     */       }
/*  304:     */       else
/*  305:     */       {
/*  306: 877 */         if (this.multipart_name.equals("")) {
/*  307: 878 */           this.multipart_name = "Object";
/*  308:     */         }
/*  309: 881 */         this.symbols.put(term_id, new symbol_part(new terminal(term_id, this.multipart_name)));
/*  310:     */       }
/*  311: 885 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("new_term_id", 24, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  312:     */       
/*  313: 887 */       return CUP$parser$result;
/*  314:     */     case 88: 
/*  315: 892 */       Object RESULT = null;
/*  316: 893 */       this.multipart_name = this.multipart_name.concat("[]");
/*  317: 894 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("type_id", 18, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  318:     */       
/*  319: 896 */       return CUP$parser$result;
/*  320:     */     case 87: 
/*  321: 901 */       Object RESULT = null;
/*  322:     */       
/*  323: 903 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("type_id", 18, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  324:     */       
/*  325: 905 */       return CUP$parser$result;
/*  326:     */     case 86: 
/*  327: 910 */       Object RESULT = null;
/*  328:     */       
/*  329: 912 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("import_id", 14, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  330:     */       
/*  331: 914 */       return CUP$parser$result;
/*  332:     */     case 85: 
/*  333: 919 */       Object RESULT = null;
/*  334: 920 */       this.multipart_name = this.multipart_name.concat(".*");
/*  335: 921 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("import_id", 14, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  336:     */       
/*  337: 923 */       return CUP$parser$result;
/*  338:     */     case 84: 
/*  339: 928 */       String RESULT = null;
/*  340: 929 */       RESULT = " ? super " + this.multipart_name;this.multipart_name = new String();
/*  341:     */       
/*  342: 931 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("wildcard", 45, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  343:     */       
/*  344: 933 */       return CUP$parser$result;
/*  345:     */     case 83: 
/*  346: 938 */       String RESULT = null;
/*  347: 939 */       RESULT = " ? extends " + this.multipart_name;this.multipart_name = new String();
/*  348:     */       
/*  349: 941 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("wildcard", 45, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  350:     */       
/*  351: 943 */       return CUP$parser$result;
/*  352:     */     case 82: 
/*  353: 948 */       String RESULT = null;
/*  354: 949 */       RESULT = " ? ";
/*  355: 950 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("wildcard", 45, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  356:     */       
/*  357: 952 */       return CUP$parser$result;
/*  358:     */     case 81: 
/*  359: 957 */       String RESULT = null;
/*  360: 958 */       int wleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  361: 959 */       int wright = ((Symbol)CUP$parser$stack.peek()).right;
/*  362: 960 */       String w = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  363: 961 */       RESULT = w;
/*  364: 962 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("typearguement", 44, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  365:     */       
/*  366: 964 */       return CUP$parser$result;
/*  367:     */     case 80: 
/*  368: 969 */       String RESULT = null;
/*  369: 970 */       RESULT = this.multipart_name;this.multipart_name = new String();
/*  370: 971 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("typearguement", 44, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  371:     */       
/*  372: 973 */       return CUP$parser$result;
/*  373:     */     case 79: 
/*  374: 978 */       String RESULT = null;
/*  375: 979 */       int listleft = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2)).left;
/*  376: 980 */       int listright = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2)).right;
/*  377: 981 */       String list = (String)((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2)).value;
/*  378: 982 */       int argleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  379: 983 */       int argright = ((Symbol)CUP$parser$stack.peek()).right;
/*  380: 984 */       String arg = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  381: 985 */       RESULT = list + "," + arg;
/*  382: 986 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("typearglist", 43, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  383:     */       
/*  384: 988 */       return CUP$parser$result;
/*  385:     */     case 78: 
/*  386: 993 */       String RESULT = null;
/*  387: 994 */       int argleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  388: 995 */       int argright = ((Symbol)CUP$parser$stack.peek()).right;
/*  389: 996 */       String arg = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  390: 997 */       RESULT = arg;
/*  391: 998 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("typearglist", 43, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  392:     */       
/*  393:1000 */       return CUP$parser$result;
/*  394:     */     case 77: 
/*  395:1005 */       Object RESULT = null;
/*  396:1006 */       int an_idleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  397:1007 */       int an_idright = ((Symbol)CUP$parser$stack.peek()).right;
/*  398:1008 */       String an_id = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  399:1009 */       this.multipart_name = this.multipart_name.concat(an_id);
/*  400:1010 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("multipart_id", 12, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  401:     */       
/*  402:1012 */       return CUP$parser$result;
/*  403:     */     case 76: 
/*  404:1017 */       Object RESULT = null;
/*  405:     */       
/*  406:1019 */       RESULT = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 3)).value;
/*  407:1020 */       int typesleft = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).left;
/*  408:1021 */       int typesright = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).right;
/*  409:1022 */       String types = (String)((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/*  410:1023 */       this.multipart_name = ((String)this.multipart_names.pop()).concat("<" + types + ">");
/*  411:     */       
/*  412:1025 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("multipart_id", 12, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 4), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  413:     */       
/*  414:1027 */       return CUP$parser$result;
/*  415:     */     case 75: 
/*  416:1032 */       Object RESULT = null;
/*  417:1033 */       this.multipart_names.push(this.multipart_name);this.multipart_name = "";
/*  418:1034 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("NT$13", 59, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  419:     */       
/*  420:1036 */       return CUP$parser$result;
/*  421:     */     case 74: 
/*  422:1041 */       Object RESULT = null;
/*  423:1042 */       int another_idleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  424:1043 */       int another_idright = ((Symbol)CUP$parser$stack.peek()).right;
/*  425:1044 */       String another_id = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  426:1045 */       this.multipart_name = this.multipart_name.concat("." + another_id);
/*  427:1046 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("multipart_id", 12, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  428:     */       
/*  429:1048 */       return CUP$parser$result;
/*  430:     */     case 73: 
/*  431:1053 */       String RESULT = null;
/*  432:1054 */       RESULT = null;
/*  433:1055 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("opt_label", 39, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  434:     */       
/*  435:1057 */       return CUP$parser$result;
/*  436:     */     case 72: 
/*  437:1062 */       String RESULT = null;
/*  438:1063 */       int labidleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  439:1064 */       int labidright = ((Symbol)CUP$parser$stack.peek()).right;
/*  440:1065 */       String labid = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  441:1066 */       RESULT = labid;
/*  442:1067 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("opt_label", 39, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  443:     */       
/*  444:1069 */       return CUP$parser$result;
/*  445:     */     case 71: 
/*  446:1074 */       Object RESULT = null;
/*  447:1075 */       int code_strleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  448:1076 */       int code_strright = ((Symbol)CUP$parser$stack.peek()).right;
/*  449:1077 */       String code_str = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  450:     */       
/*  451:     */ 
/*  452:1080 */       add_rhs_part(new action_part(code_str));
/*  453:     */       
/*  454:1082 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("prod_part", 23, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  455:     */       
/*  456:1084 */       return CUP$parser$result;
/*  457:     */     case 70: 
/*  458:1089 */       Object RESULT = null;
/*  459:1090 */       int symidleft = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).left;
/*  460:1091 */       int symidright = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).right;
/*  461:1092 */       String symid = (String)((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/*  462:1093 */       int labidleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  463:1094 */       int labidright = ((Symbol)CUP$parser$stack.peek()).right;
/*  464:1095 */       String labid = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  465:     */       
/*  466:     */ 
/*  467:1098 */       production_part symb = (production_part)this.symbols.get(symid);
/*  468:1101 */       if (symb == null)
/*  469:     */       {
/*  470:1103 */         if (ErrorManager.getManager().getErrorCount() == 0) {
/*  471:1104 */           ErrorManager.getManager().emit_error("java_cup.runtime.Symbol \"" + symid + "\" has not been declared");
/*  472:     */         }
/*  473:     */       }
/*  474:     */       else {
/*  475:1110 */         add_rhs_part(add_lab(symb, labid));
/*  476:     */       }
/*  477:1113 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("prod_part", 23, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  478:     */       
/*  479:1115 */       return CUP$parser$result;
/*  480:     */     case 69: 
/*  481:1120 */       Object RESULT = null;
/*  482:     */       
/*  483:1122 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("prod_part_list", 22, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  484:     */       
/*  485:1124 */       return CUP$parser$result;
/*  486:     */     case 68: 
/*  487:1129 */       Object RESULT = null;
/*  488:     */       
/*  489:1131 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("prod_part_list", 22, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  490:     */       
/*  491:1133 */       return CUP$parser$result;
/*  492:     */     case 67: 
/*  493:1138 */       Object RESULT = null;
/*  494:1140 */       if (this.lhs_nt != null)
/*  495:     */       {
/*  496:1143 */         production p = new production(this.lhs_nt, this.rhs_parts, this.rhs_pos);
/*  497:1148 */         if (this.start_nt == null)
/*  498:     */         {
/*  499:1150 */           this.start_nt = this.lhs_nt;
/*  500:     */           
/*  501:     */ 
/*  502:1153 */           new_rhs();
/*  503:1154 */           add_rhs_part(add_lab(new symbol_part(this.start_nt), "start_val"));
/*  504:1155 */           add_rhs_part(new symbol_part(terminal.EOF));
/*  505:1156 */           if (!emit._xmlactions) {
/*  506:1156 */             add_rhs_part(new action_part("RESULT = start_val;"));
/*  507:     */           }
/*  508:1157 */           emit.start_production = new production(non_terminal.START_nt, this.rhs_parts, this.rhs_pos);
/*  509:     */           
/*  510:     */ 
/*  511:1160 */           new_rhs();
/*  512:     */         }
/*  513:     */       }
/*  514:1165 */       new_rhs();
/*  515:     */       
/*  516:1167 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("rhs", 27, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  517:     */       
/*  518:1169 */       return CUP$parser$result;
/*  519:     */     case 66: 
/*  520:1174 */       Object RESULT = null;
/*  521:1175 */       int term_nameleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  522:1176 */       int term_nameright = ((Symbol)CUP$parser$stack.peek()).right;
/*  523:1177 */       String term_name = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  524:     */       
/*  525:1179 */       symbol sym = null;
/*  526:1180 */       if (this.lhs_nt != null)
/*  527:     */       {
/*  528:1183 */         if (term_name == null)
/*  529:     */         {
/*  530:1184 */           System.err.println("No terminal for contextual precedence");
/*  531:1185 */           sym = null;
/*  532:     */         }
/*  533:     */         else
/*  534:     */         {
/*  535:1187 */           sym = ((symbol_part)this.symbols.get(term_name)).the_symbol();
/*  536:     */         }
/*  537:     */         production p;
/*  538:1191 */         if ((sym != null) && ((sym instanceof terminal)))
/*  539:     */         {
/*  540:1192 */           production p = new production(this.lhs_nt, this.rhs_parts, this.rhs_pos, ((terminal)sym).precedence_num(), ((terminal)sym).precedence_side());
/*  541:     */           
/*  542:     */ 
/*  543:1195 */           ((symbol_part)this.symbols.get(term_name)).the_symbol().note_use();
/*  544:     */         }
/*  545:     */         else
/*  546:     */         {
/*  547:1197 */           System.err.println("Invalid terminal " + term_name + " for contextual precedence assignment");
/*  548:     */           
/*  549:1199 */           p = new production(this.lhs_nt, this.rhs_parts, this.rhs_pos);
/*  550:     */         }
/*  551:1205 */         if (this.start_nt == null)
/*  552:     */         {
/*  553:1207 */           this.start_nt = this.lhs_nt;
/*  554:     */           
/*  555:     */ 
/*  556:1210 */           new_rhs();
/*  557:1211 */           add_rhs_part(add_lab(new symbol_part(this.start_nt), "start_val"));
/*  558:1212 */           add_rhs_part(new symbol_part(terminal.EOF));
/*  559:1213 */           if (!emit._xmlactions) {
/*  560:1213 */             add_rhs_part(new action_part("RESULT = start_val;"));
/*  561:     */           }
/*  562:1214 */           if ((sym != null) && ((sym instanceof terminal))) {
/*  563:1215 */             emit.start_production = new production(non_terminal.START_nt, this.rhs_parts, this.rhs_pos, ((terminal)sym).precedence_num(), ((terminal)sym).precedence_side());
/*  564:     */           } else {
/*  565:1220 */             emit.start_production = new production(non_terminal.START_nt, this.rhs_parts, this.rhs_pos);
/*  566:     */           }
/*  567:1223 */           new_rhs();
/*  568:     */         }
/*  569:     */       }
/*  570:1228 */       new_rhs();
/*  571:     */       
/*  572:1230 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("rhs", 27, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  573:     */       
/*  574:1232 */       return CUP$parser$result;
/*  575:     */     case 65: 
/*  576:1237 */       Object RESULT = null;
/*  577:     */       
/*  578:1239 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("rhs_list", 26, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  579:     */       
/*  580:1241 */       return CUP$parser$result;
/*  581:     */     case 64: 
/*  582:1246 */       Object RESULT = null;
/*  583:     */       
/*  584:1248 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("rhs_list", 26, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  585:     */       
/*  586:1250 */       return CUP$parser$result;
/*  587:     */     case 63: 
/*  588:1255 */       Object RESULT = null;
/*  589:     */       
/*  590:1257 */       RESULT = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/*  591:     */       
/*  592:1259 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("production", 21, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  593:     */       
/*  594:1261 */       return CUP$parser$result;
/*  595:     */     case 62: 
/*  596:1266 */       Object RESULT = null;
/*  597:1267 */       ErrorManager.getManager().emit_error("Syntax Error");
/*  598:1268 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("NT$12", 58, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  599:     */       
/*  600:1270 */       return CUP$parser$result;
/*  601:     */     case 61: 
/*  602:1275 */       Object RESULT = null;
/*  603:     */       
/*  604:1277 */       RESULT = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 3)).value;
/*  605:1278 */       int lhs_idleft = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 4)).left;
/*  606:1279 */       int lhs_idright = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 4)).right;
/*  607:1280 */       String lhs_id = (String)((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 4)).value;
/*  608:     */       
/*  609:1282 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("production", 21, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 4), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  610:     */       
/*  611:1284 */       return CUP$parser$result;
/*  612:     */     case 60: 
/*  613:1289 */       Object RESULT = null;
/*  614:1290 */       int lhs_idleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  615:1291 */       int lhs_idright = ((Symbol)CUP$parser$stack.peek()).right;
/*  616:1292 */       String lhs_id = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  617:     */       
/*  618:     */ 
/*  619:1295 */       this.lhs_nt = ((non_terminal)this.non_terms.get(lhs_id));
/*  620:1298 */       if (this.lhs_nt == null) {
/*  621:1300 */         if (ErrorManager.getManager().getErrorCount() == 0) {
/*  622:1301 */           ErrorManager.getManager().emit_warning("LHS non terminal \"" + lhs_id + "\" has not been declared");
/*  623:     */         }
/*  624:     */       }
/*  625:1306 */       new_rhs();
/*  626:     */       
/*  627:1308 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("NT$11", 57, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  628:     */       
/*  629:1310 */       return CUP$parser$result;
/*  630:     */     case 59: 
/*  631:1315 */       Object RESULT = null;
/*  632:     */       
/*  633:1317 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("production_list", 11, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  634:     */       
/*  635:1319 */       return CUP$parser$result;
/*  636:     */     case 58: 
/*  637:1324 */       Object RESULT = null;
/*  638:     */       
/*  639:1326 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("production_list", 11, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  640:     */       
/*  641:1328 */       return CUP$parser$result;
/*  642:     */     case 57: 
/*  643:1333 */       Object RESULT = null;
/*  644:     */       
/*  645:1335 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("start_spec", 10, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  646:     */       
/*  647:1337 */       return CUP$parser$result;
/*  648:     */     case 56: 
/*  649:1342 */       Object RESULT = null;
/*  650:     */       
/*  651:1344 */       RESULT = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/*  652:1345 */       int start_nameleft = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2)).left;
/*  653:1346 */       int start_nameright = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2)).right;
/*  654:1347 */       String start_name = (String)((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2)).value;
/*  655:     */       
/*  656:1349 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("start_spec", 10, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 4), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  657:     */       
/*  658:1351 */       return CUP$parser$result;
/*  659:     */     case 55: 
/*  660:1356 */       Object RESULT = null;
/*  661:1357 */       int start_nameleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  662:1358 */       int start_nameright = ((Symbol)CUP$parser$stack.peek()).right;
/*  663:1359 */       String start_name = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  664:     */       
/*  665:     */ 
/*  666:1362 */       non_terminal nt = (non_terminal)this.non_terms.get(start_name);
/*  667:1363 */       if (nt == null)
/*  668:     */       {
/*  669:1365 */         ErrorManager.getManager().emit_error("Start non terminal \"" + start_name + "\" has not been declared");
/*  670:     */       }
/*  671:     */       else
/*  672:     */       {
/*  673:1371 */         this.start_nt = nt;
/*  674:     */         
/*  675:     */ 
/*  676:1374 */         new_rhs();
/*  677:1375 */         add_rhs_part(add_lab(new symbol_part(this.start_nt), "start_val"));
/*  678:1376 */         add_rhs_part(new symbol_part(terminal.EOF));
/*  679:1377 */         if (!emit._xmlactions) {
/*  680:1377 */           add_rhs_part(new action_part("RESULT = start_val;"));
/*  681:     */         }
/*  682:1378 */         emit.start_production = new production(non_terminal.START_nt, this.rhs_parts, this.rhs_pos);
/*  683:     */         
/*  684:1380 */         new_rhs();
/*  685:     */       }
/*  686:1383 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("NT$10", 56, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  687:     */       
/*  688:1385 */       return CUP$parser$result;
/*  689:     */     case 54: 
/*  690:1390 */       String RESULT = null;
/*  691:1391 */       int symleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  692:1392 */       int symright = ((Symbol)CUP$parser$stack.peek()).right;
/*  693:1393 */       String sym = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  694:1396 */       if (this.symbols.get(sym) == null) {
/*  695:1399 */         ErrorManager.getManager().emit_error("Terminal \"" + sym + "\" has not been declared");
/*  696:     */       }
/*  697:1402 */       RESULT = sym;
/*  698:     */       
/*  699:1404 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("term_id", 41, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  700:     */       
/*  701:1406 */       return CUP$parser$result;
/*  702:     */     case 53: 
/*  703:1411 */       String RESULT = null;
/*  704:1412 */       int symleft = ((Symbol)CUP$parser$stack.peek()).left;
/*  705:1413 */       int symright = ((Symbol)CUP$parser$stack.peek()).right;
/*  706:1414 */       String sym = (String)((Symbol)CUP$parser$stack.peek()).value;
/*  707:     */       
/*  708:1416 */       add_precedence(sym);
/*  709:1417 */       RESULT = sym;
/*  710:     */       
/*  711:1419 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("terminal_id", 40, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  712:     */       
/*  713:1421 */       return CUP$parser$result;
/*  714:     */     case 52: 
/*  715:1426 */       Object RESULT = null;
/*  716:     */       
/*  717:1428 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("terminal_list", 31, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  718:     */       
/*  719:1430 */       return CUP$parser$result;
/*  720:     */     case 51: 
/*  721:1435 */       Object RESULT = null;
/*  722:     */       
/*  723:1437 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("terminal_list", 31, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  724:     */       
/*  725:1439 */       return CUP$parser$result;
/*  726:     */     case 50: 
/*  727:1444 */       Object RESULT = null;
/*  728:     */       
/*  729:1446 */       RESULT = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2)).value;
/*  730:     */       
/*  731:1448 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("preced", 30, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 4), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  732:     */       
/*  733:1450 */       return CUP$parser$result;
/*  734:     */     case 49: 
/*  735:1455 */       Object RESULT = null;
/*  736:     */       
/*  737:1457 */       update_precedence(2);
/*  738:     */       
/*  739:1459 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("NT$9", 55, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  740:     */       
/*  741:1461 */       return CUP$parser$result;
/*  742:     */     case 48: 
/*  743:1466 */       Object RESULT = null;
/*  744:     */       
/*  745:1468 */       RESULT = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2)).value;
/*  746:     */       
/*  747:1470 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("preced", 30, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 4), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  748:     */       
/*  749:1472 */       return CUP$parser$result;
/*  750:     */     case 47: 
/*  751:1477 */       Object RESULT = null;
/*  752:     */       
/*  753:1479 */       update_precedence(1);
/*  754:     */       
/*  755:1481 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("NT$8", 54, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  756:     */       
/*  757:1483 */       return CUP$parser$result;
/*  758:     */     case 46: 
/*  759:1488 */       Object RESULT = null;
/*  760:     */       
/*  761:1490 */       RESULT = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2)).value;
/*  762:     */       
/*  763:1492 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("preced", 30, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 4), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  764:     */       
/*  765:1494 */       return CUP$parser$result;
/*  766:     */     case 45: 
/*  767:1499 */       Object RESULT = null;
/*  768:     */       
/*  769:1501 */       update_precedence(0);
/*  770:     */       
/*  771:1503 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("NT$7", 53, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  772:     */       
/*  773:1505 */       return CUP$parser$result;
/*  774:     */     case 44: 
/*  775:1510 */       Object RESULT = null;
/*  776:     */       
/*  777:1512 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("precedence_l", 32, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  778:     */       
/*  779:1514 */       return CUP$parser$result;
/*  780:     */     case 43: 
/*  781:1519 */       Object RESULT = null;
/*  782:     */       
/*  783:1521 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("precedence_l", 32, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  784:     */       
/*  785:1523 */       return CUP$parser$result;
/*  786:     */     case 42: 
/*  787:1528 */       Object RESULT = null;
/*  788:     */       
/*  789:1530 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("precedence_list", 29, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  790:     */       
/*  791:1532 */       return CUP$parser$result;
/*  792:     */     case 41: 
/*  793:1537 */       Object RESULT = null;
/*  794:     */       
/*  795:1539 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("precedence_list", 29, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  796:     */       
/*  797:1541 */       return CUP$parser$result;
/*  798:     */     case 40: 
/*  799:1546 */       Object RESULT = null;
/*  800:     */       
/*  801:1548 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("non_term_name_list", 20, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  802:     */       
/*  803:1550 */       return CUP$parser$result;
/*  804:     */     case 39: 
/*  805:1555 */       Object RESULT = null;
/*  806:     */       
/*  807:1557 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("non_term_name_list", 20, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  808:     */       
/*  809:1559 */       return CUP$parser$result;
/*  810:     */     case 38: 
/*  811:1564 */       Object RESULT = null;
/*  812:     */       
/*  813:1566 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("term_name_list", 19, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  814:     */       
/*  815:1568 */       return CUP$parser$result;
/*  816:     */     case 37: 
/*  817:1573 */       Object RESULT = null;
/*  818:     */       
/*  819:1575 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("term_name_list", 19, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  820:     */       
/*  821:1577 */       return CUP$parser$result;
/*  822:     */     case 36: 
/*  823:1582 */       Object RESULT = null;
/*  824:     */       
/*  825:1584 */       RESULT = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/*  826:     */       
/*  827:1586 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("declares_non_term", 34, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  828:     */       
/*  829:1588 */       return CUP$parser$result;
/*  830:     */     case 35: 
/*  831:1593 */       Object RESULT = null;
/*  832:     */       
/*  833:     */ 
/*  834:1596 */       this.multipart_name = new String();
/*  835:     */       
/*  836:1598 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("NT$6", 52, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  837:     */       
/*  838:1600 */       return CUP$parser$result;
/*  839:     */     case 34: 
/*  840:1605 */       Object RESULT = null;
/*  841:     */       
/*  842:1607 */       RESULT = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/*  843:     */       
/*  844:1609 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("declares_term", 33, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  845:     */       
/*  846:1611 */       return CUP$parser$result;
/*  847:     */     case 33: 
/*  848:1616 */       Object RESULT = null;
/*  849:     */       
/*  850:     */ 
/*  851:1619 */       this.multipart_name = new String();
/*  852:     */       
/*  853:1621 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("NT$5", 51, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  854:     */       
/*  855:1623 */       return CUP$parser$result;
/*  856:     */     case 32: 
/*  857:1628 */       Object RESULT = null;
/*  858:     */       
/*  859:1630 */       RESULT = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/*  860:     */       
/*  861:1632 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("symbol", 17, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 3), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  862:     */       
/*  863:1634 */       return CUP$parser$result;
/*  864:     */     case 31: 
/*  865:1639 */       Object RESULT = null;
/*  866:     */       
/*  867:     */ 
/*  868:1642 */       this.multipart_name = new String();
/*  869:     */       
/*  870:1644 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("NT$4", 50, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  871:     */       
/*  872:1646 */       return CUP$parser$result;
/*  873:     */     case 30: 
/*  874:1651 */       Object RESULT = null;
/*  875:     */       
/*  876:1653 */       RESULT = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/*  877:     */       
/*  878:1655 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("symbol", 17, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 3), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  879:     */       
/*  880:1657 */       return CUP$parser$result;
/*  881:     */     case 29: 
/*  882:1662 */       Object RESULT = null;
/*  883:     */       
/*  884:     */ 
/*  885:1665 */       this.multipart_name = new String();
/*  886:     */       
/*  887:1667 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("NT$3", 49, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  888:     */       
/*  889:1669 */       return CUP$parser$result;
/*  890:     */     case 28: 
/*  891:1674 */       Object RESULT = null;
/*  892:     */       
/*  893:1676 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("symbol", 17, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  894:     */       
/*  895:1678 */       return CUP$parser$result;
/*  896:     */     case 27: 
/*  897:1683 */       Object RESULT = null;
/*  898:     */       
/*  899:1685 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("symbol", 17, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  900:     */       
/*  901:1687 */       return CUP$parser$result;
/*  902:     */     case 26: 
/*  903:1692 */       Object RESULT = null;
/*  904:     */       
/*  905:1694 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("symbol", 17, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  906:     */       
/*  907:1696 */       return CUP$parser$result;
/*  908:     */     case 25: 
/*  909:1701 */       Object RESULT = null;
/*  910:     */       
/*  911:1703 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("symbol", 17, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  912:     */       
/*  913:1705 */       return CUP$parser$result;
/*  914:     */     case 24: 
/*  915:1710 */       Object RESULT = null;
/*  916:     */       
/*  917:1712 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("symbol_list", 9, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  918:     */       
/*  919:1714 */       return CUP$parser$result;
/*  920:     */     case 23: 
/*  921:1719 */       Object RESULT = null;
/*  922:     */       
/*  923:1721 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("symbol_list", 9, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  924:     */       
/*  925:1723 */       return CUP$parser$result;
/*  926:     */     case 22: 
/*  927:1728 */       Object RESULT = null;
/*  928:1729 */       int user_codeleft = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).left;
/*  929:1730 */       int user_coderight = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).right;
/*  930:1731 */       String user_code = (String)((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/*  931:1733 */       if (emit.scan_code != null) {
/*  932:1734 */         ErrorManager.getManager().emit_warning("Redundant scan code (skipping)");
/*  933:     */       } else {
/*  934:1736 */         emit.scan_code = user_code;
/*  935:     */       }
/*  936:1738 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("scan_code", 16, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 3), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  937:     */       
/*  938:1740 */       return CUP$parser$result;
/*  939:     */     case 21: 
/*  940:1745 */       Object RESULT = null;
/*  941:1746 */       int user_codeleft = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).left;
/*  942:1747 */       int user_coderight = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).right;
/*  943:1748 */       String user_code = (String)((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/*  944:1750 */       if (emit.init_code != null) {
/*  945:1751 */         ErrorManager.getManager().emit_warning("Redundant init code (skipping)");
/*  946:     */       } else {
/*  947:1753 */         emit.init_code = user_code;
/*  948:     */       }
/*  949:1755 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("init_code", 15, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 3), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  950:     */       
/*  951:1757 */       return CUP$parser$result;
/*  952:     */     case 20: 
/*  953:1762 */       Object RESULT = null;
/*  954:1763 */       int user_codeleft = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).left;
/*  955:1764 */       int user_coderight = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).right;
/*  956:1765 */       String user_code = (String)((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/*  957:1767 */       if (emit.parser_code != null) {
/*  958:1768 */         ErrorManager.getManager().emit_warning("Redundant parser code (skipping)");
/*  959:     */       } else {
/*  960:1770 */         emit.parser_code = user_code;
/*  961:     */       }
/*  962:1772 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("parser_code_part", 8, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 3), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  963:     */       
/*  964:1774 */       return CUP$parser$result;
/*  965:     */     case 19: 
/*  966:1779 */       Object RESULT = null;
/*  967:1780 */       int user_codeleft = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).left;
/*  968:1781 */       int user_coderight = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).right;
/*  969:1782 */       String user_code = (String)((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/*  970:1784 */       if (emit.action_code != null) {
/*  971:1785 */         ErrorManager.getManager().emit_warning("Redundant action code (skipping)");
/*  972:     */       } else {
/*  973:1787 */         emit.action_code = user_code;
/*  974:     */       }
/*  975:1789 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("action_code_part", 3, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 3), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  976:     */       
/*  977:1791 */       return CUP$parser$result;
/*  978:     */     case 18: 
/*  979:1796 */       Object RESULT = null;
/*  980:     */       
/*  981:1798 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("code_parts", 4, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  982:     */       
/*  983:1800 */       return CUP$parser$result;
/*  984:     */     case 17: 
/*  985:1805 */       Object RESULT = null;
/*  986:     */       
/*  987:1807 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("code_parts", 4, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  988:     */       
/*  989:1809 */       return CUP$parser$result;
/*  990:     */     case 16: 
/*  991:1814 */       Object RESULT = null;
/*  992:     */       
/*  993:1816 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("code_part", 5, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/*  994:     */       
/*  995:1818 */       return CUP$parser$result;
/*  996:     */     case 15: 
/*  997:1823 */       Object RESULT = null;
/*  998:     */       
/*  999:1825 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("code_part", 5, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1000:     */       
/* 1001:1827 */       return CUP$parser$result;
/* 1002:     */     case 14: 
/* 1003:1832 */       Object RESULT = null;
/* 1004:     */       
/* 1005:1834 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("code_part", 5, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1006:     */       
/* 1007:1836 */       return CUP$parser$result;
/* 1008:     */     case 13: 
/* 1009:1841 */       Object RESULT = null;
/* 1010:     */       
/* 1011:1843 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("code_part", 5, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1012:     */       
/* 1013:1845 */       return CUP$parser$result;
/* 1014:     */     case 12: 
/* 1015:1850 */       Object RESULT = null;
/* 1016:1851 */       int idleft = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).left;
/* 1017:1852 */       int idright = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).right;
/* 1018:1853 */       String id = (String)((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/* 1019:     */       
/* 1020:1855 */       emit.parser_class_name = id;
/* 1021:1856 */       emit.symbol_const_class_name = id + "Sym";
/* 1022:     */       
/* 1023:1858 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("class_name", 35, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 2), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1024:     */       
/* 1025:1860 */       return CUP$parser$result;
/* 1026:     */     case 11: 
/* 1027:1865 */       Object RESULT = null;
/* 1028:     */       
/* 1029:1867 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("class_name", 35, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1030:     */       
/* 1031:1869 */       return CUP$parser$result;
/* 1032:     */     case 10: 
/* 1033:1874 */       Object RESULT = null;
/* 1034:     */       
/* 1035:1876 */       RESULT = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/* 1036:     */       
/* 1037:1878 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("import_spec", 13, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 3), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1038:     */       
/* 1039:1880 */       return CUP$parser$result;
/* 1040:     */     case 9: 
/* 1041:1885 */       Object RESULT = null;
/* 1042:     */       
/* 1043:     */ 
/* 1044:1888 */       emit.import_list.push(this.multipart_name);
/* 1045:     */       
/* 1046:     */ 
/* 1047:1891 */       this.multipart_name = new String();
/* 1048:     */       
/* 1049:1893 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("NT$2", 48, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1050:     */       
/* 1051:1895 */       return CUP$parser$result;
/* 1052:     */     case 8: 
/* 1053:1900 */       Object RESULT = null;
/* 1054:     */       
/* 1055:1902 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("import_list", 2, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1056:     */       
/* 1057:1904 */       return CUP$parser$result;
/* 1058:     */     case 7: 
/* 1059:1909 */       Object RESULT = null;
/* 1060:     */       
/* 1061:1911 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("import_list", 2, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1062:     */       
/* 1063:1913 */       return CUP$parser$result;
/* 1064:     */     case 6: 
/* 1065:1918 */       Object RESULT = null;
/* 1066:     */       
/* 1067:1920 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("package_spec", 1, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1068:     */       
/* 1069:1922 */       return CUP$parser$result;
/* 1070:     */     case 5: 
/* 1071:1927 */       Object RESULT = null;
/* 1072:     */       
/* 1073:1929 */       RESULT = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/* 1074:     */       
/* 1075:1931 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("package_spec", 1, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 3), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1076:     */       
/* 1077:1933 */       return CUP$parser$result;
/* 1078:     */     case 4: 
/* 1079:1938 */       Object RESULT = null;
/* 1080:     */       
/* 1081:     */ 
/* 1082:1941 */       emit.package_name = this.multipart_name;
/* 1083:     */       
/* 1084:     */ 
/* 1085:1944 */       this.multipart_name = new String();
/* 1086:     */       
/* 1087:1946 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("NT$1", 47, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1088:     */       
/* 1089:1948 */       return CUP$parser$result;
/* 1090:     */     case 3: 
/* 1091:1953 */       Object RESULT = null;
/* 1092:     */       
/* 1093:1955 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("spec", 0, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 4), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1094:     */       
/* 1095:1957 */       return CUP$parser$result;
/* 1096:     */     case 2: 
/* 1097:1962 */       Object RESULT = null;
/* 1098:     */       
/* 1099:1964 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("spec", 0, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 8), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1100:     */       
/* 1101:1966 */       return CUP$parser$result;
/* 1102:     */     case 1: 
/* 1103:1971 */       Object RESULT = null;
/* 1104:     */       
/* 1105:     */ 
/* 1106:1974 */       this.symbols.put("error", new symbol_part(terminal.error));
/* 1107:     */       
/* 1108:     */ 
/* 1109:1977 */       this.non_terms.put("$START", non_terminal.START_nt);
/* 1110:     */       
/* 1111:1979 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("NT$0", 46, (Symbol)CUP$parser$stack.peek(), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1112:     */       
/* 1113:1981 */       return CUP$parser$result;
/* 1114:     */     case 0: 
/* 1115:1986 */       Object RESULT = null;
/* 1116:1987 */       int start_valleft = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).left;
/* 1117:1988 */       int start_valright = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).right;
/* 1118:1989 */       Object start_val = ((Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1)).value;
/* 1119:1990 */       RESULT = start_val;
/* 1120:1991 */       CUP$parser$result = this.parser.getSymbolFactory().newSymbol("$START", 0, (Symbol)CUP$parser$stack.elementAt(CUP$parser$top - 1), (Symbol)CUP$parser$stack.peek(), RESULT);
/* 1121:     */       
/* 1122:     */ 
/* 1123:1994 */       CUP$parser$parser.done_parsing();
/* 1124:1995 */       return CUP$parser$result;
/* 1125:     */     }
/* 1126:1999 */     throw new Exception("Invalid action number found in internal parse table");
/* 1127:     */   }
/* 1128:     */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.CUP.parser.actions
 * JD-Core Version:    0.7.0.1
 */
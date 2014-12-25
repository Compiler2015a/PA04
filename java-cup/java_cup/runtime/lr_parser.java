/*    1:     */ package java_cup.runtime;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.lang.reflect.Field;
/*    5:     */ import java.util.LinkedList;
/*    6:     */ import java.util.List;
/*    7:     */ import java.util.Stack;
/*    8:     */ 
/*    9:     */ public abstract class lr_parser
/*   10:     */ {
/*   11:     */   public SymbolFactory symbolFactory;
/*   12:     */   protected static final int _error_sync_size = 3;
/*   13:     */   
/*   14:     */   @Deprecated
/*   15:     */   public lr_parser()
/*   16:     */   {
/*   17: 133 */     this(new DefaultSymbolFactory());
/*   18:     */   }
/*   19:     */   
/*   20:     */   public lr_parser(SymbolFactory fac)
/*   21:     */   {
/*   22: 139 */     this.symbolFactory = fac;
/*   23:     */   }
/*   24:     */   
/*   25:     */   @Deprecated
/*   26:     */   public lr_parser(Scanner s)
/*   27:     */   {
/*   28: 147 */     this(s, new DefaultSymbolFactory());
/*   29:     */   }
/*   30:     */   
/*   31:     */   public lr_parser(Scanner s, SymbolFactory symfac)
/*   32:     */   {
/*   33: 153 */     this();
/*   34: 154 */     this.symbolFactory = symfac;
/*   35: 155 */     setScanner(s);
/*   36:     */   }
/*   37:     */   
/*   38:     */   public SymbolFactory getSymbolFactory()
/*   39:     */   {
/*   40: 162 */     return this.symbolFactory;
/*   41:     */   }
/*   42:     */   
/*   43:     */   protected int error_sync_size()
/*   44:     */   {
/*   45: 178 */     return 3;
/*   46:     */   }
/*   47:     */   
/*   48: 253 */   protected boolean _done_parsing = false;
/*   49:     */   protected int tos;
/*   50:     */   protected Symbol cur_token;
/*   51:     */   
/*   52:     */   public abstract short[][] production_table();
/*   53:     */   
/*   54:     */   public abstract short[][] action_table();
/*   55:     */   
/*   56:     */   public abstract short[][] reduce_table();
/*   57:     */   
/*   58:     */   public abstract int start_state();
/*   59:     */   
/*   60:     */   public abstract int start_production();
/*   61:     */   
/*   62:     */   public abstract int EOF_sym();
/*   63:     */   
/*   64:     */   public abstract int error_sym();
/*   65:     */   
/*   66:     */   public void done_parsing()
/*   67:     */   {
/*   68: 263 */     this._done_parsing = true;
/*   69:     */   }
/*   70:     */   
/*   71: 282 */   protected Stack stack = new Stack();
/*   72:     */   protected short[][] production_tab;
/*   73:     */   protected short[][] action_tab;
/*   74:     */   protected short[][] reduce_tab;
/*   75:     */   private Scanner _scanner;
/*   76:     */   protected Symbol[] lookahead;
/*   77:     */   protected int lookahead_pos;
/*   78:     */   
/*   79:     */   public void setScanner(Scanner s)
/*   80:     */   {
/*   81: 309 */     this._scanner = s;
/*   82:     */   }
/*   83:     */   
/*   84:     */   public Scanner getScanner()
/*   85:     */   {
/*   86: 314 */     return this._scanner;
/*   87:     */   }
/*   88:     */   
/*   89:     */   public abstract Symbol do_action(int paramInt1, lr_parser paramlr_parser, Stack paramStack, int paramInt2)
/*   90:     */     throws Exception;
/*   91:     */   
/*   92:     */   public void user_init()
/*   93:     */     throws Exception
/*   94:     */   {}
/*   95:     */   
/*   96:     */   protected abstract void init_actions()
/*   97:     */     throws Exception;
/*   98:     */   
/*   99:     */   public Symbol scan()
/*  100:     */     throws Exception
/*  101:     */   {
/*  102: 366 */     Symbol sym = getScanner().next_token();
/*  103: 367 */     return sym != null ? sym : getSymbolFactory().newSymbol("END_OF_FILE", EOF_sym());
/*  104:     */   }
/*  105:     */   
/*  106:     */   public void report_fatal_error(String message, Object info)
/*  107:     */     throws Exception
/*  108:     */   {
/*  109: 386 */     done_parsing();
/*  110:     */     
/*  111:     */ 
/*  112: 389 */     report_error(message, info);
/*  113:     */     
/*  114:     */ 
/*  115: 392 */     throw new Exception("Can't recover from previous error(s)");
/*  116:     */   }
/*  117:     */   
/*  118:     */   public void report_error(String message, Object info)
/*  119:     */   {
/*  120: 408 */     if ((info instanceof ComplexSymbolFactory.ComplexSymbol))
/*  121:     */     {
/*  122: 409 */       ComplexSymbolFactory.ComplexSymbol cs = (ComplexSymbolFactory.ComplexSymbol)info;
/*  123: 410 */       System.err.println(message + " for input symbol \"" + cs.getName() + "\" spanning from " + cs.getLeft() + " to " + cs.getRight());
/*  124: 411 */       return;
/*  125:     */     }
/*  126: 414 */     System.err.print(message);
/*  127: 415 */     System.err.flush();
/*  128: 416 */     if ((info instanceof Symbol)) {
/*  129: 417 */       if (((Symbol)info).left != -1) {
/*  130: 418 */         System.err.println(" at character " + ((Symbol)info).left + " of input");
/*  131:     */       } else {
/*  132: 419 */         System.err.println("");
/*  133:     */       }
/*  134:     */     }
/*  135:     */   }
/*  136:     */   
/*  137:     */   public void syntax_error(Symbol cur_token)
/*  138:     */   {
/*  139: 432 */     report_error("Syntax error", cur_token);
/*  140: 433 */     report_expected_token_ids();
/*  141:     */   }
/*  142:     */   
/*  143:     */   public Class getSymbolContainer()
/*  144:     */   {
/*  145: 440 */     return null;
/*  146:     */   }
/*  147:     */   
/*  148:     */   protected void report_expected_token_ids()
/*  149:     */   {
/*  150: 443 */     List<Integer> ids = expected_token_ids();
/*  151: 444 */     LinkedList<String> list = new LinkedList();
/*  152: 445 */     for (Integer expected : ids) {
/*  153: 446 */       list.add(symbl_name_from_id(expected.intValue()));
/*  154:     */     }
/*  155: 448 */     System.out.println("instead expected token classes are " + list);
/*  156:     */   }
/*  157:     */   
/*  158:     */   public String symbl_name_from_id(int id)
/*  159:     */   {
/*  160: 456 */     Field[] fields = getSymbolContainer().getFields();
/*  161: 457 */     for (Field f : fields) {
/*  162:     */       try
/*  163:     */       {
/*  164: 459 */         if (f.getInt(null) == id) {
/*  165: 460 */           return f.getName();
/*  166:     */         }
/*  167:     */       }
/*  168:     */       catch (IllegalArgumentException e) {}catch (IllegalAccessException e) {}
/*  169:     */     }
/*  170: 467 */     return "invalid symbol id";
/*  171:     */   }
/*  172:     */   
/*  173:     */   public List<Integer> expected_token_ids()
/*  174:     */   {
/*  175: 474 */     List<Integer> ret = new LinkedList();
/*  176: 475 */     int parse_state = ((Symbol)this.stack.peek()).parse_state;
/*  177: 476 */     short[] row = this.action_tab[parse_state];
/*  178: 477 */     for (int i = 0; i < row.length; i += 2) {
/*  179: 478 */       if ((row[i] != -1) && 
/*  180: 479 */         (validate_expected_symbol(row[i]))) {
/*  181: 480 */         ret.add(new Integer(row[i]));
/*  182:     */       }
/*  183:     */     }
/*  184: 482 */     return ret;
/*  185:     */   }
/*  186:     */   
/*  187:     */   private boolean validate_expected_symbol(int id)
/*  188:     */   {
/*  189:     */     try
/*  190:     */     {
/*  191: 488 */       virtual_parse_stack vstack = new virtual_parse_stack(this.stack);
/*  192:     */       for (;;)
/*  193:     */       {
/*  194: 493 */         int act = get_action(vstack.top(), id);
/*  195: 496 */         if (act == 0) {
/*  196: 496 */           return false;
/*  197:     */         }
/*  198: 499 */         if (act > 0)
/*  199:     */         {
/*  200: 502 */           vstack.push(act - 1);
/*  201: 505 */           if (!advance_lookahead()) {
/*  202: 505 */             return true;
/*  203:     */           }
/*  204:     */         }
/*  205:     */         else
/*  206:     */         {
/*  207: 511 */           if (-act - 1 == start_production()) {
/*  208: 511 */             return true;
/*  209:     */           }
/*  210: 514 */           short lhs = this.production_tab[(-act - 1)][0];
/*  211: 515 */           short rhs_size = this.production_tab[(-act - 1)][1];
/*  212: 517 */           for (int i = 0; i < rhs_size; i++) {
/*  213: 517 */             vstack.pop();
/*  214:     */           }
/*  215: 519 */           vstack.push(get_reduce(vstack.top(), lhs));
/*  216:     */         }
/*  217:     */       }
/*  218: 526 */       return true;
/*  219:     */     }
/*  220:     */     catch (Exception e)
/*  221:     */     {
/*  222: 524 */       e.printStackTrace();
/*  223:     */     }
/*  224:     */   }
/*  225:     */   
/*  226:     */   public void unrecovered_syntax_error(Symbol cur_token)
/*  227:     */     throws Exception
/*  228:     */   {
/*  229: 539 */     report_fatal_error("Couldn't repair and continue parse", cur_token);
/*  230:     */   }
/*  231:     */   
/*  232:     */   protected final short get_action(int state, int sym)
/*  233:     */   {
/*  234: 558 */     short[] row = this.action_tab[state];
/*  235: 561 */     if (row.length < 20) {
/*  236: 562 */       for (int probe = 0; probe < row.length; probe++)
/*  237:     */       {
/*  238: 565 */         short tag = row[(probe++)];
/*  239: 566 */         if ((tag == sym) || (tag == -1)) {
/*  240: 569 */           return row[probe];
/*  241:     */         }
/*  242:     */       }
/*  243:     */     }
/*  244: 575 */     int first = 0;
/*  245: 576 */     int last = (row.length - 1) / 2 - 1;
/*  246: 577 */     while (first <= last)
/*  247:     */     {
/*  248: 579 */       int probe = (first + last) / 2;
/*  249: 580 */       if (sym == row[(probe * 2)]) {
/*  250: 581 */         return row[(probe * 2 + 1)];
/*  251:     */       }
/*  252: 582 */       if (sym > row[(probe * 2)]) {
/*  253: 583 */         first = probe + 1;
/*  254:     */       } else {
/*  255: 585 */         last = probe - 1;
/*  256:     */       }
/*  257:     */     }
/*  258: 589 */     return row[(row.length - 1)];
/*  259:     */     int probe;
/*  260: 594 */     return 0;
/*  261:     */   }
/*  262:     */   
/*  263:     */   protected final short get_reduce(int state, int sym)
/*  264:     */   {
/*  265: 612 */     short[] row = this.reduce_tab[state];
/*  266: 615 */     if (row == null) {
/*  267: 616 */       return -1;
/*  268:     */     }
/*  269: 618 */     for (int probe = 0; probe < row.length; probe++)
/*  270:     */     {
/*  271: 621 */       short tag = row[(probe++)];
/*  272: 622 */       if ((tag == sym) || (tag == -1)) {
/*  273: 625 */         return row[probe];
/*  274:     */       }
/*  275:     */     }
/*  276: 629 */     return -1;
/*  277:     */   }
/*  278:     */   
/*  279:     */   public Symbol parse()
/*  280:     */     throws Exception
/*  281:     */   {
/*  282: 646 */     Symbol lhs_sym = null;
/*  283:     */     
/*  284:     */ 
/*  285:     */ 
/*  286:     */ 
/*  287:     */ 
/*  288:     */ 
/*  289: 653 */     this.production_tab = production_table();
/*  290: 654 */     this.action_tab = action_table();
/*  291: 655 */     this.reduce_tab = reduce_table();
/*  292:     */     
/*  293:     */ 
/*  294: 658 */     init_actions();
/*  295:     */     
/*  296:     */ 
/*  297: 661 */     user_init();
/*  298:     */     
/*  299:     */ 
/*  300: 664 */     this.cur_token = scan();
/*  301:     */     
/*  302:     */ 
/*  303: 667 */     this.stack.removeAllElements();
/*  304: 668 */     this.stack.push(getSymbolFactory().startSymbol("START", 0, start_state()));
/*  305: 669 */     this.tos = 0;
/*  306: 672 */     for (this._done_parsing = false; !this._done_parsing;)
/*  307:     */     {
/*  308: 675 */       if (this.cur_token.used_by_parser) {
/*  309: 676 */         throw new Error("Symbol recycling detected (fix your scanner).");
/*  310:     */       }
/*  311: 681 */       int act = get_action(((Symbol)this.stack.peek()).parse_state, this.cur_token.sym);
/*  312: 684 */       if (act > 0)
/*  313:     */       {
/*  314: 687 */         this.cur_token.parse_state = (act - 1);
/*  315: 688 */         this.cur_token.used_by_parser = true;
/*  316: 689 */         this.stack.push(this.cur_token);
/*  317: 690 */         this.tos += 1;
/*  318:     */         
/*  319:     */ 
/*  320: 693 */         this.cur_token = scan();
/*  321:     */       }
/*  322: 696 */       else if (act < 0)
/*  323:     */       {
/*  324: 699 */         lhs_sym = do_action(-act - 1, this, this.stack, this.tos);
/*  325:     */         
/*  326:     */ 
/*  327: 702 */         short lhs_sym_num = this.production_tab[(-act - 1)][0];
/*  328: 703 */         short handle_size = this.production_tab[(-act - 1)][1];
/*  329: 706 */         for (int i = 0; i < handle_size; i++)
/*  330:     */         {
/*  331: 708 */           this.stack.pop();
/*  332: 709 */           this.tos -= 1;
/*  333:     */         }
/*  334: 713 */         act = get_reduce(((Symbol)this.stack.peek()).parse_state, lhs_sym_num);
/*  335:     */         
/*  336:     */ 
/*  337: 716 */         lhs_sym.parse_state = act;
/*  338: 717 */         lhs_sym.used_by_parser = true;
/*  339: 718 */         this.stack.push(lhs_sym);
/*  340: 719 */         this.tos += 1;
/*  341:     */       }
/*  342: 722 */       else if (act == 0)
/*  343:     */       {
/*  344: 725 */         syntax_error(this.cur_token);
/*  345: 728 */         if (!error_recovery(false))
/*  346:     */         {
/*  347: 731 */           unrecovered_syntax_error(this.cur_token);
/*  348:     */           
/*  349:     */ 
/*  350: 734 */           done_parsing();
/*  351:     */         }
/*  352:     */         else
/*  353:     */         {
/*  354: 736 */           lhs_sym = (Symbol)this.stack.peek();
/*  355:     */         }
/*  356:     */       }
/*  357:     */     }
/*  358: 740 */     return lhs_sym;
/*  359:     */   }
/*  360:     */   
/*  361:     */   public void debug_message(String mess)
/*  362:     */   {
/*  363: 752 */     System.err.println(mess);
/*  364:     */   }
/*  365:     */   
/*  366:     */   public void dump_stack()
/*  367:     */   {
/*  368: 760 */     if (this.stack == null)
/*  369:     */     {
/*  370: 762 */       debug_message("# Stack dump requested, but stack is null");
/*  371: 763 */       return;
/*  372:     */     }
/*  373: 766 */     debug_message("============ Parse Stack Dump ============");
/*  374: 769 */     for (int i = 0; i < this.stack.size(); i++) {
/*  375: 771 */       debug_message("Symbol: " + ((Symbol)this.stack.elementAt(i)).sym + " State: " + ((Symbol)this.stack.elementAt(i)).parse_state);
/*  376:     */     }
/*  377: 774 */     debug_message("==========================================");
/*  378:     */   }
/*  379:     */   
/*  380:     */   public void debug_reduce(int prod_num, int nt_num, int rhs_size)
/*  381:     */   {
/*  382: 787 */     debug_message("# Reduce with prod #" + prod_num + " [NT=" + nt_num + ", " + "SZ=" + rhs_size + "]");
/*  383:     */   }
/*  384:     */   
/*  385:     */   public void debug_shift(Symbol shift_tkn)
/*  386:     */   {
/*  387: 799 */     debug_message("# Shift under term #" + shift_tkn.sym + " to state #" + shift_tkn.parse_state);
/*  388:     */   }
/*  389:     */   
/*  390:     */   public void debug_stack()
/*  391:     */   {
/*  392: 808 */     StringBuffer sb = new StringBuffer("## STACK:");
/*  393: 809 */     for (int i = 0; i < this.stack.size(); i++)
/*  394:     */     {
/*  395: 810 */       Symbol s = (Symbol)this.stack.elementAt(i);
/*  396: 811 */       sb.append(" <state " + s.parse_state + ", sym " + s.sym + ">");
/*  397: 812 */       if ((i % 3 == 2) || (i == this.stack.size() - 1))
/*  398:     */       {
/*  399: 813 */         debug_message(sb.toString());
/*  400: 814 */         sb = new StringBuffer("         ");
/*  401:     */       }
/*  402:     */     }
/*  403:     */   }
/*  404:     */   
/*  405:     */   public Symbol debug_parse()
/*  406:     */     throws Exception
/*  407:     */   {
/*  408: 833 */     Symbol lhs_sym = null;
/*  409:     */     
/*  410:     */ 
/*  411:     */ 
/*  412:     */ 
/*  413:     */ 
/*  414: 839 */     this.production_tab = production_table();
/*  415: 840 */     this.action_tab = action_table();
/*  416: 841 */     this.reduce_tab = reduce_table();
/*  417:     */     
/*  418: 843 */     debug_message("# Initializing parser");
/*  419:     */     
/*  420:     */ 
/*  421: 846 */     init_actions();
/*  422:     */     
/*  423:     */ 
/*  424: 849 */     user_init();
/*  425:     */     
/*  426:     */ 
/*  427: 852 */     this.cur_token = scan();
/*  428:     */     
/*  429: 854 */     debug_message("# Current Symbol is #" + this.cur_token.sym);
/*  430:     */     
/*  431:     */ 
/*  432: 857 */     this.stack.removeAllElements();
/*  433: 858 */     this.stack.push(getSymbolFactory().startSymbol("START", 0, start_state()));
/*  434: 859 */     this.tos = 0;
/*  435: 862 */     for (this._done_parsing = false; !this._done_parsing;)
/*  436:     */     {
/*  437: 865 */       if (this.cur_token.used_by_parser) {
/*  438: 866 */         throw new Error("Symbol recycling detected (fix your scanner).");
/*  439:     */       }
/*  440: 872 */       int act = get_action(((Symbol)this.stack.peek()).parse_state, this.cur_token.sym);
/*  441: 875 */       if (act > 0)
/*  442:     */       {
/*  443: 878 */         this.cur_token.parse_state = (act - 1);
/*  444: 879 */         this.cur_token.used_by_parser = true;
/*  445: 880 */         debug_shift(this.cur_token);
/*  446: 881 */         this.stack.push(this.cur_token);
/*  447: 882 */         this.tos += 1;
/*  448:     */         
/*  449:     */ 
/*  450: 885 */         this.cur_token = scan();
/*  451: 886 */         debug_message("# Current token is " + this.cur_token);
/*  452:     */       }
/*  453: 889 */       else if (act < 0)
/*  454:     */       {
/*  455: 892 */         lhs_sym = do_action(-act - 1, this, this.stack, this.tos);
/*  456:     */         
/*  457:     */ 
/*  458: 895 */         short lhs_sym_num = this.production_tab[(-act - 1)][0];
/*  459: 896 */         short handle_size = this.production_tab[(-act - 1)][1];
/*  460:     */         
/*  461: 898 */         debug_reduce(-act - 1, lhs_sym_num, handle_size);
/*  462: 901 */         for (int i = 0; i < handle_size; i++)
/*  463:     */         {
/*  464: 903 */           this.stack.pop();
/*  465: 904 */           this.tos -= 1;
/*  466:     */         }
/*  467: 908 */         act = get_reduce(((Symbol)this.stack.peek()).parse_state, lhs_sym_num);
/*  468: 909 */         debug_message("# Reduce rule: top state " + ((Symbol)this.stack.peek()).parse_state + ", lhs sym " + lhs_sym_num + " -> state " + act);
/*  469:     */         
/*  470:     */ 
/*  471:     */ 
/*  472:     */ 
/*  473: 914 */         lhs_sym.parse_state = act;
/*  474: 915 */         lhs_sym.used_by_parser = true;
/*  475: 916 */         this.stack.push(lhs_sym);
/*  476: 917 */         this.tos += 1;
/*  477:     */         
/*  478: 919 */         debug_message("# Goto state #" + act);
/*  479:     */       }
/*  480: 922 */       else if (act == 0)
/*  481:     */       {
/*  482: 925 */         syntax_error(this.cur_token);
/*  483: 928 */         if (!error_recovery(true))
/*  484:     */         {
/*  485: 931 */           unrecovered_syntax_error(this.cur_token);
/*  486:     */           
/*  487:     */ 
/*  488: 934 */           done_parsing();
/*  489:     */         }
/*  490:     */         else
/*  491:     */         {
/*  492: 936 */           lhs_sym = (Symbol)this.stack.peek();
/*  493:     */         }
/*  494:     */       }
/*  495:     */     }
/*  496: 940 */     return lhs_sym;
/*  497:     */   }
/*  498:     */   
/*  499:     */   protected boolean error_recovery(boolean debug)
/*  500:     */     throws Exception
/*  501:     */   {
/*  502: 972 */     if (debug) {
/*  503: 972 */       debug_message("# Attempting error recovery");
/*  504:     */     }
/*  505: 976 */     if (!find_recovery_config(debug))
/*  506:     */     {
/*  507: 978 */       if (debug) {
/*  508: 978 */         debug_message("# Error recovery fails");
/*  509:     */       }
/*  510: 979 */       return false;
/*  511:     */     }
/*  512: 983 */     read_lookahead();
/*  513:     */     for (;;)
/*  514:     */     {
/*  515: 989 */       if (debug) {
/*  516: 989 */         debug_message("# Trying to parse ahead");
/*  517:     */       }
/*  518: 990 */       if (try_parse_ahead(debug)) {
/*  519:     */         break;
/*  520:     */       }
/*  521: 996 */       if (this.lookahead[0].sym == EOF_sym())
/*  522:     */       {
/*  523: 998 */         if (debug) {
/*  524: 998 */           debug_message("# Error recovery fails at EOF");
/*  525:     */         }
/*  526: 999 */         return false;
/*  527:     */       }
/*  528:1008 */       if (debug) {
/*  529:1009 */         debug_message("# Consuming Symbol #" + this.lookahead[0].sym);
/*  530:     */       }
/*  531:1010 */       restart_lookahead();
/*  532:     */     }
/*  533:1014 */     if (debug) {
/*  534:1014 */       debug_message("# Parse-ahead ok, going back to normal parse");
/*  535:     */     }
/*  536:1017 */     parse_lookahead(debug);
/*  537:     */     
/*  538:     */ 
/*  539:1020 */     return true;
/*  540:     */   }
/*  541:     */   
/*  542:     */   protected boolean shift_under_error()
/*  543:     */   {
/*  544:1031 */     return get_action(((Symbol)this.stack.peek()).parse_state, error_sym()) > 0;
/*  545:     */   }
/*  546:     */   
/*  547:     */   protected boolean find_recovery_config(boolean debug)
/*  548:     */   {
/*  549:1048 */     if (debug) {
/*  550:1048 */       debug_message("# Finding recovery state on stack");
/*  551:     */     }
/*  552:1051 */     Symbol right = (Symbol)this.stack.peek();
/*  553:1052 */     Symbol left = right;
/*  554:1055 */     while (!shift_under_error())
/*  555:     */     {
/*  556:1058 */       if (debug) {
/*  557:1059 */         debug_message("# Pop stack by one, state was # " + ((Symbol)this.stack.peek()).parse_state);
/*  558:     */       }
/*  559:1061 */       left = (Symbol)this.stack.pop();
/*  560:1062 */       this.tos -= 1;
/*  561:1065 */       if (this.stack.empty())
/*  562:     */       {
/*  563:1067 */         if (debug) {
/*  564:1067 */           debug_message("# No recovery state found on stack");
/*  565:     */         }
/*  566:1068 */         return false;
/*  567:     */       }
/*  568:     */     }
/*  569:1073 */     int act = get_action(((Symbol)this.stack.peek()).parse_state, error_sym());
/*  570:1074 */     if (debug)
/*  571:     */     {
/*  572:1076 */       debug_message("# Recover state found (#" + ((Symbol)this.stack.peek()).parse_state + ")");
/*  573:     */       
/*  574:1078 */       debug_message("# Shifting on error to state #" + (act - 1));
/*  575:     */     }
/*  576:1082 */     Symbol error_token = getSymbolFactory().newSymbol("ERROR", error_sym(), left, right);
/*  577:1083 */     error_token.parse_state = (act - 1);
/*  578:1084 */     error_token.used_by_parser = true;
/*  579:1085 */     this.stack.push(error_token);
/*  580:1086 */     this.tos += 1;
/*  581:     */     
/*  582:1088 */     return true;
/*  583:     */   }
/*  584:     */   
/*  585:     */   protected void read_lookahead()
/*  586:     */     throws Exception
/*  587:     */   {
/*  588:1107 */     this.lookahead = new Symbol[error_sync_size()];
/*  589:1110 */     for (int i = 0; i < error_sync_size(); i++)
/*  590:     */     {
/*  591:1112 */       this.lookahead[i] = this.cur_token;
/*  592:1113 */       this.cur_token = scan();
/*  593:     */     }
/*  594:1117 */     this.lookahead_pos = 0;
/*  595:     */   }
/*  596:     */   
/*  597:     */   protected Symbol cur_err_token()
/*  598:     */   {
/*  599:1123 */     return this.lookahead[this.lookahead_pos];
/*  600:     */   }
/*  601:     */   
/*  602:     */   protected boolean advance_lookahead()
/*  603:     */   {
/*  604:1133 */     this.lookahead_pos += 1;
/*  605:     */     
/*  606:     */ 
/*  607:1136 */     return this.lookahead_pos < error_sync_size();
/*  608:     */   }
/*  609:     */   
/*  610:     */   protected void restart_lookahead()
/*  611:     */     throws Exception
/*  612:     */   {
/*  613:1147 */     for (int i = 1; i < error_sync_size(); i++) {
/*  614:1148 */       this.lookahead[(i - 1)] = this.lookahead[i];
/*  615:     */     }
/*  616:1155 */     this.lookahead[(error_sync_size() - 1)] = this.cur_token;
/*  617:1156 */     this.cur_token = scan();
/*  618:     */     
/*  619:     */ 
/*  620:1159 */     this.lookahead_pos = 0;
/*  621:     */   }
/*  622:     */   
/*  623:     */   protected boolean try_parse_ahead(boolean debug)
/*  624:     */     throws Exception
/*  625:     */   {
/*  626:1180 */     virtual_parse_stack vstack = new virtual_parse_stack(this.stack);
/*  627:     */     for (;;)
/*  628:     */     {
/*  629:1186 */       int act = get_action(vstack.top(), cur_err_token().sym);
/*  630:1189 */       if (act == 0) {
/*  631:1189 */         return false;
/*  632:     */       }
/*  633:1192 */       if (act > 0)
/*  634:     */       {
/*  635:1195 */         vstack.push(act - 1);
/*  636:1197 */         if (debug) {
/*  637:1197 */           debug_message("# Parse-ahead shifts Symbol #" + cur_err_token().sym + " into state #" + (act - 1));
/*  638:     */         }
/*  639:1201 */         if (!advance_lookahead()) {
/*  640:1201 */           return true;
/*  641:     */         }
/*  642:     */       }
/*  643:     */       else
/*  644:     */       {
/*  645:1207 */         if (-act - 1 == start_production())
/*  646:     */         {
/*  647:1209 */           if (debug) {
/*  648:1209 */             debug_message("# Parse-ahead accepts");
/*  649:     */           }
/*  650:1210 */           return true;
/*  651:     */         }
/*  652:1214 */         short lhs = this.production_tab[(-act - 1)][0];
/*  653:1215 */         short rhs_size = this.production_tab[(-act - 1)][1];
/*  654:1218 */         for (int i = 0; i < rhs_size; i++) {
/*  655:1219 */           vstack.pop();
/*  656:     */         }
/*  657:1221 */         if (debug) {
/*  658:1222 */           debug_message("# Parse-ahead reduces: handle size = " + rhs_size + " lhs = #" + lhs + " from state #" + vstack.top());
/*  659:     */         }
/*  660:1226 */         vstack.push(get_reduce(vstack.top(), lhs));
/*  661:1227 */         if (debug) {
/*  662:1228 */           debug_message("# Goto state #" + vstack.top());
/*  663:     */         }
/*  664:     */       }
/*  665:     */     }
/*  666:     */   }
/*  667:     */   
/*  668:     */   protected void parse_lookahead(boolean debug)
/*  669:     */     throws Exception
/*  670:     */   {
/*  671:1251 */     Symbol lhs_sym = null;
/*  672:     */     
/*  673:     */ 
/*  674:     */ 
/*  675:     */ 
/*  676:     */ 
/*  677:1257 */     this.lookahead_pos = 0;
/*  678:1259 */     if (debug)
/*  679:     */     {
/*  680:1261 */       debug_message("# Reparsing saved input with actions");
/*  681:1262 */       debug_message("# Current Symbol is #" + cur_err_token().sym);
/*  682:1263 */       debug_message("# Current state is #" + ((Symbol)this.stack.peek()).parse_state);
/*  683:     */     }
/*  684:1268 */     while (!this._done_parsing)
/*  685:     */     {
/*  686:1273 */       int act = get_action(((Symbol)this.stack.peek()).parse_state, cur_err_token().sym);
/*  687:1277 */       if (act > 0)
/*  688:     */       {
/*  689:1280 */         cur_err_token().parse_state = (act - 1);
/*  690:1281 */         cur_err_token().used_by_parser = true;
/*  691:1282 */         if (debug) {
/*  692:1282 */           debug_shift(cur_err_token());
/*  693:     */         }
/*  694:1283 */         this.stack.push(cur_err_token());
/*  695:1284 */         this.tos += 1;
/*  696:1287 */         if (!advance_lookahead())
/*  697:     */         {
/*  698:1289 */           if (debug) {
/*  699:1289 */             debug_message("# Completed reparse");
/*  700:     */           }
/*  701:1298 */           return;
/*  702:     */         }
/*  703:1301 */         if (debug) {
/*  704:1302 */           debug_message("# Current Symbol is #" + cur_err_token().sym);
/*  705:     */         }
/*  706:     */       }
/*  707:1305 */       else if (act < 0)
/*  708:     */       {
/*  709:1308 */         lhs_sym = do_action(-act - 1, this, this.stack, this.tos);
/*  710:     */         
/*  711:     */ 
/*  712:1311 */         short lhs_sym_num = this.production_tab[(-act - 1)][0];
/*  713:1312 */         short handle_size = this.production_tab[(-act - 1)][1];
/*  714:1314 */         if (debug) {
/*  715:1314 */           debug_reduce(-act - 1, lhs_sym_num, handle_size);
/*  716:     */         }
/*  717:1317 */         for (int i = 0; i < handle_size; i++)
/*  718:     */         {
/*  719:1319 */           this.stack.pop();
/*  720:1320 */           this.tos -= 1;
/*  721:     */         }
/*  722:1324 */         act = get_reduce(((Symbol)this.stack.peek()).parse_state, lhs_sym_num);
/*  723:     */         
/*  724:     */ 
/*  725:1327 */         lhs_sym.parse_state = act;
/*  726:1328 */         lhs_sym.used_by_parser = true;
/*  727:1329 */         this.stack.push(lhs_sym);
/*  728:1330 */         this.tos += 1;
/*  729:1332 */         if (debug) {
/*  730:1332 */           debug_message("# Goto state #" + act);
/*  731:     */         }
/*  732:     */       }
/*  733:1337 */       else if (act == 0)
/*  734:     */       {
/*  735:1339 */         report_fatal_error("Syntax error", lhs_sym);
/*  736:1340 */         return;
/*  737:     */       }
/*  738:     */     }
/*  739:     */   }
/*  740:     */   
/*  741:     */   protected static short[][] unpackFromStrings(String[] sa)
/*  742:     */   {
/*  743:1353 */     StringBuffer sb = new StringBuffer(sa[0]);
/*  744:1354 */     for (int i = 1; i < sa.length; i++) {
/*  745:1355 */       sb.append(sa[i]);
/*  746:     */     }
/*  747:1356 */     int n = 0;
/*  748:1357 */     int size1 = sb.charAt(n) << '\020' | sb.charAt(n + 1);n += 2;
/*  749:1358 */     short[][] result = new short[size1][];
/*  750:1359 */     for (int i = 0; i < size1; i++)
/*  751:     */     {
/*  752:1360 */       int size2 = sb.charAt(n) << '\020' | sb.charAt(n + 1);n += 2;
/*  753:1361 */       result[i] = new short[size2];
/*  754:1362 */       for (int j = 0; j < size2; j++) {
/*  755:1363 */         result[i][j] = ((short)(sb.charAt(n++) - '\002'));
/*  756:     */       }
/*  757:     */     }
/*  758:1365 */     return result;
/*  759:     */   }
/*  760:     */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.runtime.lr_parser
 * JD-Core Version:    0.7.0.1
 */
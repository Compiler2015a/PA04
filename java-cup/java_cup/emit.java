/*    1:     */ package java_cup;
/*    2:     */ 
/*    3:     */ import java.io.PrintWriter;
/*    4:     */ import java.util.Enumeration;
/*    5:     */ import java.util.Stack;
/*    6:     */ 
/*    7:     */ public class emit
/*    8:     */ {
/*    9: 111 */   public static String prefix = "CUP$";
/*   10: 116 */   public static String package_name = null;
/*   11: 121 */   public static String symbol_const_class_name = "sym";
/*   12: 126 */   public static String parser_class_name = "parser";
/*   13: 131 */   public static String class_type_argument = null;
/*   14: 136 */   public static String action_code = null;
/*   15: 141 */   public static String parser_code = null;
/*   16: 146 */   public static String init_code = null;
/*   17: 151 */   public static String scan_code = null;
/*   18: 156 */   public static production start_production = null;
/*   19: 161 */   public static Stack import_list = new Stack();
/*   20: 166 */   public static int num_conflicts = 0;
/*   21: 171 */   public static boolean nowarn = false;
/*   22: 176 */   public static int not_reduced = 0;
/*   23: 181 */   public static int unused_term = 0;
/*   24: 186 */   public static int unused_non_term = 0;
/*   25: 193 */   public static long symbols_time = 0L;
/*   26: 196 */   public static long parser_time = 0L;
/*   27: 199 */   public static long action_code_time = 0L;
/*   28: 202 */   public static long production_table_time = 0L;
/*   29: 205 */   public static long action_table_time = 0L;
/*   30: 208 */   public static long goto_table_time = 0L;
/*   31:     */   protected static boolean _lr_values;
/*   32:     */   protected static boolean _locations;
/*   33:     */   protected static boolean _xmlactions;
/*   34:     */   protected static boolean _genericlabels;
/*   35:     */   static final int UPPERLIMIT = 300;
/*   36:     */   
/*   37:     */   public static boolean lr_values()
/*   38:     */   {
/*   39: 217 */     return _lr_values;
/*   40:     */   }
/*   41:     */   
/*   42:     */   public static boolean locations()
/*   43:     */   {
/*   44: 218 */     return _locations;
/*   45:     */   }
/*   46:     */   
/*   47:     */   protected static void set_lr_values(boolean b)
/*   48:     */   {
/*   49: 219 */     _lr_values = b;
/*   50:     */   }
/*   51:     */   
/*   52:     */   protected static void set_locations(boolean b)
/*   53:     */   {
/*   54: 220 */     _locations = b;
/*   55:     */   }
/*   56:     */   
/*   57:     */   protected static void set_genericlabels(boolean b)
/*   58:     */   {
/*   59: 221 */     _genericlabels = b;
/*   60:     */   }
/*   61:     */   
/*   62:     */   protected static void set_xmlactions(boolean b)
/*   63:     */   {
/*   64: 222 */     _xmlactions = b;
/*   65: 223 */     if (!b) {
/*   66: 223 */       return;
/*   67:     */     }
/*   68: 224 */     _locations = true;
/*   69: 225 */     _lr_values = true;
/*   70:     */   }
/*   71:     */   
/*   72:     */   public static void clear()
/*   73:     */   {
/*   74: 229 */     _genericlabels = false;
/*   75: 230 */     _xmlactions = false;
/*   76: 231 */     _locations = false;
/*   77: 232 */     _lr_values = true;
/*   78: 233 */     action_code = null;
/*   79: 234 */     import_list = new Stack();
/*   80: 235 */     init_code = null;
/*   81: 236 */     not_reduced = 0;
/*   82: 237 */     num_conflicts = 0;
/*   83: 238 */     package_name = null;
/*   84: 239 */     parser_class_name = "parser";
/*   85: 240 */     parser_code = null;
/*   86: 241 */     scan_code = null;
/*   87: 242 */     start_production = null;
/*   88: 243 */     symbol_const_class_name = "sym";
/*   89: 244 */     unused_non_term = 0;
/*   90: 245 */     unused_term = 0;
/*   91:     */   }
/*   92:     */   
/*   93:     */   protected static String pre(String str)
/*   94:     */   {
/*   95: 256 */     return prefix + parser_class_name + "$" + str;
/*   96:     */   }
/*   97:     */   
/*   98:     */   protected static String typeArgument()
/*   99:     */   {
/*  100: 265 */     return "<" + class_type_argument + ">";
/*  101:     */   }
/*  102:     */   
/*  103:     */   protected static void emit_package(PrintWriter out)
/*  104:     */   {
/*  105: 276 */     if (package_name != null)
/*  106:     */     {
/*  107: 277 */       out.println("package " + package_name + ";");out.println();
/*  108:     */     }
/*  109:     */   }
/*  110:     */   
/*  111:     */   public static void symbols(PrintWriter out, boolean emit_non_terms, boolean sym_interface)
/*  112:     */   {
/*  113: 294 */     String class_or_interface = sym_interface ? "interface" : "class";
/*  114:     */     
/*  115: 296 */     long start_time = System.currentTimeMillis();
/*  116:     */     
/*  117:     */ 
/*  118: 299 */     out.println();
/*  119: 300 */     out.println("//----------------------------------------------------");
/*  120: 301 */     out.println("// The following code was generated by CUP v0.11b 20140808 (SVN rev 54)");
/*  121: 302 */     out.println("//----------------------------------------------------");
/*  122: 303 */     out.println();
/*  123: 304 */     emit_package(out);
/*  124:     */     
/*  125:     */ 
/*  126: 307 */     out.println("/** CUP generated " + class_or_interface + " containing symbol constants. */");
/*  127:     */     
/*  128: 309 */     out.println("public " + class_or_interface + " " + symbol_const_class_name + " {");
/*  129:     */     
/*  130:     */ 
/*  131: 312 */     out.println("  /* terminals */");
/*  132: 315 */     for (Enumeration e = terminal.all(); e.hasMoreElements();)
/*  133:     */     {
/*  134: 317 */       terminal term = (terminal)e.nextElement();
/*  135:     */       
/*  136:     */ 
/*  137: 320 */       out.println("  public static final int " + term.name() + " = " + term.index() + ";");
/*  138:     */     }
/*  139: 325 */     out.println("  public static final String[] terminalNames = new String[] {");
/*  140: 326 */     for (int i = 0; i < terminal.number(); i++)
/*  141:     */     {
/*  142: 327 */       out.print("  \"");
/*  143: 328 */       out.print(terminal.find(i).name());
/*  144: 329 */       out.print("\"");
/*  145: 330 */       if (i < terminal.number() - 1) {
/*  146: 331 */         out.print(",");
/*  147:     */       }
/*  148: 333 */       out.println();
/*  149:     */     }
/*  150: 335 */     out.println("  };");
/*  151:     */     Enumeration e;
/*  152: 338 */     if (emit_non_terms)
/*  153:     */     {
/*  154: 340 */       out.println();
/*  155: 341 */       out.println("  /* non terminals */");
/*  156: 344 */       for (e = non_terminal.all(); e.hasMoreElements();)
/*  157:     */       {
/*  158: 346 */         non_terminal nt = (non_terminal)e.nextElement();
/*  159:     */         
/*  160:     */ 
/*  161:     */ 
/*  162:     */ 
/*  163:     */ 
/*  164:     */ 
/*  165: 353 */         out.println("  static final int " + nt.name() + " = " + nt.index() + ";");
/*  166:     */       }
/*  167:     */     }
/*  168: 359 */     out.println("}");
/*  169: 360 */     out.println();
/*  170:     */     
/*  171: 362 */     symbols_time = System.currentTimeMillis() - start_time;
/*  172:     */   }
/*  173:     */   
/*  174:     */   protected static void emit_action_code(PrintWriter out, production start_prod)
/*  175:     */     throws internal_error
/*  176:     */   {
/*  177: 377 */     long start_time = System.currentTimeMillis();
/*  178:     */     
/*  179:     */ 
/*  180: 380 */     out.println();
/*  181: 381 */     out.println("/** Cup generated class to encapsulate user supplied action code.*/");
/*  182:     */     
/*  183:     */ 
/*  184: 384 */     out.println("@SuppressWarnings({\"rawtypes\", \"unchecked\", \"unused\"})");
/*  185:     */     
/*  186: 386 */     out.println("class " + pre("actions") + typeArgument() + " {");
/*  187: 388 */     if (action_code != null)
/*  188:     */     {
/*  189: 390 */       out.println();
/*  190: 391 */       out.println(action_code);
/*  191:     */     }
/*  192: 396 */     out.println("  private final " + parser_class_name + typeArgument() + " parser;");
/*  193:     */     
/*  194:     */ 
/*  195: 399 */     out.println();
/*  196: 400 */     out.println("  /** Constructor */");
/*  197:     */     
/*  198: 402 */     out.println("  " + pre("actions") + "(" + parser_class_name + typeArgument() + " parser) {");
/*  199: 403 */     out.println("    this.parser = parser;");
/*  200: 404 */     out.println("  }");
/*  201:     */     
/*  202: 406 */     out.println();
/*  203: 407 */     for (int instancecounter = 0; instancecounter <= production.number() / 300; instancecounter++)
/*  204:     */     {
/*  205: 408 */       out.println("  /** Method " + instancecounter + " with the actual generated action code for actions " + instancecounter * 300 + " to " + (instancecounter + 1) * 300 + ". */");
/*  206: 409 */       out.println("  public final java_cup.runtime.Symbol " + pre("do_action_part") + String.format("%08d", new Object[] { new Integer(instancecounter) }) + "(");
/*  207:     */       
/*  208: 411 */       out.println("    int                        " + pre("act_num,"));
/*  209: 412 */       out.println("    java_cup.runtime.lr_parser " + pre("parser,"));
/*  210: 413 */       out.println("    java.util.Stack            " + pre("stack,"));
/*  211: 414 */       out.println("    int                        " + pre("top)"));
/*  212: 415 */       out.println("    throws java.lang.Exception");
/*  213: 416 */       out.println("    {");
/*  214: 417 */       out.println("      /* Symbol object for return from actions */");
/*  215: 418 */       out.println("      java_cup.runtime.Symbol " + pre("result") + ";");
/*  216: 419 */       out.println();
/*  217: 420 */       out.println("      /* select the action based on the action number */");
/*  218: 421 */       out.println("      switch (" + pre("act_num") + ")");
/*  219: 422 */       out.println("        {");
/*  220:     */       
/*  221:     */ 
/*  222: 425 */       int proditeration = instancecounter * 300;
/*  223: 426 */       for (production prod = production.find(proditeration); proditeration < Math.min((instancecounter + 1) * 300, production.number()); prod = production.find(proditeration))
/*  224:     */       {
/*  225: 430 */         out.println("          /*. . . . . . . . . . . . . . . . . . . .*/");
/*  226: 431 */         out.println("          case " + prod.index() + ": // " + prod.to_simple_string());
/*  227:     */         
/*  228:     */ 
/*  229:     */ 
/*  230: 435 */         out.println("            {");
/*  231:     */         
/*  232:     */ 
/*  233:     */ 
/*  234:     */ 
/*  235:     */ 
/*  236: 441 */         String result = "null";
/*  237: 442 */         if ((prod instanceof action_production))
/*  238:     */         {
/*  239: 443 */           int lastResult = ((action_production)prod).getIndexOfIntermediateResult();
/*  240: 444 */           if (lastResult != -1) {
/*  241: 445 */             result = "(" + prod.lhs().the_symbol().stack_type() + ") " + "((java_cup.runtime.Symbol) " + pre("stack") + (lastResult == 1 ? ".peek()" : new StringBuilder().append(".elementAt(").append(pre("top")).append("-").append(lastResult - 1).append(")").toString()) + ").value";
/*  242:     */           }
/*  243:     */         }
/*  244: 457 */         out.println("              " + prod.lhs().the_symbol().stack_type() + " RESULT =" + result + ";");
/*  245: 464 */         for (int i = prod.rhs_length() - 1; i >= 0; i--) {
/*  246: 466 */           if ((prod.rhs(i) instanceof symbol_part))
/*  247:     */           {
/*  248: 467 */             symbol s = ((symbol_part)prod.rhs(i)).the_symbol();
/*  249: 468 */             if ((s instanceof non_terminal)) {
/*  250: 471 */               if (((non_terminal)s).is_embedded_action)
/*  251:     */               {
/*  252: 473 */                 int index = prod.rhs_length() - i - 1;
/*  253:     */                 
/*  254: 475 */                 out.println("              // propagate RESULT from " + s.name());
/*  255:     */                 
/*  256:     */ 
/*  257:     */ 
/*  258:     */ 
/*  259:     */ 
/*  260:     */ 
/*  261:     */ 
/*  262:     */ 
/*  263:     */ 
/*  264:     */ 
/*  265: 486 */                 out.println("                RESULT = (" + prod.lhs().the_symbol().stack_type() + ") " + "((java_cup.runtime.Symbol) " + pre("stack") + (index == 0 ? ".peek()" : new StringBuilder().append(".elementAt(").append(pre("top")).append("-").append(index).append(")").toString()) + ").value;");
/*  266:     */                 
/*  267:     */ 
/*  268:     */ 
/*  269:     */ 
/*  270:     */ 
/*  271: 492 */                 break;
/*  272:     */               }
/*  273:     */             }
/*  274:     */           }
/*  275:     */         }
/*  276: 496 */         if ((prod.action() != null) && (prod.action().code_string() != null) && (!prod.action().equals(""))) {
/*  277: 498 */           out.println(prod.action().code_string());
/*  278:     */         }
/*  279: 506 */         if (lr_values())
/*  280:     */         {
/*  281: 511 */           String rightstring = "((java_cup.runtime.Symbol)" + pre("stack") + ".peek()" + ")";
/*  282:     */           String leftstring;
/*  283:     */           String leftstring;
/*  284: 517 */           if (prod.rhs_length() == 0)
/*  285:     */           {
/*  286: 518 */             leftstring = rightstring;
/*  287:     */           }
/*  288:     */           else
/*  289:     */           {
/*  290: 520 */             int loffset = prod.rhs_length() - 1;
/*  291: 521 */             leftstring = "((java_cup.runtime.Symbol)" + pre("stack") + (loffset == 0 ? ".peek()" : new StringBuilder().append(".elementAt(").append(pre("top")).append("-").append(loffset).append(")").toString()) + ")";
/*  292:     */           }
/*  293: 527 */           out.println("              " + pre("result") + " = parser.getSymbolFactory().newSymbol(" + "\"" + prod.lhs().the_symbol().name() + "\"," + prod.lhs().the_symbol().index() + ", " + leftstring + ", " + rightstring + ", RESULT);");
/*  294:     */         }
/*  295:     */         else
/*  296:     */         {
/*  297: 532 */           out.println("              " + pre("result") + " = parser.getSymbolFactory().newSymbol(" + "\"" + prod.lhs().the_symbol().name() + "\"," + prod.lhs().the_symbol().index() + ", RESULT);");
/*  298:     */         }
/*  299: 539 */         out.println("            }");
/*  300: 542 */         if (prod == start_prod)
/*  301:     */         {
/*  302: 544 */           out.println("          /* ACCEPT */");
/*  303: 545 */           out.println("          " + pre("parser") + ".done_parsing();");
/*  304:     */         }
/*  305: 549 */         out.println("          return " + pre("result") + ";");
/*  306: 550 */         out.println();proditeration++;
/*  307:     */       }
/*  308: 554 */       out.println("          /* . . . . . .*/");
/*  309: 555 */       out.println("          default:");
/*  310: 556 */       out.println("            throw new Exception(");
/*  311: 557 */       out.println("               \"Invalid action number \"+" + pre("act_num") + "+\"found in " + "internal parse table\");");
/*  312:     */       
/*  313: 559 */       out.println();
/*  314: 560 */       out.println("        }");
/*  315: 561 */       out.println("    } /* end of method */");
/*  316:     */     }
/*  317: 565 */     out.println();
/*  318: 566 */     out.println("  /** Method splitting the generated action code into several parts. */");
/*  319: 567 */     out.println("  public final java_cup.runtime.Symbol " + pre("do_action") + "(");
/*  320:     */     
/*  321: 569 */     out.println("    int                        " + pre("act_num,"));
/*  322: 570 */     out.println("    java_cup.runtime.lr_parser " + pre("parser,"));
/*  323: 571 */     out.println("    java.util.Stack            " + pre("stack,"));
/*  324: 572 */     out.println("    int                        " + pre("top)"));
/*  325: 573 */     out.println("    throws java.lang.Exception");
/*  326: 574 */     out.println("    {");
/*  327: 576 */     if (production.number() < 300)
/*  328:     */     {
/*  329: 577 */       out.println("              return " + pre("do_action_part") + String.format("%08d", new Object[] { new Integer(0) }) + "(");
/*  330: 578 */       out.println("                               " + pre("act_num,"));
/*  331: 579 */       out.println("                               " + pre("parser,"));
/*  332: 580 */       out.println("                               " + pre("stack,"));
/*  333: 581 */       out.println("                               " + pre("top);"));
/*  334: 582 */       out.println("    }");
/*  335:     */       
/*  336:     */ 
/*  337: 585 */       out.println("}");
/*  338: 586 */       out.println();
/*  339:     */       
/*  340: 588 */       action_code_time = System.currentTimeMillis() - start_time;
/*  341: 589 */       return;
/*  342:     */     }
/*  343: 593 */     out.println("      /* select the action handler based on the action number */");
/*  344: 594 */     out.println("      switch (" + pre("act_num") + "/" + 300 + ")");
/*  345: 595 */     out.println("        {");
/*  346: 598 */     for (int instancecounter = 0; instancecounter <= production.number() / 300; instancecounter++)
/*  347:     */     {
/*  348: 600 */       out.println("          /*. . . . . . . . " + instancecounter * 300 + " < #action < " + (instancecounter + 1) * 300 + ". . . . . . . . . . . .*/");
/*  349: 601 */       out.println("          case " + instancecounter + ": ");
/*  350: 602 */       out.println("              return " + pre("do_action_part") + String.format("%08d", new Object[] { new Integer(instancecounter) }) + "(");
/*  351: 603 */       out.println("                               " + pre("act_num,"));
/*  352: 604 */       out.println("                               " + pre("parser,"));
/*  353: 605 */       out.println("                               " + pre("stack,"));
/*  354: 606 */       out.println("                               " + pre("top);"));
/*  355:     */     }
/*  356: 609 */     out.println("          /* . . . no valid action number: . . .*/");
/*  357: 610 */     out.println("          default:");
/*  358: 611 */     out.println("            throw new Exception(\"Invalid action number found in internal parse table\");");
/*  359: 612 */     out.println();
/*  360: 613 */     out.println("        }      /* end of switch */");
/*  361:     */     
/*  362:     */ 
/*  363: 616 */     out.println("    }");
/*  364:     */     
/*  365:     */ 
/*  366: 619 */     out.println("}");
/*  367: 620 */     out.println();
/*  368:     */     
/*  369: 622 */     action_code_time = System.currentTimeMillis() - start_time;
/*  370:     */   }
/*  371:     */   
/*  372:     */   protected static void emit_production_table(PrintWriter out)
/*  373:     */   {
/*  374: 635 */     long start_time = System.currentTimeMillis();
/*  375:     */     
/*  376:     */ 
/*  377: 638 */     production[] all_prods = new production[production.number()];
/*  378: 639 */     for (Enumeration p = production.all(); p.hasMoreElements();)
/*  379:     */     {
/*  380: 641 */       production prod = (production)p.nextElement();
/*  381: 642 */       all_prods[prod.index()] = prod;
/*  382:     */     }
/*  383: 646 */     short[][] prod_table = new short[production.number()][2];
/*  384: 647 */     for (int i = 0; i < production.number(); i++)
/*  385:     */     {
/*  386: 649 */       production prod = all_prods[i];
/*  387:     */       
/*  388: 651 */       prod_table[i][0] = ((short)prod.lhs().the_symbol().index());
/*  389: 652 */       prod_table[i][1] = ((short)prod.rhs_length());
/*  390:     */     }
/*  391: 655 */     out.println();
/*  392: 656 */     out.println("  /** Production table. */");
/*  393: 657 */     out.println("  protected static final short _production_table[][] = ");
/*  394: 658 */     out.print("    unpackFromStrings(");
/*  395: 659 */     do_table_as_string(out, prod_table);
/*  396: 660 */     out.println(");");
/*  397:     */     
/*  398:     */ 
/*  399: 663 */     out.println();
/*  400: 664 */     out.println("  /** Access to production table. */");
/*  401: 665 */     out.println("  public short[][] production_table() {return _production_table;}");
/*  402:     */     
/*  403:     */ 
/*  404: 668 */     production_table_time = System.currentTimeMillis() - start_time;
/*  405:     */   }
/*  406:     */   
/*  407:     */   protected static void do_action_table(PrintWriter out, parse_action_table act_tab, boolean compact_reduces)
/*  408:     */     throws internal_error
/*  409:     */   {
/*  410: 688 */     long start_time = System.currentTimeMillis();
/*  411:     */     
/*  412:     */ 
/*  413: 691 */     short[][] action_table = new short[act_tab.num_states()][];
/*  414: 693 */     for (int i = 0; i < act_tab.num_states(); i++)
/*  415:     */     {
/*  416: 696 */       parse_action_row row = act_tab.under_state[i];
/*  417: 699 */       if (compact_reduces) {
/*  418: 700 */         row.compute_default();
/*  419:     */       } else {
/*  420: 702 */         row.default_reduce = -1;
/*  421:     */       }
/*  422: 705 */       short[] temp_table = new short[2 * parse_action_row.size()];
/*  423: 706 */       int nentries = 0;
/*  424: 709 */       for (int j = 0; j < parse_action_row.size(); j++)
/*  425:     */       {
/*  426: 712 */         parse_action act = row.under_term[j];
/*  427: 715 */         if (act.kind() != 0) {
/*  428: 720 */           if (act.kind() == 1)
/*  429:     */           {
/*  430: 723 */             temp_table[(nentries++)] = ((short)j);
/*  431: 724 */             temp_table[(nentries++)] = ((short)(((shift_action)act).shift_to().index() + 1));
/*  432:     */           }
/*  433: 729 */           else if (act.kind() == 2)
/*  434:     */           {
/*  435: 732 */             int red = ((reduce_action)act).reduce_with().index();
/*  436: 733 */             if (red != row.default_reduce)
/*  437:     */             {
/*  438: 735 */               temp_table[(nentries++)] = ((short)j);
/*  439: 736 */               temp_table[(nentries++)] = ((short)-(red + 1));
/*  440:     */             }
/*  441:     */           }
/*  442: 738 */           else if (act.kind() != 3)
/*  443:     */           {
/*  444: 744 */             throw new internal_error("Unrecognized action code " + act.kind() + " found in parse table");
/*  445:     */           }
/*  446:     */         }
/*  447:     */       }
/*  448: 750 */       action_table[i] = new short[nentries + 2];
/*  449: 751 */       System.arraycopy(temp_table, 0, action_table[i], 0, nentries);
/*  450:     */       
/*  451:     */ 
/*  452: 754 */       action_table[i][(nentries++)] = -1;
/*  453: 755 */       if (row.default_reduce != -1) {
/*  454: 756 */         action_table[i][(nentries++)] = ((short)-(row.default_reduce + 1));
/*  455:     */       } else {
/*  456: 758 */         action_table[i][(nentries++)] = 0;
/*  457:     */       }
/*  458:     */     }
/*  459: 762 */     out.println();
/*  460: 763 */     out.println("  /** Parse-action table. */");
/*  461: 764 */     out.println("  protected static final short[][] _action_table = ");
/*  462: 765 */     out.print("    unpackFromStrings(");
/*  463: 766 */     do_table_as_string(out, action_table);
/*  464: 767 */     out.println(");");
/*  465:     */     
/*  466:     */ 
/*  467: 770 */     out.println();
/*  468: 771 */     out.println("  /** Access to parse-action table. */");
/*  469: 772 */     out.println("  public short[][] action_table() {return _action_table;}");
/*  470:     */     
/*  471: 774 */     action_table_time = System.currentTimeMillis() - start_time;
/*  472:     */   }
/*  473:     */   
/*  474:     */   protected static void do_reduce_table(PrintWriter out, parse_reduce_table red_tab)
/*  475:     */   {
/*  476: 790 */     long start_time = System.currentTimeMillis();
/*  477:     */     
/*  478:     */ 
/*  479: 793 */     short[][] reduce_goto_table = new short[red_tab.num_states()][];
/*  480: 795 */     for (int i = 0; i < red_tab.num_states(); i++)
/*  481:     */     {
/*  482: 798 */       short[] temp_table = new short[2 * parse_reduce_row.size()];
/*  483: 799 */       int nentries = 0;
/*  484: 801 */       for (int j = 0; j < parse_reduce_row.size(); j++)
/*  485:     */       {
/*  486: 804 */         lalr_state goto_st = red_tab.under_state[i].under_non_term[j];
/*  487: 807 */         if (goto_st != null)
/*  488:     */         {
/*  489: 810 */           temp_table[(nentries++)] = ((short)j);
/*  490: 811 */           temp_table[(nentries++)] = ((short)goto_st.index());
/*  491:     */         }
/*  492:     */       }
/*  493: 815 */       reduce_goto_table[i] = new short[nentries + 2];
/*  494: 816 */       System.arraycopy(temp_table, 0, reduce_goto_table[i], 0, nentries);
/*  495:     */       
/*  496:     */ 
/*  497: 819 */       reduce_goto_table[i][(nentries++)] = -1;
/*  498: 820 */       reduce_goto_table[i][(nentries++)] = -1;
/*  499:     */     }
/*  500: 824 */     out.println();
/*  501: 825 */     out.println("  /** <code>reduce_goto</code> table. */");
/*  502: 826 */     out.println("  protected static final short[][] _reduce_table = ");
/*  503: 827 */     out.print("    unpackFromStrings(");
/*  504: 828 */     do_table_as_string(out, reduce_goto_table);
/*  505: 829 */     out.println(");");
/*  506:     */     
/*  507:     */ 
/*  508: 832 */     out.println();
/*  509: 833 */     out.println("  /** Access to <code>reduce_goto</code> table. */");
/*  510: 834 */     out.println("  public short[][] reduce_table() {return _reduce_table;}");
/*  511: 835 */     out.println();
/*  512:     */     
/*  513: 837 */     goto_table_time = System.currentTimeMillis() - start_time;
/*  514:     */   }
/*  515:     */   
/*  516:     */   protected static void do_table_as_string(PrintWriter out, short[][] sa)
/*  517:     */   {
/*  518: 842 */     out.println("new String[] {");
/*  519: 843 */     out.print("    \"");
/*  520: 844 */     int nchar = 0;int nbytes = 0;
/*  521: 845 */     nbytes += do_escaped(out, (char)(sa.length >> 16));
/*  522: 846 */     nchar = do_newline(out, nchar, nbytes);
/*  523: 847 */     nbytes += do_escaped(out, (char)(sa.length & 0xFFFF));
/*  524: 848 */     nchar = do_newline(out, nchar, nbytes);
/*  525: 849 */     for (int i = 0; i < sa.length; i++)
/*  526:     */     {
/*  527: 850 */       nbytes += do_escaped(out, (char)(sa[i].length >> 16));
/*  528: 851 */       nchar = do_newline(out, nchar, nbytes);
/*  529: 852 */       nbytes += do_escaped(out, (char)(sa[i].length & 0xFFFF));
/*  530: 853 */       nchar = do_newline(out, nchar, nbytes);
/*  531: 854 */       for (int j = 0; j < sa[i].length; j++)
/*  532:     */       {
/*  533: 857 */         nbytes += do_escaped(out, (char)(2 + sa[i][j]));
/*  534: 858 */         nchar = do_newline(out, nchar, nbytes);
/*  535:     */       }
/*  536:     */     }
/*  537: 861 */     out.print("\" }");
/*  538:     */   }
/*  539:     */   
/*  540:     */   protected static int do_newline(PrintWriter out, int nchar, int nbytes)
/*  541:     */   {
/*  542: 865 */     if (nbytes > 65500)
/*  543:     */     {
/*  544: 865 */       out.println("\", ");out.print("    \"");
/*  545:     */     }
/*  546: 866 */     else if (nchar > 11)
/*  547:     */     {
/*  548: 866 */       out.println("\" +");out.print("    \"");
/*  549:     */     }
/*  550:     */     else
/*  551:     */     {
/*  552: 867 */       return nchar + 1;
/*  553:     */     }
/*  554: 868 */     return 0;
/*  555:     */   }
/*  556:     */   
/*  557:     */   protected static int do_escaped(PrintWriter out, char c)
/*  558:     */   {
/*  559: 872 */     StringBuffer escape = new StringBuffer();
/*  560: 873 */     if (c <= 'ÿ')
/*  561:     */     {
/*  562: 874 */       escape.append(Integer.toOctalString(c));
/*  563: 875 */       while (escape.length() < 3) {
/*  564: 875 */         escape.insert(0, '0');
/*  565:     */       }
/*  566:     */     }
/*  567: 877 */     escape.append(Integer.toHexString(c));
/*  568: 878 */     while (escape.length() < 4) {
/*  569: 878 */       escape.insert(0, '0');
/*  570:     */     }
/*  571: 879 */     escape.insert(0, 'u');
/*  572:     */     
/*  573: 881 */     escape.insert(0, '\\');
/*  574: 882 */     out.print(escape.toString());
/*  575: 885 */     if (c == 0) {
/*  576: 885 */       return 2;
/*  577:     */     }
/*  578: 886 */     if ((c >= '\001') && (c <= '')) {
/*  579: 886 */       return 1;
/*  580:     */     }
/*  581: 887 */     if ((c >= '') && (c <= '߿')) {
/*  582: 887 */       return 2;
/*  583:     */     }
/*  584: 888 */     return 3;
/*  585:     */   }
/*  586:     */   
/*  587:     */   public static void parser(PrintWriter out, parse_action_table action_table, parse_reduce_table reduce_table, int start_st, production start_prod, boolean compact_reduces, boolean suppress_scanner)
/*  588:     */     throws internal_error
/*  589:     */   {
/*  590: 912 */     long start_time = System.currentTimeMillis();
/*  591:     */     
/*  592:     */ 
/*  593: 915 */     out.println();
/*  594: 916 */     out.println("//----------------------------------------------------");
/*  595: 917 */     out.println("// The following code was generated by CUP v0.11b 20140808 (SVN rev 54)");
/*  596: 918 */     out.println("//----------------------------------------------------");
/*  597: 919 */     out.println();
/*  598: 920 */     emit_package(out);
/*  599: 923 */     for (int i = 0; i < import_list.size(); i++) {
/*  600: 924 */       out.println("import " + import_list.elementAt(i) + ";");
/*  601:     */     }
/*  602: 925 */     if (locations()) {
/*  603: 926 */       out.println("import java_cup.runtime.ComplexSymbolFactory.Location;");
/*  604:     */     }
/*  605: 927 */     out.println("import java_cup.runtime.XMLElement;");
/*  606:     */     
/*  607:     */ 
/*  608: 930 */     out.println();
/*  609: 931 */     out.println("/** CUP v0.11b 20140808 (SVN rev 54) generated parser.");
/*  610: 932 */     out.println("  */");
/*  611: 933 */     out.println("@SuppressWarnings({\"rawtypes\"})");
/*  612:     */     
/*  613: 935 */     out.println("public class " + parser_class_name + typeArgument() + " extends java_cup.runtime.lr_parser {");
/*  614:     */     
/*  615:     */ 
/*  616: 938 */     out.println();
/*  617: 939 */     out.println(" public final Class getSymbolContainer() {");
/*  618: 940 */     out.println("    return " + symbol_const_class_name + ".class;");
/*  619: 941 */     out.println("}");
/*  620:     */     
/*  621:     */ 
/*  622: 944 */     out.println();
/*  623: 945 */     out.println("  /** Default constructor. */");
/*  624: 946 */     out.println("  public " + parser_class_name + "() {super();}");
/*  625: 947 */     if (!suppress_scanner)
/*  626:     */     {
/*  627: 948 */       out.println();
/*  628: 949 */       out.println("  /** Constructor which sets the default scanner. */");
/*  629: 950 */       out.println("  public " + parser_class_name + "(java_cup.runtime.Scanner s) {super(s);}");
/*  630:     */       
/*  631:     */ 
/*  632: 953 */       out.println();
/*  633: 954 */       out.println("  /** Constructor which sets the default scanner. */");
/*  634: 955 */       out.println("  public " + parser_class_name + "(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}");
/*  635:     */     }
/*  636: 960 */     emit_production_table(out);
/*  637: 961 */     do_action_table(out, action_table, compact_reduces);
/*  638: 962 */     do_reduce_table(out, reduce_table);
/*  639:     */     
/*  640:     */ 
/*  641: 965 */     out.println("  /** Instance of action encapsulation class. */");
/*  642: 966 */     out.println("  protected " + pre("actions") + " action_obj;");
/*  643: 967 */     out.println();
/*  644:     */     
/*  645:     */ 
/*  646: 970 */     out.println("  /** Action encapsulation object initializer. */");
/*  647: 971 */     out.println("  protected void init_actions()");
/*  648: 972 */     out.println("    {");
/*  649:     */     
/*  650: 974 */     out.println("      action_obj = new " + pre("actions") + typeArgument() + "(this);");
/*  651: 975 */     out.println("    }");
/*  652: 976 */     out.println();
/*  653:     */     
/*  654:     */ 
/*  655: 979 */     out.println("  /** Invoke a user supplied parse action. */");
/*  656: 980 */     out.println("  public java_cup.runtime.Symbol do_action(");
/*  657: 981 */     out.println("    int                        act_num,");
/*  658: 982 */     out.println("    java_cup.runtime.lr_parser parser,");
/*  659: 983 */     out.println("    java.util.Stack            stack,");
/*  660: 984 */     out.println("    int                        top)");
/*  661: 985 */     out.println("    throws java.lang.Exception");
/*  662: 986 */     out.println("  {");
/*  663: 987 */     out.println("    /* call code in generated class */");
/*  664: 988 */     out.println("    return action_obj." + pre("do_action(") + "act_num, parser, stack, top);");
/*  665:     */     
/*  666: 990 */     out.println("  }");
/*  667: 991 */     out.println("");
/*  668:     */     
/*  669:     */ 
/*  670:     */ 
/*  671: 995 */     out.println("  /** Indicates start state. */");
/*  672: 996 */     out.println("  public int start_state() {return " + start_st + ";}");
/*  673:     */     
/*  674:     */ 
/*  675: 999 */     out.println("  /** Indicates start production. */");
/*  676:1000 */     out.println("  public int start_production() {return " + start_production.index() + ";}");
/*  677:     */     
/*  678:1002 */     out.println();
/*  679:     */     
/*  680:     */ 
/*  681:1005 */     out.println("  /** <code>EOF</code> Symbol index. */");
/*  682:1006 */     out.println("  public int EOF_sym() {return " + terminal.EOF.index() + ";}");
/*  683:     */     
/*  684:1008 */     out.println();
/*  685:1009 */     out.println("  /** <code>error</code> Symbol index. */");
/*  686:1010 */     out.println("  public int error_sym() {return " + terminal.error.index() + ";}");
/*  687:     */     
/*  688:1012 */     out.println();
/*  689:1015 */     if (init_code != null)
/*  690:     */     {
/*  691:1017 */       out.println();
/*  692:1018 */       out.println("  /** User initialization code. */");
/*  693:1019 */       out.println("  public void user_init() throws java.lang.Exception");
/*  694:1020 */       out.println("    {");
/*  695:1021 */       out.println(init_code);
/*  696:1022 */       out.println("    }");
/*  697:     */     }
/*  698:1026 */     if (scan_code != null)
/*  699:     */     {
/*  700:1028 */       out.println();
/*  701:1029 */       out.println("  /** Scan to get the next Symbol. */");
/*  702:1030 */       out.println("  public java_cup.runtime.Symbol scan()");
/*  703:1031 */       out.println("    throws java.lang.Exception");
/*  704:1032 */       out.println("    {");
/*  705:1033 */       out.println(scan_code);
/*  706:1034 */       out.println("    }");
/*  707:     */     }
/*  708:1038 */     if (parser_code != null)
/*  709:     */     {
/*  710:1040 */       out.println();
/*  711:1041 */       out.println(parser_code);
/*  712:     */     }
/*  713:1046 */     if (!_xmlactions) {
/*  714:1047 */       emit_action_code(out, start_prod);
/*  715:     */     } else {
/*  716:1049 */       emit_xmlaction_code(out, start_prod);
/*  717:     */     }
/*  718:1052 */     out.println("}");
/*  719:     */     
/*  720:1054 */     parser_time = System.currentTimeMillis() - start_time;
/*  721:     */   }
/*  722:     */   
/*  723:     */   protected static void emit_xmlaction_code(PrintWriter out, production start_prod)
/*  724:     */     throws internal_error
/*  725:     */   {
/*  726:1065 */     long start_time = System.currentTimeMillis();
/*  727:     */     
/*  728:     */ 
/*  729:1068 */     out.println();
/*  730:1069 */     out.println("/** Cup generated class to encapsulate user supplied action code.*/");
/*  731:     */     
/*  732:     */ 
/*  733:1072 */     out.println("@SuppressWarnings({\"rawtypes\", \"unchecked\", \"unused\"})");
/*  734:1073 */     out.println("class " + pre("actions") + typeArgument() + " {");
/*  735:1075 */     if (action_code != null)
/*  736:     */     {
/*  737:1077 */       out.println();
/*  738:1078 */       out.println(action_code);
/*  739:     */     }
/*  740:1082 */     out.println("  private final " + parser_class_name + typeArgument() + " parser;");
/*  741:     */     
/*  742:     */ 
/*  743:1085 */     out.println();
/*  744:1086 */     out.println("  /** Constructor */");
/*  745:1087 */     out.println("  " + pre("actions") + "(" + parser_class_name + typeArgument() + " parser) {");
/*  746:1088 */     out.println("    this.parser = parser;");
/*  747:1089 */     out.println("  }");
/*  748:     */     
/*  749:1091 */     out.println();
/*  750:1092 */     for (int instancecounter = 0; instancecounter <= production.number() / 300; instancecounter++)
/*  751:     */     {
/*  752:1093 */       out.println("  /** Method " + instancecounter + " with the actual generated action code for actions " + instancecounter * 300 + " to " + (instancecounter + 1) * 300 + ". */");
/*  753:1094 */       out.println("  public final java_cup.runtime.Symbol " + pre("do_action_part") + String.format("%08d", new Object[] { new Integer(instancecounter) }) + "(");
/*  754:     */       
/*  755:1096 */       out.println("    int                        " + pre("act_num,"));
/*  756:1097 */       out.println("    java_cup.runtime.lr_parser " + pre("parser,"));
/*  757:1098 */       out.println("    java.util.Stack            " + pre("stack,"));
/*  758:1099 */       out.println("    int                        " + pre("top)"));
/*  759:1100 */       out.println("    throws java.lang.Exception");
/*  760:1101 */       out.println("    {");
/*  761:1102 */       out.println("      /* Symbol object for return from actions */");
/*  762:1103 */       out.println("      java_cup.runtime.Symbol " + pre("result") + ";");
/*  763:1104 */       out.println();
/*  764:1105 */       out.println("      /* select the action based on the action number */");
/*  765:1106 */       out.println("      switch (" + pre("act_num") + ")");
/*  766:1107 */       out.println("        {");
/*  767:     */       
/*  768:     */ 
/*  769:1110 */       int proditeration = instancecounter * 300;
/*  770:1111 */       for (production prod = production.find(proditeration); proditeration < Math.min((instancecounter + 1) * 300, production.number()); prod = production.find(proditeration))
/*  771:     */       {
/*  772:1115 */         out.println("          /*. . . . . . . . . . . . . . . . . . . .*/");
/*  773:1116 */         out.println("          case " + prod.index() + ": // " + prod.to_simple_string());
/*  774:     */         
/*  775:     */ 
/*  776:     */ 
/*  777:1120 */         out.println("            {");
/*  778:     */         
/*  779:     */ 
/*  780:1123 */         out.println("                XMLElement RESULT;");
/*  781:     */         
/*  782:     */ 
/*  783:     */ 
/*  784:1127 */         String nested = "";
/*  785:1128 */         for (int rhsi = 0; rhsi < prod.rhs_length(); rhsi++) {
/*  786:1129 */           if ((prod.rhs(rhsi) instanceof symbol_part))
/*  787:     */           {
/*  788:1130 */             String label = prod.rhs(rhsi).label();
/*  789:1131 */             symbol_part sym = (symbol_part)prod.rhs(rhsi);
/*  790:1132 */             if (label == null)
/*  791:     */             {
/*  792:1133 */               if (_genericlabels) {
/*  793:1134 */                 label = sym.the_symbol().name() + rhsi;
/*  794:     */               }
/*  795:     */             }
/*  796:1136 */             else if (sym.the_symbol().is_non_term()) {
/*  797:1137 */               nested = nested + ",(XMLElement)" + label;
/*  798:     */             } else {
/*  799:1139 */               nested = nested + ",new XMLElement.Terminal(" + label + "xleft,\"" + label + "\"," + label + "," + label + "xright)";
/*  800:     */             }
/*  801:     */           }
/*  802:     */         }
/*  803:1142 */         if ((prod.action() != null) && (prod.action().code_string() != null) && (!prod.action().equals(""))) {
/*  804:1144 */           out.println(prod.action().code_string());
/*  805:     */         }
/*  806:1147 */         int variant = 0;
/*  807:1148 */         for (int i = 0; i < proditeration; i++) {
/*  808:1149 */           if (production.find(i).lhs().equals(prod.lhs())) {
/*  809:1149 */             variant++;
/*  810:     */           }
/*  811:     */         }
/*  812:1151 */         String lhsname = prod.lhs().the_symbol().name().replace('$', '_');
/*  813:1152 */         out.println("                RESULT = new XMLElement.NonTerminal(\"" + lhsname + "\"," + variant + nested + ");");
/*  814:1159 */         if (lr_values())
/*  815:     */         {
/*  816:1162 */           String rightstring = "((java_cup.runtime.Symbol)" + pre("stack") + ".peek()" + ")";
/*  817:     */           String leftstring;
/*  818:     */           String leftstring;
/*  819:1163 */           if (prod.rhs_length() == 0)
/*  820:     */           {
/*  821:1164 */             leftstring = rightstring;
/*  822:     */           }
/*  823:     */           else
/*  824:     */           {
/*  825:1166 */             int loffset = prod.rhs_length() - 1;
/*  826:1167 */             leftstring = "((java_cup.runtime.Symbol)" + pre("stack") + (loffset == 0 ? ".peek()" : new StringBuilder().append(".elementAt(").append(pre("top")).append("-").append(loffset).append(")").toString()) + ")";
/*  827:     */           }
/*  828:1170 */           out.println("              " + pre("result") + " = parser.getSymbolFactory().newSymbol(" + "\"" + prod.lhs().the_symbol().name() + "\"," + prod.lhs().the_symbol().index() + ", " + leftstring + ", " + rightstring + ", RESULT);");
/*  829:     */         }
/*  830:     */         else
/*  831:     */         {
/*  832:1174 */           out.println("              " + pre("result") + " = parser.getSymbolFactory().newSymbol(" + "\"" + prod.lhs().the_symbol().name() + "\"," + prod.lhs().the_symbol().index() + ", RESULT);");
/*  833:     */         }
/*  834:1179 */         out.println("            }");
/*  835:1182 */         if (prod == start_prod)
/*  836:     */         {
/*  837:1184 */           out.println("          /* ACCEPT */");
/*  838:1185 */           out.println("          " + pre("parser") + ".done_parsing();");
/*  839:     */         }
/*  840:1189 */         out.println("          return " + pre("result") + ";");
/*  841:1190 */         out.println();proditeration++;
/*  842:     */       }
/*  843:1194 */       out.println("          /* . . . . . .*/");
/*  844:1195 */       out.println("          default:");
/*  845:1196 */       out.println("            throw new Exception(");
/*  846:1197 */       out.println("               \"Invalid action number \"+" + pre("act_num") + "+\"found in " + "internal parse table\");");
/*  847:     */       
/*  848:1199 */       out.println();
/*  849:1200 */       out.println("        }");
/*  850:1201 */       out.println("    } /* end of method */");
/*  851:     */     }
/*  852:1205 */     out.println();
/*  853:1206 */     out.println("  /** Method splitting the generated action code into several parts. */");
/*  854:1207 */     out.println("  public final java_cup.runtime.Symbol " + pre("do_action") + "(");
/*  855:     */     
/*  856:1209 */     out.println("    int                        " + pre("act_num,"));
/*  857:1210 */     out.println("    java_cup.runtime.lr_parser " + pre("parser,"));
/*  858:1211 */     out.println("    java.util.Stack            " + pre("stack,"));
/*  859:1212 */     out.println("    int                        " + pre("top)"));
/*  860:1213 */     out.println("    throws java.lang.Exception");
/*  861:1214 */     out.println("    {");
/*  862:1216 */     if (production.number() < 300)
/*  863:     */     {
/*  864:1217 */       out.println("              return " + pre("do_action_part") + String.format("%08d", new Object[] { new Integer(0) }) + "(");
/*  865:1218 */       out.println("                               " + pre("act_num,"));
/*  866:1219 */       out.println("                               " + pre("parser,"));
/*  867:1220 */       out.println("                               " + pre("stack,"));
/*  868:1221 */       out.println("                               " + pre("top);"));
/*  869:1222 */       out.println("    }");
/*  870:     */       
/*  871:     */ 
/*  872:1225 */       out.println("}");
/*  873:1226 */       out.println();
/*  874:     */       
/*  875:1228 */       action_code_time = System.currentTimeMillis() - start_time;
/*  876:1229 */       return;
/*  877:     */     }
/*  878:1233 */     out.println("      /* select the action handler based on the action number */");
/*  879:1234 */     out.println("      switch (" + pre("act_num") + "/" + 300 + ")");
/*  880:1235 */     out.println("        {");
/*  881:1238 */     for (int instancecounter = 0; instancecounter <= production.number() / 300; instancecounter++)
/*  882:     */     {
/*  883:1240 */       out.println("          /*. . . . . . . . " + instancecounter * 300 + " < #action < " + (instancecounter + 1) * 300 + ". . . . . . . . . . . .*/");
/*  884:1241 */       out.println("          case " + instancecounter + ": ");
/*  885:1242 */       out.println("              return " + pre("do_action_part") + String.format("%08d", new Object[] { new Integer(instancecounter) }) + "(");
/*  886:1243 */       out.println("                               " + pre("act_num,"));
/*  887:1244 */       out.println("                               " + pre("parser,"));
/*  888:1245 */       out.println("                               " + pre("stack,"));
/*  889:1246 */       out.println("                               " + pre("top);"));
/*  890:     */     }
/*  891:1249 */     out.println("          /* . . . no valid action number: . . .*/");
/*  892:1250 */     out.println("          default:");
/*  893:1251 */     out.println("            throw new Exception(\"Invalid action number found in internal parse table\");");
/*  894:1252 */     out.println();
/*  895:1253 */     out.println("        }      /* end of switch */");
/*  896:     */     
/*  897:     */ 
/*  898:1256 */     out.println("    }");
/*  899:     */     
/*  900:     */ 
/*  901:1259 */     out.println("}");
/*  902:1260 */     out.println();
/*  903:     */     
/*  904:1262 */     action_code_time = System.currentTimeMillis() - start_time;
/*  905:     */   }
/*  906:     */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.emit
 * JD-Core Version:    0.7.0.1
 */
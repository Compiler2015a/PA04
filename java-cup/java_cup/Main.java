/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.BufferedOutputStream;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileInputStream;
/*   7:    */ import java.io.FileNotFoundException;
/*   8:    */ import java.io.FileOutputStream;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.io.PrintStream;
/*  11:    */ import java.io.PrintWriter;
/*  12:    */ import java.util.Enumeration;
/*  13:    */ import java_cup.runtime.ComplexSymbolFactory;
/*  14:    */ 
/*  15:    */ public class Main
/*  16:    */ {
/*  17: 83 */   protected static boolean print_progress = false;
/*  18: 85 */   protected static boolean opt_dump_states = false;
/*  19: 87 */   protected static boolean opt_dump_tables = false;
/*  20: 89 */   protected static boolean opt_dump_grammar = false;
/*  21: 91 */   protected static boolean opt_show_timing = false;
/*  22: 93 */   protected static boolean opt_do_debug = false;
/*  23: 96 */   protected static boolean opt_compact_red = false;
/*  24: 99 */   protected static boolean include_non_terms = false;
/*  25:101 */   protected static boolean no_summary = false;
/*  26:103 */   protected static int expect_conflicts = 0;
/*  27:107 */   protected static boolean lr_values = true;
/*  28:108 */   protected static boolean locations = false;
/*  29:109 */   protected static boolean xmlactions = false;
/*  30:110 */   protected static boolean genericlabels = false;
/*  31:113 */   protected static boolean sym_interface = false;
/*  32:117 */   protected static boolean suppress_scanner = false;
/*  33:123 */   protected static long start_time = 0L;
/*  34:125 */   protected static long prelim_end = 0L;
/*  35:127 */   protected static long parse_end = 0L;
/*  36:129 */   protected static long check_end = 0L;
/*  37:131 */   protected static long dump_end = 0L;
/*  38:133 */   protected static long build_end = 0L;
/*  39:135 */   protected static long nullability_end = 0L;
/*  40:137 */   protected static long first_end = 0L;
/*  41:139 */   protected static long machine_end = 0L;
/*  42:141 */   protected static long table_end = 0L;
/*  43:143 */   protected static long reduce_check_end = 0L;
/*  44:145 */   protected static long emit_end = 0L;
/*  45:147 */   protected static long final_time = 0L;
/*  46:    */   protected static BufferedInputStream input_file;
/*  47:    */   protected static PrintWriter parser_class_file;
/*  48:    */   protected static PrintWriter symbol_class_file;
/*  49:    */   
/*  50:    */   public static void main(String[] argv)
/*  51:    */     throws internal_error, IOException, Exception
/*  52:    */   {
/*  53:161 */     boolean did_output = false;
/*  54:    */     
/*  55:163 */     start_time = System.currentTimeMillis();
/*  56:    */     
/*  57:    */ 
/*  58:166 */     terminal.clear();
/*  59:167 */     production.clear();
/*  60:168 */     action_production.clear();
/*  61:169 */     emit.clear();
/*  62:170 */     non_terminal.clear();
/*  63:171 */     parse_reduce_row.clear();
/*  64:172 */     parse_action_row.clear();
/*  65:173 */     lalr_state.clear();
/*  66:    */     
/*  67:    */ 
/*  68:176 */     parse_args(argv);
/*  69:    */     
/*  70:    */ 
/*  71:    */ 
/*  72:180 */     emit.set_lr_values(lr_values);
/*  73:181 */     emit.set_locations(locations);
/*  74:182 */     emit.set_xmlactions(xmlactions);
/*  75:183 */     emit.set_genericlabels(genericlabels);
/*  76:185 */     if (print_progress) {
/*  77:185 */       System.err.println("Opening files...");
/*  78:    */     }
/*  79:187 */     input_file = new BufferedInputStream(System.in);
/*  80:    */     
/*  81:189 */     prelim_end = System.currentTimeMillis();
/*  82:192 */     if (print_progress) {
/*  83:193 */       System.err.println("Parsing specification from standard input...");
/*  84:    */     }
/*  85:194 */     parse_grammar_spec();
/*  86:    */     
/*  87:196 */     parse_end = System.currentTimeMillis();
/*  88:199 */     if (ErrorManager.getManager().getErrorCount() == 0)
/*  89:    */     {
/*  90:202 */       if (print_progress) {
/*  91:202 */         System.err.println("Checking specification...");
/*  92:    */       }
/*  93:203 */       check_unused();
/*  94:    */       
/*  95:205 */       check_end = System.currentTimeMillis();
/*  96:208 */       if (print_progress) {
/*  97:208 */         System.err.println("Building parse tables...");
/*  98:    */       }
/*  99:209 */       build_parser();
/* 100:    */       
/* 101:211 */       build_end = System.currentTimeMillis();
/* 102:214 */       if (ErrorManager.getManager().getErrorCount() != 0)
/* 103:    */       {
/* 104:216 */         opt_dump_tables = false;
/* 105:    */       }
/* 106:    */       else
/* 107:    */       {
/* 108:218 */         if (print_progress) {
/* 109:218 */           System.err.println("Writing parser...");
/* 110:    */         }
/* 111:219 */         open_files();
/* 112:220 */         emit_parser();
/* 113:221 */         did_output = true;
/* 114:    */       }
/* 115:    */     }
/* 116:225 */     emit_end = System.currentTimeMillis();
/* 117:228 */     if (opt_dump_grammar) {
/* 118:228 */       dump_grammar();
/* 119:    */     }
/* 120:229 */     if (opt_dump_states) {
/* 121:229 */       dump_machine();
/* 122:    */     }
/* 123:230 */     if (opt_dump_tables) {
/* 124:230 */       dump_tables();
/* 125:    */     }
/* 126:232 */     dump_end = System.currentTimeMillis();
/* 127:235 */     if (print_progress) {
/* 128:235 */       System.err.println("Closing files...");
/* 129:    */     }
/* 130:236 */     close_files();
/* 131:239 */     if (!no_summary) {
/* 132:239 */       emit_summary(did_output);
/* 133:    */     }
/* 134:243 */     if (ErrorManager.getManager().getErrorCount() != 0) {
/* 135:244 */       System.exit(100);
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected static void usage(String message)
/* 140:    */   {
/* 141:255 */     System.err.println();
/* 142:256 */     System.err.println(message);
/* 143:257 */     System.err.println();
/* 144:258 */     System.err.println("CUP v0.11b 20140808 (SVN rev 54)\nUsage: java_cup [options] [filename]\n  and expects a specification file on standard input if no filename is given.\n  Legal options include:\n    -package name  specify package generated classes go in [default none]\n    -destdir name  specify the destination directory, to store the generated files in\n    -parser name   specify parser class name [default \"parser\"]\n    -typearg args  specify type arguments for parser class\n    -symbols name  specify name for symbol constant class [default \"sym\"]\n    -interface     put symbols in an interface, rather than a class\n    -nonterms      put non terminals in symbol constant class\n    -expect #      number of conflicts expected/allowed [default 0]\n    -compact_red   compact tables by defaulting to most frequent reduce\n    -nowarn        don't warn about useless productions, etc.\n    -nosummary     don't print the usual summary of parse states, etc.\n    -nopositions   don't propagate the left and right token position values\n    -locations     generate handles xleft/xright for symbol positions in actions\n    -xmlactions    make the generated parser yield its parse tree as XML\n    -genericlabels automatically generate labels to all symbols in XML mode\n    -noscanner     don't refer to java_cup.runtime.Scanner\n    -progress      print messages to indicate progress of the system\n    -time          print time usage summary\n    -dump_grammar  produce a human readable dump of the symbols and grammar\n    -dump_states   produce a dump of parse state machine\n    -dump_tables   produce a dump of the parse tables\n    -dump          produce a dump of all of the above\n    -version       print the version information for CUP and exit\n");
/* 145:    */     
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:287 */     System.exit(1);
/* 174:    */   }
/* 175:    */   
/* 176:    */   protected static void parse_args(String[] argv)
/* 177:    */   {
/* 178:298 */     int len = argv.length;
/* 179:302 */     for (int i = 0; i < len; i++) {
/* 180:305 */       if (argv[i].equals("-package"))
/* 181:    */       {
/* 182:308 */         i++;
/* 183:308 */         if ((i >= len) || (argv[i].startsWith("-")) || (argv[i].endsWith(".cup"))) {
/* 184:310 */           usage("-package must have a name argument");
/* 185:    */         }
/* 186:313 */         emit.package_name = argv[i];
/* 187:    */       }
/* 188:315 */       else if (argv[i].equals("-destdir"))
/* 189:    */       {
/* 190:318 */         i++;
/* 191:318 */         if ((i >= len) || (argv[i].startsWith("-")) || (argv[i].endsWith(".cup"))) {
/* 192:320 */           usage("-destdir must have a name argument");
/* 193:    */         }
/* 194:322 */         dest_dir = new File(argv[i]);
/* 195:    */       }
/* 196:324 */       else if (argv[i].equals("-parser"))
/* 197:    */       {
/* 198:327 */         i++;
/* 199:327 */         if ((i >= len) || (argv[i].startsWith("-")) || (argv[i].endsWith(".cup"))) {
/* 200:329 */           usage("-parser must have a name argument");
/* 201:    */         }
/* 202:332 */         emit.parser_class_name = argv[i];
/* 203:    */       }
/* 204:334 */       else if (argv[i].equals("-symbols"))
/* 205:    */       {
/* 206:337 */         i++;
/* 207:337 */         if ((i >= len) || (argv[i].startsWith("-")) || (argv[i].endsWith(".cup"))) {
/* 208:339 */           usage("-symbols must have a name argument");
/* 209:    */         }
/* 210:342 */         emit.symbol_const_class_name = argv[i];
/* 211:    */       }
/* 212:344 */       else if (argv[i].equals("-nonterms"))
/* 213:    */       {
/* 214:346 */         include_non_terms = true;
/* 215:    */       }
/* 216:348 */       else if (argv[i].equals("-expect"))
/* 217:    */       {
/* 218:351 */         i++;
/* 219:351 */         if ((i >= len) || (argv[i].startsWith("-")) || (argv[i].endsWith(".cup"))) {
/* 220:353 */           usage("-expect must have a name argument");
/* 221:    */         }
/* 222:    */         try
/* 223:    */         {
/* 224:357 */           expect_conflicts = Integer.parseInt(argv[i]);
/* 225:    */         }
/* 226:    */         catch (NumberFormatException e)
/* 227:    */         {
/* 228:359 */           usage("-expect must be followed by a decimal integer");
/* 229:    */         }
/* 230:    */       }
/* 231:362 */       else if (argv[i].equals("-compact_red"))
/* 232:    */       {
/* 233:362 */         opt_compact_red = true;
/* 234:    */       }
/* 235:363 */       else if (argv[i].equals("-nosummary"))
/* 236:    */       {
/* 237:363 */         no_summary = true;
/* 238:    */       }
/* 239:364 */       else if (argv[i].equals("-nowarn"))
/* 240:    */       {
/* 241:364 */         emit.nowarn = true;
/* 242:    */       }
/* 243:365 */       else if (argv[i].equals("-dump_states"))
/* 244:    */       {
/* 245:365 */         opt_dump_states = true;
/* 246:    */       }
/* 247:366 */       else if (argv[i].equals("-dump_tables"))
/* 248:    */       {
/* 249:366 */         opt_dump_tables = true;
/* 250:    */       }
/* 251:367 */       else if (argv[i].equals("-progress"))
/* 252:    */       {
/* 253:367 */         print_progress = true;
/* 254:    */       }
/* 255:368 */       else if (argv[i].equals("-dump_grammar"))
/* 256:    */       {
/* 257:368 */         opt_dump_grammar = true;
/* 258:    */       }
/* 259:369 */       else if (argv[i].equals("-dump"))
/* 260:    */       {
/* 261:370 */         opt_dump_states = Main.opt_dump_tables = Main.opt_dump_grammar = 1;
/* 262:    */       }
/* 263:371 */       else if (argv[i].equals("-time"))
/* 264:    */       {
/* 265:371 */         opt_show_timing = true;
/* 266:    */       }
/* 267:372 */       else if (argv[i].equals("-debug"))
/* 268:    */       {
/* 269:372 */         opt_do_debug = true;
/* 270:    */       }
/* 271:374 */       else if (argv[i].equals("-nopositions"))
/* 272:    */       {
/* 273:374 */         lr_values = false;
/* 274:    */       }
/* 275:375 */       else if (argv[i].equals("-locations"))
/* 276:    */       {
/* 277:375 */         locations = true;
/* 278:    */       }
/* 279:376 */       else if (argv[i].equals("-xmlactions"))
/* 280:    */       {
/* 281:376 */         xmlactions = true;
/* 282:    */       }
/* 283:377 */       else if (argv[i].equals("-genericlabels"))
/* 284:    */       {
/* 285:377 */         genericlabels = true;
/* 286:    */       }
/* 287:379 */       else if (argv[i].equals("-interface"))
/* 288:    */       {
/* 289:379 */         sym_interface = true;
/* 290:    */       }
/* 291:381 */       else if (argv[i].equals("-noscanner"))
/* 292:    */       {
/* 293:381 */         suppress_scanner = true;
/* 294:    */       }
/* 295:383 */       else if (argv[i].equals("-version"))
/* 296:    */       {
/* 297:384 */         System.out.println("CUP v0.11b 20140808 (SVN rev 54)");
/* 298:385 */         System.exit(1);
/* 299:    */       }
/* 300:388 */       else if (argv[i].equals("-typearg"))
/* 301:    */       {
/* 302:389 */         i++;
/* 303:389 */         if ((i >= len) || (argv[i].startsWith("-")) || (argv[i].endsWith(".cup"))) {
/* 304:391 */           usage("-symbols must have a name argument");
/* 305:    */         }
/* 306:394 */         emit.class_type_argument = argv[i];
/* 307:    */       }
/* 308:398 */       else if ((!argv[i].startsWith("-")) && (i == len - 1))
/* 309:    */       {
/* 310:    */         try
/* 311:    */         {
/* 312:401 */           System.setIn(new FileInputStream(argv[i]));
/* 313:    */         }
/* 314:    */         catch (FileNotFoundException e)
/* 315:    */         {
/* 316:403 */           usage("Unable to open \"" + argv[i] + "\" for input");
/* 317:    */         }
/* 318:    */       }
/* 319:    */       else
/* 320:    */       {
/* 321:408 */         usage("Unrecognized option \"" + argv[i] + "\"");
/* 322:    */       }
/* 323:    */     }
/* 324:    */   }
/* 325:    */   
/* 326:429 */   protected static File dest_dir = null;
/* 327:    */   protected static lalr_state start_state;
/* 328:    */   protected static parse_action_table action_table;
/* 329:    */   protected static parse_reduce_table reduce_table;
/* 330:    */   
/* 331:    */   protected static void open_files()
/* 332:    */   {
/* 333:441 */     String out_name = emit.parser_class_name + ".java";
/* 334:442 */     File fil = new File(dest_dir, out_name);
/* 335:    */     try
/* 336:    */     {
/* 337:444 */       parser_class_file = new PrintWriter(new BufferedOutputStream(new FileOutputStream(fil), 4096));
/* 338:    */     }
/* 339:    */     catch (Exception e)
/* 340:    */     {
/* 341:447 */       System.err.println("Can't open \"" + out_name + "\" for output");
/* 342:448 */       System.exit(3);
/* 343:    */     }
/* 344:452 */     out_name = emit.symbol_const_class_name + ".java";
/* 345:453 */     fil = new File(dest_dir, out_name);
/* 346:    */     try
/* 347:    */     {
/* 348:455 */       symbol_class_file = new PrintWriter(new BufferedOutputStream(new FileOutputStream(fil), 4096));
/* 349:    */     }
/* 350:    */     catch (Exception e)
/* 351:    */     {
/* 352:458 */       System.err.println("Can't open \"" + out_name + "\" for output");
/* 353:459 */       System.exit(4);
/* 354:    */     }
/* 355:    */   }
/* 356:    */   
/* 357:    */   protected static void close_files()
/* 358:    */     throws IOException
/* 359:    */   {
/* 360:468 */     if (input_file != null) {
/* 361:468 */       input_file.close();
/* 362:    */     }
/* 363:469 */     if (parser_class_file != null) {
/* 364:469 */       parser_class_file.close();
/* 365:    */     }
/* 366:470 */     if (symbol_class_file != null) {
/* 367:470 */       symbol_class_file.close();
/* 368:    */     }
/* 369:    */   }
/* 370:    */   
/* 371:    */   protected static void parse_grammar_spec()
/* 372:    */     throws Exception
/* 373:    */   {
/* 374:486 */     ComplexSymbolFactory csf = new ComplexSymbolFactory();
/* 375:487 */     parser parser_obj = new parser(new Lexer(csf), csf);
/* 376:    */     try
/* 377:    */     {
/* 378:489 */       if (opt_do_debug) {
/* 379:490 */         parser_obj.debug_parse();
/* 380:    */       } else {
/* 381:492 */         parser_obj.parse();
/* 382:    */       }
/* 383:    */     }
/* 384:    */     catch (Exception e)
/* 385:    */     {
/* 386:497 */       ErrorManager.getManager().emit_error("Internal error: Unexpected exception");
/* 387:498 */       throw e;
/* 388:    */     }
/* 389:    */   }
/* 390:    */   
/* 391:    */   protected static void check_unused()
/* 392:    */   {
/* 393:513 */     for (Enumeration t = terminal.all(); t.hasMoreElements();)
/* 394:    */     {
/* 395:515 */       terminal term = (terminal)t.nextElement();
/* 396:518 */       if ((term != terminal.EOF) && 
/* 397:    */       
/* 398:    */ 
/* 399:521 */         (term != terminal.error) && 
/* 400:    */         
/* 401:    */ 
/* 402:524 */         (term.use_count() == 0))
/* 403:    */       {
/* 404:527 */         emit.unused_term += 1;
/* 405:528 */         if (!emit.nowarn) {
/* 406:530 */           ErrorManager.getManager().emit_warning("Terminal \"" + term.name() + "\" was declared but never used");
/* 407:    */         }
/* 408:    */       }
/* 409:    */     }
/* 410:536 */     for (Enumeration n = non_terminal.all(); n.hasMoreElements();)
/* 411:    */     {
/* 412:538 */       non_terminal nt = (non_terminal)n.nextElement();
/* 413:541 */       if (nt.use_count() == 0)
/* 414:    */       {
/* 415:544 */         emit.unused_term += 1;
/* 416:545 */         if (!emit.nowarn) {
/* 417:547 */           ErrorManager.getManager().emit_warning("Non terminal \"" + nt.name() + "\" was declared but never used");
/* 418:    */         }
/* 419:    */       }
/* 420:    */     }
/* 421:    */   }
/* 422:    */   
/* 423:    */   protected static void build_parser()
/* 424:    */     throws internal_error
/* 425:    */   {
/* 426:581 */     if ((opt_do_debug) || (print_progress)) {
/* 427:582 */       System.err.println("  Computing non-terminal nullability...");
/* 428:    */     }
/* 429:583 */     non_terminal.compute_nullability();
/* 430:    */     
/* 431:585 */     nullability_end = System.currentTimeMillis();
/* 432:588 */     if ((opt_do_debug) || (print_progress)) {
/* 433:589 */       System.err.println("  Computing first sets...");
/* 434:    */     }
/* 435:590 */     non_terminal.compute_first_sets();
/* 436:    */     
/* 437:592 */     first_end = System.currentTimeMillis();
/* 438:595 */     if ((opt_do_debug) || (print_progress)) {
/* 439:596 */       System.err.println("  Building state machine...");
/* 440:    */     }
/* 441:597 */     start_state = lalr_state.build_machine(emit.start_production);
/* 442:    */     
/* 443:599 */     machine_end = System.currentTimeMillis();
/* 444:602 */     if ((opt_do_debug) || (print_progress)) {
/* 445:603 */       System.err.println("  Filling in tables...");
/* 446:    */     }
/* 447:604 */     action_table = new parse_action_table();
/* 448:605 */     reduce_table = new parse_reduce_table();
/* 449:606 */     for (Enumeration st = lalr_state.all(); st.hasMoreElements();)
/* 450:    */     {
/* 451:608 */       lalr_state lst = (lalr_state)st.nextElement();
/* 452:609 */       lst.build_table_entries(action_table, reduce_table);
/* 453:    */     }
/* 454:613 */     table_end = System.currentTimeMillis();
/* 455:616 */     if ((opt_do_debug) || (print_progress)) {
/* 456:617 */       System.err.println("  Checking for non-reduced productions...");
/* 457:    */     }
/* 458:618 */     action_table.check_reductions();
/* 459:    */     
/* 460:620 */     reduce_check_end = System.currentTimeMillis();
/* 461:623 */     if (emit.num_conflicts > expect_conflicts) {
/* 462:625 */       ErrorManager.getManager().emit_error("*** More conflicts encountered than expected -- parser generation aborted");
/* 463:    */     }
/* 464:    */   }
/* 465:    */   
/* 466:    */   protected static void emit_parser()
/* 467:    */     throws internal_error
/* 468:    */   {
/* 469:637 */     emit.symbols(symbol_class_file, include_non_terms, sym_interface);
/* 470:638 */     emit.parser(parser_class_file, action_table, reduce_table, start_state.index(), emit.start_production, opt_compact_red, suppress_scanner);
/* 471:    */   }
/* 472:    */   
/* 473:    */   protected static String plural(int val)
/* 474:    */   {
/* 475:650 */     if (val == 1) {
/* 476:651 */       return "";
/* 477:    */     }
/* 478:653 */     return "s";
/* 479:    */   }
/* 480:    */   
/* 481:    */   protected static void emit_summary(boolean output_produced)
/* 482:    */   {
/* 483:666 */     final_time = System.currentTimeMillis();
/* 484:668 */     if (no_summary) {
/* 485:668 */       return;
/* 486:    */     }
/* 487:670 */     System.err.println("------- CUP v0.11b 20140808 (SVN rev 54) Parser Generation Summary -------");
/* 488:    */     
/* 489:    */ 
/* 490:    */ 
/* 491:674 */     System.err.println("  " + ErrorManager.getManager().getErrorCount() + " error" + plural(ErrorManager.getManager().getErrorCount()) + " and " + ErrorManager.getManager().getWarningCount() + " warning" + plural(ErrorManager.getManager().getWarningCount()));
/* 492:    */     
/* 493:    */ 
/* 494:    */ 
/* 495:    */ 
/* 496:679 */     System.err.print("  " + terminal.number() + " terminal" + plural(terminal.number()) + ", ");
/* 497:    */     
/* 498:681 */     System.err.print(non_terminal.number() + " non-terminal" + plural(non_terminal.number()) + ", and ");
/* 499:    */     
/* 500:683 */     System.err.println(production.number() + " production" + plural(production.number()) + " declared, ");
/* 501:    */     
/* 502:685 */     System.err.println("  producing " + lalr_state.number() + " unique parse states.");
/* 503:    */     
/* 504:    */ 
/* 505:    */ 
/* 506:689 */     System.err.println("  " + emit.unused_term + " terminal" + plural(emit.unused_term) + " declared but not used.");
/* 507:    */     
/* 508:691 */     System.err.println("  " + emit.unused_non_term + " non-terminal" + plural(emit.unused_term) + " declared but not used.");
/* 509:    */     
/* 510:    */ 
/* 511:    */ 
/* 512:695 */     System.err.println("  " + emit.not_reduced + " production" + plural(emit.not_reduced) + " never reduced.");
/* 513:    */     
/* 514:    */ 
/* 515:    */ 
/* 516:699 */     System.err.println("  " + emit.num_conflicts + " conflict" + plural(emit.num_conflicts) + " detected" + " (" + expect_conflicts + " expected).");
/* 517:704 */     if (output_produced) {
/* 518:705 */       System.err.println("  Code written to \"" + emit.parser_class_name + ".java\", and \"" + emit.symbol_const_class_name + ".java\".");
/* 519:    */     } else {
/* 520:708 */       System.err.println("  No code produced.");
/* 521:    */     }
/* 522:710 */     if (opt_show_timing) {
/* 523:710 */       show_times();
/* 524:    */     }
/* 525:712 */     System.err.println("---------------------------------------------------- (CUP v0.11b 20140808 (SVN rev 54))");
/* 526:    */   }
/* 527:    */   
/* 528:    */   protected static void show_times()
/* 529:    */   {
/* 530:722 */     long total_time = final_time - start_time;
/* 531:    */     
/* 532:724 */     System.err.println(". . . . . . . . . . . . . . . . . . . . . . . . . ");
/* 533:725 */     System.err.println("  Timing Summary");
/* 534:726 */     System.err.println("    Total time       " + timestr(final_time - start_time, total_time));
/* 535:    */     
/* 536:728 */     System.err.println("      Startup        " + timestr(prelim_end - start_time, total_time));
/* 537:    */     
/* 538:730 */     System.err.println("      Parse          " + timestr(parse_end - prelim_end, total_time));
/* 539:732 */     if (check_end != 0L) {
/* 540:733 */       System.err.println("      Checking       " + timestr(check_end - parse_end, total_time));
/* 541:    */     }
/* 542:735 */     if ((check_end != 0L) && (build_end != 0L)) {
/* 543:736 */       System.err.println("      Parser Build   " + timestr(build_end - check_end, total_time));
/* 544:    */     }
/* 545:738 */     if ((nullability_end != 0L) && (check_end != 0L)) {
/* 546:739 */       System.err.println("        Nullability  " + timestr(nullability_end - check_end, total_time));
/* 547:    */     }
/* 548:741 */     if ((first_end != 0L) && (nullability_end != 0L)) {
/* 549:742 */       System.err.println("        First sets   " + timestr(first_end - nullability_end, total_time));
/* 550:    */     }
/* 551:744 */     if ((machine_end != 0L) && (first_end != 0L)) {
/* 552:745 */       System.err.println("        State build  " + timestr(machine_end - first_end, total_time));
/* 553:    */     }
/* 554:747 */     if ((table_end != 0L) && (machine_end != 0L)) {
/* 555:748 */       System.err.println("        Table build  " + timestr(table_end - machine_end, total_time));
/* 556:    */     }
/* 557:750 */     if ((reduce_check_end != 0L) && (table_end != 0L)) {
/* 558:751 */       System.err.println("        Checking     " + timestr(reduce_check_end - table_end, total_time));
/* 559:    */     }
/* 560:753 */     if ((emit_end != 0L) && (build_end != 0L)) {
/* 561:754 */       System.err.println("      Code Output    " + timestr(emit_end - build_end, total_time));
/* 562:    */     }
/* 563:756 */     if (emit.symbols_time != 0L) {
/* 564:757 */       System.err.println("        Symbols      " + timestr(emit.symbols_time, total_time));
/* 565:    */     }
/* 566:759 */     if (emit.parser_time != 0L) {
/* 567:760 */       System.err.println("        Parser class " + timestr(emit.parser_time, total_time));
/* 568:    */     }
/* 569:762 */     if (emit.action_code_time != 0L) {
/* 570:763 */       System.err.println("          Actions    " + timestr(emit.action_code_time, total_time));
/* 571:    */     }
/* 572:765 */     if (emit.production_table_time != 0L) {
/* 573:766 */       System.err.println("          Prod table " + timestr(emit.production_table_time, total_time));
/* 574:    */     }
/* 575:768 */     if (emit.action_table_time != 0L) {
/* 576:769 */       System.err.println("          Action tab " + timestr(emit.action_table_time, total_time));
/* 577:    */     }
/* 578:771 */     if (emit.goto_table_time != 0L) {
/* 579:772 */       System.err.println("          Reduce tab " + timestr(emit.goto_table_time, total_time));
/* 580:    */     }
/* 581:775 */     System.err.println("      Dump Output    " + timestr(dump_end - emit_end, total_time));
/* 582:    */   }
/* 583:    */   
/* 584:    */   protected static String timestr(long time_val, long total_time)
/* 585:    */   {
/* 586:791 */     long ms = 0L;
/* 587:792 */     long sec = 0L;
/* 588:    */     
/* 589:    */ 
/* 590:    */ 
/* 591:    */ 
/* 592:797 */     boolean neg = time_val < 0L;
/* 593:798 */     if (neg) {
/* 594:798 */       time_val = -time_val;
/* 595:    */     }
/* 596:801 */     ms = time_val % 1000L;
/* 597:802 */     sec = time_val / 1000L;
/* 598:    */     String pad;
/* 599:    */     String pad;
/* 600:805 */     if (sec < 10L)
/* 601:    */     {
/* 602:806 */       pad = "   ";
/* 603:    */     }
/* 604:    */     else
/* 605:    */     {
/* 606:    */       String pad;
/* 607:807 */       if (sec < 100L)
/* 608:    */       {
/* 609:808 */         pad = "  ";
/* 610:    */       }
/* 611:    */       else
/* 612:    */       {
/* 613:    */         String pad;
/* 614:809 */         if (sec < 1000L) {
/* 615:810 */           pad = " ";
/* 616:    */         } else {
/* 617:812 */           pad = "";
/* 618:    */         }
/* 619:    */       }
/* 620:    */     }
/* 621:815 */     long percent10 = time_val * 1000L / total_time;
/* 622:    */     
/* 623:    */ 
/* 624:818 */     return (neg ? "-" : "") + pad + sec + "." + ms % 1000L / 100L + ms % 100L / 10L + ms % 10L + "sec" + " (" + percent10 / 10L + "." + percent10 % 10L + "%)";
/* 625:    */   }
/* 626:    */   
/* 627:    */   public static void dump_grammar()
/* 628:    */     throws internal_error
/* 629:    */   {
/* 630:828 */     System.err.println("===== Terminals =====");
/* 631:829 */     int tidx = 0;
/* 632:829 */     for (int cnt = 0; tidx < terminal.number(); cnt++)
/* 633:    */     {
/* 634:831 */       System.err.print("[" + tidx + "]" + terminal.find(tidx).name() + " ");
/* 635:832 */       if ((cnt + 1) % 5 == 0) {
/* 636:832 */         System.err.println();
/* 637:    */       }
/* 638:829 */       tidx++;
/* 639:    */     }
/* 640:834 */     System.err.println();
/* 641:835 */     System.err.println();
/* 642:    */     
/* 643:837 */     System.err.println("===== Non terminals =====");
/* 644:838 */     int nidx = 0;
/* 645:838 */     for (int cnt = 0; nidx < non_terminal.number(); cnt++)
/* 646:    */     {
/* 647:840 */       System.err.print("[" + nidx + "]" + non_terminal.find(nidx).name() + " ");
/* 648:841 */       if ((cnt + 1) % 5 == 0) {
/* 649:841 */         System.err.println();
/* 650:    */       }
/* 651:838 */       nidx++;
/* 652:    */     }
/* 653:843 */     System.err.println();
/* 654:844 */     System.err.println();
/* 655:    */     
/* 656:    */ 
/* 657:847 */     System.err.println("===== Productions =====");
/* 658:848 */     for (int pidx = 0; pidx < production.number(); pidx++)
/* 659:    */     {
/* 660:850 */       production prod = production.find(pidx);
/* 661:851 */       System.err.print("[" + pidx + "] " + prod.lhs().the_symbol().name() + " ::= ");
/* 662:852 */       for (int i = 0; i < prod.rhs_length(); i++) {
/* 663:853 */         if (prod.rhs(i).is_action()) {
/* 664:854 */           System.err.print("{action} ");
/* 665:    */         } else {
/* 666:856 */           System.err.print(((symbol_part)prod.rhs(i)).the_symbol().name() + " ");
/* 667:    */         }
/* 668:    */       }
/* 669:858 */       System.err.println();
/* 670:    */     }
/* 671:860 */     System.err.println();
/* 672:    */   }
/* 673:    */   
/* 674:    */   public static void dump_machine()
/* 675:    */   {
/* 676:870 */     lalr_state[] ordered = new lalr_state[lalr_state.number()];
/* 677:873 */     for (Enumeration s = lalr_state.all(); s.hasMoreElements();)
/* 678:    */     {
/* 679:875 */       lalr_state st = (lalr_state)s.nextElement();
/* 680:876 */       ordered[st.index()] = st;
/* 681:    */     }
/* 682:879 */     System.err.println("===== Viable Prefix Recognizer =====");
/* 683:880 */     for (int i = 0; i < lalr_state.number(); i++)
/* 684:    */     {
/* 685:882 */       if (ordered[i] == start_state) {
/* 686:882 */         System.err.print("START ");
/* 687:    */       }
/* 688:883 */       System.err.println(ordered[i]);
/* 689:884 */       System.err.println("-------------------");
/* 690:    */     }
/* 691:    */   }
/* 692:    */   
/* 693:    */   public static void dump_tables()
/* 694:    */   {
/* 695:893 */     System.err.println(action_table);
/* 696:894 */     System.err.println(reduce_table);
/* 697:    */   }
/* 698:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.Main
 * JD-Core Version:    0.7.0.1
 */
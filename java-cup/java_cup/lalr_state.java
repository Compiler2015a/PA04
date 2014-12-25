/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import java.util.Hashtable;
/*   6:    */ import java.util.Stack;
/*   7:    */ 
/*   8:    */ public class lalr_state
/*   9:    */ {
/*  10:    */   public lalr_state(lalr_item_set itms)
/*  11:    */     throws internal_error
/*  12:    */   {
/*  13: 63 */     if (itms == null) {
/*  14: 64 */       throw new internal_error("Attempt to construct an LALR state from a null item set");
/*  15:    */     }
/*  16: 67 */     if (find_state(itms) != null) {
/*  17: 68 */       throw new internal_error("Attempt to construct a duplicate LALR state");
/*  18:    */     }
/*  19: 72 */     this._index = (next_index++);
/*  20:    */     
/*  21:    */ 
/*  22: 75 */     this._items = itms;
/*  23:    */     
/*  24:    */ 
/*  25: 78 */     _all.put(this._items, this);
/*  26:    */   }
/*  27:    */   
/*  28: 86 */   protected static Hashtable _all = new Hashtable();
/*  29:    */   
/*  30:    */   public static Enumeration all()
/*  31:    */   {
/*  32: 89 */     return _all.elements();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static void clear()
/*  36:    */   {
/*  37: 93 */     _all.clear();
/*  38: 94 */     _all_kernels.clear();
/*  39: 95 */     next_index = 0;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static int number()
/*  43:    */   {
/*  44:101 */     return _all.size();
/*  45:    */   }
/*  46:    */   
/*  47:109 */   protected static Hashtable _all_kernels = new Hashtable();
/*  48:    */   
/*  49:    */   public static lalr_state find_state(lalr_item_set itms)
/*  50:    */   {
/*  51:122 */     if (itms == null) {
/*  52:123 */       return null;
/*  53:    */     }
/*  54:125 */     return (lalr_state)_all.get(itms);
/*  55:    */   }
/*  56:    */   
/*  57:131 */   protected static int next_index = 0;
/*  58:    */   protected lalr_item_set _items;
/*  59:    */   
/*  60:    */   public lalr_item_set items()
/*  61:    */   {
/*  62:141 */     return this._items;
/*  63:    */   }
/*  64:    */   
/*  65:146 */   protected lalr_transition _transitions = null;
/*  66:    */   protected int _index;
/*  67:    */   
/*  68:    */   public lalr_transition transitions()
/*  69:    */   {
/*  70:149 */     return this._transitions;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int index()
/*  74:    */   {
/*  75:157 */     return this._index;
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected static void dump_state(lalr_state st)
/*  79:    */     throws internal_error
/*  80:    */   {
/*  81:172 */     if (st == null)
/*  82:    */     {
/*  83:174 */       System.out.println("NULL lalr_state");
/*  84:175 */       return;
/*  85:    */     }
/*  86:178 */     System.out.println("lalr_state [" + st.index() + "] {");
/*  87:179 */     lalr_item_set itms = st.items();
/*  88:180 */     for (Enumeration e = itms.all(); e.hasMoreElements();)
/*  89:    */     {
/*  90:182 */       lalr_item itm = (lalr_item)e.nextElement();
/*  91:183 */       System.out.print("  [");
/*  92:184 */       System.out.print(itm.the_production().lhs().the_symbol().name());
/*  93:185 */       System.out.print(" ::= ");
/*  94:186 */       for (int i = 0; i < itm.the_production().rhs_length(); i++)
/*  95:    */       {
/*  96:188 */         if (i == itm.dot_pos()) {
/*  97:188 */           System.out.print("(*) ");
/*  98:    */         }
/*  99:189 */         production_part part = itm.the_production().rhs(i);
/* 100:190 */         if (part.is_action()) {
/* 101:191 */           System.out.print("{action} ");
/* 102:    */         } else {
/* 103:193 */           System.out.print(((symbol_part)part).the_symbol().name() + " ");
/* 104:    */         }
/* 105:    */       }
/* 106:195 */       if (itm.dot_at_end()) {
/* 107:195 */         System.out.print("(*) ");
/* 108:    */       }
/* 109:196 */       System.out.println("]");
/* 110:    */     }
/* 111:198 */     System.out.println("}");
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected static void propagate_all_lookaheads()
/* 115:    */     throws internal_error
/* 116:    */   {
/* 117:213 */     for (Enumeration st = all(); st.hasMoreElements();) {
/* 118:216 */       ((lalr_state)st.nextElement()).propagate_lookaheads();
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void add_transition(symbol on_sym, lalr_state to_st)
/* 123:    */     throws internal_error
/* 124:    */   {
/* 125:234 */     lalr_transition trans = new lalr_transition(on_sym, to_st, this._transitions);
/* 126:235 */     this._transitions = trans;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static lalr_state build_machine(production start_prod)
/* 130:    */     throws internal_error
/* 131:    */   {
/* 132:286 */     Stack work_stack = new Stack();
/* 133:294 */     if (start_prod == null) {
/* 134:295 */       throw new internal_error("Attempt to build viable prefix recognizer using a null production");
/* 135:    */     }
/* 136:299 */     lalr_item_set start_items = new lalr_item_set();
/* 137:    */     
/* 138:301 */     lalr_item itm = new lalr_item(start_prod);
/* 139:302 */     itm.lookahead().add(terminal.EOF);
/* 140:    */     
/* 141:304 */     start_items.add(itm);
/* 142:    */     
/* 143:    */ 
/* 144:307 */     lalr_item_set kernel = new lalr_item_set(start_items);
/* 145:    */     
/* 146:    */ 
/* 147:310 */     start_items.compute_closure();
/* 148:    */     
/* 149:    */ 
/* 150:313 */     lalr_state start_state = new lalr_state(start_items);
/* 151:314 */     work_stack.push(start_state);
/* 152:    */     
/* 153:    */ 
/* 154:317 */     _all_kernels.put(kernel, start_state);
/* 155:    */     lalr_state st;
/* 156:    */     Enumeration i;
/* 157:    */     Enumeration s;
/* 158:320 */     while (!work_stack.empty())
/* 159:    */     {
/* 160:323 */       st = (lalr_state)work_stack.pop();
/* 161:    */       
/* 162:    */ 
/* 163:326 */       symbol_set outgoing = new symbol_set();
/* 164:327 */       for (i = st.items().all(); i.hasMoreElements();)
/* 165:    */       {
/* 166:329 */         itm = (lalr_item)i.nextElement();
/* 167:    */         
/* 168:    */ 
/* 169:332 */         symbol sym = itm.symbol_after_dot();
/* 170:333 */         if (sym != null) {
/* 171:333 */           outgoing.add(sym);
/* 172:    */         }
/* 173:    */       }
/* 174:337 */       for (s = outgoing.all(); s.hasMoreElements();)
/* 175:    */       {
/* 176:339 */         symbol sym = (symbol)s.nextElement();
/* 177:    */         
/* 178:    */ 
/* 179:342 */         lalr_item_set linked_items = new lalr_item_set();
/* 180:    */         
/* 181:    */ 
/* 182:    */ 
/* 183:346 */         lalr_item_set new_items = new lalr_item_set();
/* 184:347 */         for (i = st.items().all(); i.hasMoreElements();)
/* 185:    */         {
/* 186:349 */           itm = (lalr_item)i.nextElement();
/* 187:    */           
/* 188:    */ 
/* 189:352 */           symbol sym2 = itm.symbol_after_dot();
/* 190:353 */           if (sym.equals(sym2))
/* 191:    */           {
/* 192:356 */             new_items.add(itm.shift());
/* 193:    */             
/* 194:    */ 
/* 195:359 */             linked_items.add(itm);
/* 196:    */           }
/* 197:    */         }
/* 198:364 */         kernel = new lalr_item_set(new_items);
/* 199:    */         
/* 200:    */ 
/* 201:367 */         lalr_state new_st = (lalr_state)_all_kernels.get(kernel);
/* 202:    */         Enumeration fix;
/* 203:370 */         if (new_st == null)
/* 204:    */         {
/* 205:373 */           new_items.compute_closure();
/* 206:    */           
/* 207:    */ 
/* 208:376 */           new_st = new lalr_state(new_items);
/* 209:    */           
/* 210:    */ 
/* 211:379 */           work_stack.push(new_st);
/* 212:    */           
/* 213:    */ 
/* 214:382 */           _all_kernels.put(kernel, new_st);
/* 215:    */         }
/* 216:    */         else
/* 217:    */         {
/* 218:388 */           for (fix = linked_items.all(); fix.hasMoreElements();)
/* 219:    */           {
/* 220:390 */             lalr_item fix_itm = (lalr_item)fix.nextElement();
/* 221:393 */             for (int l = 0; l < fix_itm.propagate_items().size(); l++)
/* 222:    */             {
/* 223:396 */               lalr_item new_itm = (lalr_item)fix_itm.propagate_items().elementAt(l);
/* 224:    */               
/* 225:    */ 
/* 226:    */ 
/* 227:400 */               lalr_item existing = new_st.items().find(new_itm);
/* 228:403 */               if (existing != null) {
/* 229:404 */                 fix_itm.propagate_items().setElementAt(existing, l);
/* 230:    */               }
/* 231:    */             }
/* 232:    */           }
/* 233:    */         }
/* 234:410 */         st.add_transition(sym, new_st);
/* 235:    */       }
/* 236:    */     }
/* 237:417 */     propagate_all_lookaheads();
/* 238:    */     
/* 239:419 */     return start_state;
/* 240:    */   }
/* 241:    */   
/* 242:    */   protected void propagate_lookaheads()
/* 243:    */     throws internal_error
/* 244:    */   {
/* 245:431 */     for (Enumeration itm = items().all(); itm.hasMoreElements();) {
/* 246:432 */       ((lalr_item)itm.nextElement()).propagate_lookaheads(null);
/* 247:    */     }
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void build_table_entries(parse_action_table act_table, parse_reduce_table reduce_table)
/* 251:    */     throws internal_error
/* 252:    */   {
/* 253:468 */     terminal_set conflict_set = new terminal_set();
/* 254:    */     
/* 255:    */ 
/* 256:471 */     parse_action_row our_act_row = act_table.under_state[index()];
/* 257:472 */     parse_reduce_row our_red_row = reduce_table.under_state[index()];
/* 258:475 */     for (Enumeration i = items().all(); i.hasMoreElements();)
/* 259:    */     {
/* 260:477 */       lalr_item itm = (lalr_item)i.nextElement();
/* 261:481 */       if (itm.dot_at_end())
/* 262:    */       {
/* 263:483 */         parse_action act = new reduce_action(itm.the_production());
/* 264:486 */         for (int t = 0; t < terminal.number(); t++) {
/* 265:489 */           if (itm.lookahead().contains(t)) {
/* 266:492 */             if (our_act_row.under_term[t].kind() == 0)
/* 267:    */             {
/* 268:495 */               our_act_row.under_term[t] = act;
/* 269:    */             }
/* 270:    */             else
/* 271:    */             {
/* 272:500 */               terminal term = terminal.find(t);
/* 273:501 */               parse_action other_act = our_act_row.under_term[t];
/* 274:504 */               if ((other_act.kind() != 1) && (other_act.kind() != 3))
/* 275:    */               {
/* 276:508 */                 if (itm.the_production().index() < ((reduce_action)other_act).reduce_with().index()) {
/* 277:512 */                   our_act_row.under_term[t] = act;
/* 278:    */                 }
/* 279:    */               }
/* 280:516 */               else if (fix_with_precedence(itm.the_production(), t, our_act_row, act)) {
/* 281:518 */                 term = null;
/* 282:    */               }
/* 283:521 */               if (term != null) {
/* 284:523 */                 conflict_set.add(term);
/* 285:    */               }
/* 286:    */             }
/* 287:    */           }
/* 288:    */         }
/* 289:    */       }
/* 290:    */     }
/* 291:531 */     for (lalr_transition trans = transitions(); trans != null; trans = trans.next())
/* 292:    */     {
/* 293:534 */       symbol sym = trans.on_symbol();
/* 294:535 */       if (!sym.is_non_term())
/* 295:    */       {
/* 296:537 */         parse_action act = new shift_action(trans.to_state());
/* 297:540 */         if (our_act_row.under_term[sym.index()].kind() == 0)
/* 298:    */         {
/* 299:543 */           our_act_row.under_term[sym.index()] = act;
/* 300:    */         }
/* 301:    */         else
/* 302:    */         {
/* 303:548 */           production p = ((reduce_action)our_act_row.under_term[sym.index()]).reduce_with();
/* 304:551 */           if (!fix_with_precedence(p, sym.index(), our_act_row, act))
/* 305:    */           {
/* 306:552 */             our_act_row.under_term[sym.index()] = act;
/* 307:553 */             conflict_set.add(terminal.find(sym.index()));
/* 308:    */           }
/* 309:    */         }
/* 310:    */       }
/* 311:    */       else
/* 312:    */       {
/* 313:560 */         our_red_row.under_non_term[sym.index()] = trans.to_state();
/* 314:    */       }
/* 315:    */     }
/* 316:565 */     if (!conflict_set.empty()) {
/* 317:566 */       report_conflicts(conflict_set);
/* 318:    */     }
/* 319:    */   }
/* 320:    */   
/* 321:    */   protected boolean fix_with_precedence(production p, int term_index, parse_action_row table_row, parse_action act)
/* 322:    */     throws internal_error
/* 323:    */   {
/* 324:599 */     terminal term = terminal.find(term_index);
/* 325:602 */     if (p.precedence_num() > -1)
/* 326:    */     {
/* 327:605 */       if (p.precedence_num() > term.precedence_num())
/* 328:    */       {
/* 329:606 */         table_row.under_term[term_index] = insert_reduce(table_row.under_term[term_index], act);
/* 330:    */         
/* 331:608 */         return true;
/* 332:    */       }
/* 333:612 */       if (p.precedence_num() < term.precedence_num())
/* 334:    */       {
/* 335:613 */         table_row.under_term[term_index] = insert_shift(table_row.under_term[term_index], act);
/* 336:    */         
/* 337:615 */         return true;
/* 338:    */       }
/* 339:621 */       if (term.precedence_side() == 1)
/* 340:    */       {
/* 341:622 */         table_row.under_term[term_index] = insert_shift(table_row.under_term[term_index], act);
/* 342:    */         
/* 343:624 */         return true;
/* 344:    */       }
/* 345:628 */       if (term.precedence_side() == 0)
/* 346:    */       {
/* 347:629 */         table_row.under_term[term_index] = insert_reduce(table_row.under_term[term_index], act);
/* 348:    */         
/* 349:631 */         return true;
/* 350:    */       }
/* 351:636 */       if (term.precedence_side() == 2)
/* 352:    */       {
/* 353:637 */         table_row.under_term[term_index] = new nonassoc_action();
/* 354:638 */         return true;
/* 355:    */       }
/* 356:641 */       throw new internal_error("Unable to resolve conflict correctly");
/* 357:    */     }
/* 358:647 */     if (term.precedence_num() > -1)
/* 359:    */     {
/* 360:648 */       table_row.under_term[term_index] = insert_shift(table_row.under_term[term_index], act);
/* 361:    */       
/* 362:650 */       return true;
/* 363:    */     }
/* 364:655 */     return false;
/* 365:    */   }
/* 366:    */   
/* 367:    */   protected parse_action insert_action(parse_action a1, parse_action a2, int act_type)
/* 368:    */     throws internal_error
/* 369:    */   {
/* 370:673 */     if ((a1.kind() == act_type) && (a2.kind() == act_type)) {
/* 371:674 */       throw new internal_error("Conflict resolution of bogus actions");
/* 372:    */     }
/* 373:675 */     if (a1.kind() == act_type) {
/* 374:676 */       return a1;
/* 375:    */     }
/* 376:677 */     if (a2.kind() == act_type) {
/* 377:678 */       return a2;
/* 378:    */     }
/* 379:680 */     throw new internal_error("Conflict resolution of bogus actions");
/* 380:    */   }
/* 381:    */   
/* 382:    */   protected parse_action insert_shift(parse_action a1, parse_action a2)
/* 383:    */     throws internal_error
/* 384:    */   {
/* 385:690 */     return insert_action(a1, a2, 1);
/* 386:    */   }
/* 387:    */   
/* 388:    */   protected parse_action insert_reduce(parse_action a1, parse_action a2)
/* 389:    */     throws internal_error
/* 390:    */   {
/* 391:699 */     return insert_action(a1, a2, 2);
/* 392:    */   }
/* 393:    */   
/* 394:    */   protected void report_conflicts(terminal_set conflict_set)
/* 395:    */     throws internal_error
/* 396:    */   {
/* 397:714 */     for (Enumeration itms = items().all(); itms.hasMoreElements();)
/* 398:    */     {
/* 399:716 */       lalr_item itm = (lalr_item)itms.nextElement();
/* 400:721 */       if (itm.dot_at_end())
/* 401:    */       {
/* 402:724 */         boolean after_itm = false;
/* 403:727 */         for (Enumeration comps = items().all(); comps.hasMoreElements();)
/* 404:    */         {
/* 405:729 */           lalr_item compare = (lalr_item)comps.nextElement();
/* 406:732 */           if (itm == compare) {
/* 407:732 */             after_itm = true;
/* 408:    */           }
/* 409:735 */           if ((itm != compare) && 
/* 410:    */           
/* 411:    */ 
/* 412:738 */             (compare.dot_at_end()) && 
/* 413:    */             
/* 414:    */ 
/* 415:741 */             (after_itm) && 
/* 416:    */             
/* 417:743 */             (compare.lookahead().intersects(itm.lookahead()))) {
/* 418:745 */             report_reduce_reduce(itm, compare);
/* 419:    */           }
/* 420:    */         }
/* 421:750 */         terminal_set lookahead = itm.lookahead();
/* 422:751 */         for (int t = 0; t < terminal.number(); t++) {
/* 423:752 */           if ((conflict_set.contains(t)) && (lookahead.contains(t))) {
/* 424:753 */             report_shift_reduce(itm, t);
/* 425:    */           }
/* 426:    */         }
/* 427:    */       }
/* 428:    */     }
/* 429:    */   }
/* 430:    */   
/* 431:    */   protected void report_reduce_reduce(lalr_item itm1, lalr_item itm2)
/* 432:    */     throws internal_error
/* 433:    */   {
/* 434:768 */     boolean comma_flag = false;
/* 435:    */     
/* 436:770 */     String message = "*** Reduce/Reduce conflict found in state #" + index() + "\n" + "  between " + itm1.to_simple_string() + "\n" + "  and     " + itm2.to_simple_string() + "\n" + "  under symbols: {";
/* 437:774 */     for (int t = 0; t < terminal.number(); t++) {
/* 438:776 */       if ((itm1.lookahead().contains(t)) && (itm2.lookahead().contains(t)))
/* 439:    */       {
/* 440:778 */         if (comma_flag) {
/* 441:778 */           message = message + ", ";
/* 442:    */         } else {
/* 443:778 */           comma_flag = true;
/* 444:    */         }
/* 445:779 */         message = message + terminal.find(t).name();
/* 446:    */       }
/* 447:    */     }
/* 448:782 */     message = message + "}\n  Resolved in favor of ";
/* 449:783 */     if (itm1.the_production().index() < itm2.the_production().index()) {
/* 450:784 */       message = message + "the first production.\n";
/* 451:    */     } else {
/* 452:786 */       message = message + "the second production.\n";
/* 453:    */     }
/* 454:789 */     emit.num_conflicts += 1;
/* 455:790 */     ErrorManager.getManager().emit_warning(message);
/* 456:    */   }
/* 457:    */   
/* 458:    */   protected void report_shift_reduce(lalr_item red_itm, int conflict_sym)
/* 459:    */     throws internal_error
/* 460:    */   {
/* 461:809 */     String message = "*** Shift/Reduce conflict found in state #" + index() + "\n" + "  between " + red_itm.to_simple_string() + "\n";
/* 462:813 */     for (Enumeration itms = items().all(); itms.hasMoreElements();)
/* 463:    */     {
/* 464:815 */       lalr_item itm = (lalr_item)itms.nextElement();
/* 465:818 */       if ((itm != red_itm) && (!itm.dot_at_end()))
/* 466:    */       {
/* 467:821 */         symbol shift_sym = itm.symbol_after_dot();
/* 468:822 */         if ((!shift_sym.is_non_term()) && (shift_sym.index() == conflict_sym)) {
/* 469:825 */           message = message + "  and     " + itm.to_simple_string() + "\n";
/* 470:    */         }
/* 471:    */       }
/* 472:    */     }
/* 473:829 */     message = message + "  under symbol " + terminal.find(conflict_sym).name() + "\n" + "  Resolved in favor of shifting.\n";
/* 474:    */     
/* 475:    */ 
/* 476:    */ 
/* 477:833 */     emit.num_conflicts += 1;
/* 478:834 */     ErrorManager.getManager().emit_warning(message);
/* 479:    */   }
/* 480:    */   
/* 481:    */   public boolean equals(lalr_state other)
/* 482:    */   {
/* 483:843 */     return (other != null) && (items().equals(other.items()));
/* 484:    */   }
/* 485:    */   
/* 486:    */   public boolean equals(Object other)
/* 487:    */   {
/* 488:851 */     if (!(other instanceof lalr_state)) {
/* 489:852 */       return false;
/* 490:    */     }
/* 491:854 */     return equals((lalr_state)other);
/* 492:    */   }
/* 493:    */   
/* 494:    */   public int hashCode()
/* 495:    */   {
/* 496:863 */     return items().hashCode();
/* 497:    */   }
/* 498:    */   
/* 499:    */   public String toString()
/* 500:    */   {
/* 501:875 */     String result = "lalr_state [" + index() + "]: " + this._items + "\n";
/* 502:878 */     for (lalr_transition tr = transitions(); tr != null; tr = tr.next())
/* 503:    */     {
/* 504:880 */       result = result + tr;
/* 505:881 */       result = result + "\n";
/* 506:    */     }
/* 507:884 */     return result;
/* 508:    */   }
/* 509:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.lalr_state
 * JD-Core Version:    0.7.0.1
 */
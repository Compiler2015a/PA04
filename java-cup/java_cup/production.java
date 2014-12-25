/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ 
/*   6:    */ public class production
/*   7:    */ {
/*   8:    */   public production(non_terminal lhs_sym, production_part[] rhs_parts, int rhs_l, String action_str)
/*   9:    */     throws internal_error
/*  10:    */   {
/*  11: 66 */     int rightlen = rhs_l;
/*  12: 69 */     if (rhs_l >= 0) {
/*  13: 70 */       this._rhs_length = rhs_l;
/*  14: 71 */     } else if (rhs_parts != null) {
/*  15: 72 */       this._rhs_length = rhs_parts.length;
/*  16:    */     } else {
/*  17: 74 */       this._rhs_length = 0;
/*  18:    */     }
/*  19: 77 */     if (lhs_sym == null) {
/*  20: 78 */       throw new internal_error("Attempt to construct a production with a null LHS");
/*  21:    */     }
/*  22: 91 */     if (rhs_l > 0) {
/*  23: 92 */       if (rhs_parts[(rhs_l - 1)].is_action()) {
/*  24: 93 */         rightlen = rhs_l - 1;
/*  25:    */       } else {
/*  26: 95 */         rightlen = rhs_l;
/*  27:    */       }
/*  28:    */     }
/*  29:100 */     String declare_str = declare_labels(rhs_parts, rightlen, action_str);
/*  30:103 */     if (action_str == null) {
/*  31:104 */       action_str = declare_str;
/*  32:    */     } else {
/*  33:106 */       action_str = declare_str + action_str;
/*  34:    */     }
/*  35:109 */     lhs_sym.note_use();
/*  36:    */     
/*  37:    */ 
/*  38:112 */     this._lhs = new symbol_part(lhs_sym);
/*  39:    */     
/*  40:    */ 
/*  41:115 */     this._rhs_length = merge_adjacent_actions(rhs_parts, this._rhs_length);
/*  42:    */     
/*  43:    */ 
/*  44:118 */     action_part tail_action = strip_trailing_action(rhs_parts, this._rhs_length);
/*  45:119 */     if (tail_action != null) {
/*  46:119 */       this._rhs_length -= 1;
/*  47:    */     }
/*  48:127 */     this._rhs = new production_part[this._rhs_length];
/*  49:128 */     for (int i = 0; i < this._rhs_length; i++)
/*  50:    */     {
/*  51:129 */       this._rhs[i] = rhs_parts[i];
/*  52:130 */       if (!this._rhs[i].is_action())
/*  53:    */       {
/*  54:131 */         ((symbol_part)this._rhs[i]).the_symbol().note_use();
/*  55:132 */         if ((((symbol_part)this._rhs[i]).the_symbol() instanceof terminal))
/*  56:    */         {
/*  57:133 */           this._rhs_prec = ((terminal)((symbol_part)this._rhs[i]).the_symbol()).precedence_num();
/*  58:    */           
/*  59:135 */           this._rhs_assoc = ((terminal)((symbol_part)this._rhs[i]).the_symbol()).precedence_side();
/*  60:    */         }
/*  61:    */       }
/*  62:    */     }
/*  63:143 */     if (action_str == null) {
/*  64:143 */       action_str = "";
/*  65:    */     }
/*  66:144 */     if ((tail_action != null) && (tail_action.code_string() != null)) {
/*  67:145 */       action_str = action_str + "\t\t" + tail_action.code_string();
/*  68:    */     }
/*  69:148 */     this._action = new action_part(action_str);
/*  70:    */     
/*  71:    */ 
/*  72:151 */     remove_embedded_actions();
/*  73:    */     
/*  74:    */ 
/*  75:154 */     this._index = (next_index++);
/*  76:    */     
/*  77:    */ 
/*  78:157 */     _all.put(new Integer(this._index), this);
/*  79:    */     
/*  80:    */ 
/*  81:160 */     lhs_sym.add_production(this);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public production(non_terminal lhs_sym, production_part[] rhs_parts, int rhs_l)
/*  85:    */     throws internal_error
/*  86:    */   {
/*  87:172 */     this(lhs_sym, rhs_parts, rhs_l, null);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public production(non_terminal lhs_sym, production_part[] rhs_parts, int rhs_l, String action_str, int prec_num, int prec_side)
/*  91:    */     throws internal_error
/*  92:    */   {
/*  93:188 */     this(lhs_sym, rhs_parts, rhs_l, action_str);
/*  94:    */     
/*  95:    */ 
/*  96:191 */     set_precedence_num(prec_num);
/*  97:192 */     set_precedence_side(prec_side);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public production(non_terminal lhs_sym, production_part[] rhs_parts, int rhs_l, int prec_num, int prec_side)
/* 101:    */     throws internal_error
/* 102:    */   {
/* 103:207 */     this(lhs_sym, rhs_parts, rhs_l, null);
/* 104:    */     
/* 105:209 */     set_precedence_num(prec_num);
/* 106:210 */     set_precedence_side(prec_side);
/* 107:    */   }
/* 108:    */   
/* 109:223 */   protected static Hashtable _all = new Hashtable();
/* 110:    */   protected static int next_index;
/* 111:    */   protected symbol_part _lhs;
/* 112:    */   
/* 113:    */   public static Enumeration all()
/* 114:    */   {
/* 115:226 */     return _all.elements();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static production find(int indx)
/* 119:    */   {
/* 120:230 */     return (production)_all.get(new Integer(indx));
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static void clear()
/* 124:    */   {
/* 125:235 */     _all.clear();
/* 126:236 */     next_index = 0;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static int number()
/* 130:    */   {
/* 131:242 */     return _all.size();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public symbol_part lhs()
/* 135:    */   {
/* 136:255 */     return this._lhs;
/* 137:    */   }
/* 138:    */   
/* 139:259 */   protected int _rhs_prec = -1;
/* 140:260 */   protected int _rhs_assoc = -1;
/* 141:    */   protected production_part[] _rhs;
/* 142:    */   protected int _rhs_length;
/* 143:    */   protected action_part _action;
/* 144:    */   protected int _index;
/* 145:    */   
/* 146:    */   public int precedence_num()
/* 147:    */   {
/* 148:263 */     return this._rhs_prec;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int precedence_side()
/* 152:    */   {
/* 153:264 */     return this._rhs_assoc;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void set_precedence_num(int prec_num)
/* 157:    */   {
/* 158:268 */     this._rhs_prec = prec_num;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void set_precedence_side(int prec_side)
/* 162:    */   {
/* 163:271 */     this._rhs_assoc = prec_side;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public production_part rhs(int indx)
/* 167:    */     throws internal_error
/* 168:    */   {
/* 169:282 */     if ((indx >= 0) && (indx < this._rhs_length)) {
/* 170:283 */       return this._rhs[indx];
/* 171:    */     }
/* 172:285 */     throw new internal_error("Index out of range for right hand side of production");
/* 173:    */   }
/* 174:    */   
/* 175:    */   public int rhs_length()
/* 176:    */   {
/* 177:295 */     return this._rhs_length;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public action_part action()
/* 181:    */   {
/* 182:307 */     return this._action;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public int index()
/* 186:    */   {
/* 187:315 */     return this._index;
/* 188:    */   }
/* 189:    */   
/* 190:320 */   protected int _num_reductions = 0;
/* 191:    */   
/* 192:    */   public int num_reductions()
/* 193:    */   {
/* 194:323 */     return this._num_reductions;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void note_reduction_use()
/* 198:    */   {
/* 199:326 */     this._num_reductions += 1;
/* 200:    */   }
/* 201:    */   
/* 202:331 */   protected boolean _nullable_known = false;
/* 203:    */   
/* 204:    */   public boolean nullable_known()
/* 205:    */   {
/* 206:334 */     return this._nullable_known;
/* 207:    */   }
/* 208:    */   
/* 209:339 */   protected boolean _nullable = false;
/* 210:    */   
/* 211:    */   public boolean nullable()
/* 212:    */   {
/* 213:342 */     return this._nullable;
/* 214:    */   }
/* 215:    */   
/* 216:349 */   protected terminal_set _first_set = new terminal_set();
/* 217:    */   
/* 218:    */   public terminal_set first_set()
/* 219:    */   {
/* 220:354 */     return this._first_set;
/* 221:    */   }
/* 222:    */   
/* 223:    */   protected static boolean is_id_start(char c)
/* 224:    */   {
/* 225:365 */     return ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) || (c == '_');
/* 226:    */   }
/* 227:    */   
/* 228:    */   protected static boolean is_id_char(char c)
/* 229:    */   {
/* 230:377 */     return (is_id_start(c)) || ((c >= '0') && (c <= '9'));
/* 231:    */   }
/* 232:    */   
/* 233:    */   protected String make_declaration(String labelname, String stack_type, int offset)
/* 234:    */   {
/* 235:    */     String ret;
/* 236:    */     String ret;
/* 237:398 */     if (emit.lr_values())
/* 238:    */     {
/* 239:    */       String ret;
/* 240:399 */       if (!emit.locations()) {
/* 241:400 */         ret = "\t\tint " + labelname + "left = ((java_cup.runtime.Symbol)" + emit.pre("stack") + (offset == 0 ? ".peek()" : new StringBuilder().append(".elementAt(").append(emit.pre("top")).append("-").append(offset).append(")").toString()) + ").left;\n" + "\t\tint " + labelname + "right = ((java_cup.runtime.Symbol)" + emit.pre("stack") + (offset == 0 ? ".peek()" : new StringBuilder().append(".elementAt(").append(emit.pre("top")).append("-").append(offset).append(")").toString()) + ").right;\n";
/* 242:    */       } else {
/* 243:411 */         ret = "\t\tLocation " + labelname + "xleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)" + emit.pre("stack") + (offset == 0 ? ".peek()" : new StringBuilder().append(".elementAt(").append(emit.pre("top")).append("-").append(offset).append(")").toString()) + ").xleft;\n" + "\t\tLocation " + labelname + "xright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)" + emit.pre("stack") + (offset == 0 ? ".peek()" : new StringBuilder().append(".elementAt(").append(emit.pre("top")).append("-").append(offset).append(")").toString()) + ").xright;\n";
/* 244:    */       }
/* 245:    */     }
/* 246:    */     else
/* 247:    */     {
/* 248:421 */       ret = "";
/* 249:    */     }
/* 250:424 */     return ret + "\t\t" + stack_type + " " + labelname + " = (" + stack_type + ")((" + "java_cup.runtime.Symbol) " + emit.pre("stack") + (offset == 0 ? ".peek()" : new StringBuilder().append(".elementAt(").append(emit.pre("top")).append("-").append(offset).append(")").toString()) + ").value;\n";
/* 251:    */   }
/* 252:    */   
/* 253:    */   protected String declare_labels(production_part[] rhs, int rhs_len, String final_action)
/* 254:    */   {
/* 255:444 */     String declaration = "";
/* 256:451 */     for (int pos = 0; pos < rhs_len; pos++) {
/* 257:453 */       if (!rhs[pos].is_action())
/* 258:    */       {
/* 259:455 */         symbol_part part = (symbol_part)rhs[pos];
/* 260:    */         String label;
/* 261:458 */         if (((label = part.label()) != null) || (emit._xmlactions))
/* 262:    */         {
/* 263:460 */           if (label == null) {
/* 264:460 */             label = part.the_symbol().name() + pos;
/* 265:    */           }
/* 266:461 */           declaration = declaration + make_declaration(label, part.the_symbol().stack_type(), rhs_len - pos - 1);
/* 267:    */         }
/* 268:    */       }
/* 269:    */     }
/* 270:467 */     return declaration;
/* 271:    */   }
/* 272:    */   
/* 273:    */   protected int merge_adjacent_actions(production_part[] rhs_parts, int len)
/* 274:    */   {
/* 275:484 */     if ((rhs_parts == null) || (len == 0)) {
/* 276:484 */       return 0;
/* 277:    */     }
/* 278:486 */     int merge_cnt = 0;
/* 279:487 */     int to_loc = -1;
/* 280:488 */     for (int from_loc = 0; from_loc < len; from_loc++)
/* 281:    */     {
/* 282:491 */       if ((to_loc < 0) || (!rhs_parts[to_loc].is_action()) || (!rhs_parts[from_loc].is_action()))
/* 283:    */       {
/* 284:495 */         to_loc++;
/* 285:498 */         if (to_loc != from_loc) {
/* 286:498 */           rhs_parts[to_loc] = null;
/* 287:    */         }
/* 288:    */       }
/* 289:502 */       if (to_loc != from_loc) {
/* 290:505 */         if ((rhs_parts[to_loc] != null) && (rhs_parts[to_loc].is_action()) && (rhs_parts[from_loc].is_action()))
/* 291:    */         {
/* 292:509 */           rhs_parts[to_loc] = new action_part(((action_part)rhs_parts[to_loc]).code_string() + ((action_part)rhs_parts[from_loc]).code_string());
/* 293:    */           
/* 294:    */ 
/* 295:512 */           merge_cnt++;
/* 296:    */         }
/* 297:    */         else
/* 298:    */         {
/* 299:517 */           rhs_parts[to_loc] = rhs_parts[from_loc];
/* 300:    */         }
/* 301:    */       }
/* 302:    */     }
/* 303:523 */     return len - merge_cnt;
/* 304:    */   }
/* 305:    */   
/* 306:    */   protected action_part strip_trailing_action(production_part[] rhs_parts, int len)
/* 307:    */   {
/* 308:540 */     if ((rhs_parts == null) || (len == 0)) {
/* 309:540 */       return null;
/* 310:    */     }
/* 311:543 */     if (rhs_parts[(len - 1)].is_action())
/* 312:    */     {
/* 313:546 */       action_part result = (action_part)rhs_parts[(len - 1)];
/* 314:547 */       rhs_parts[(len - 1)] = null;
/* 315:548 */       return result;
/* 316:    */     }
/* 317:551 */     return null;
/* 318:    */   }
/* 319:    */   
/* 320:    */   protected void remove_embedded_actions()
/* 321:    */     throws internal_error
/* 322:    */   {
/* 323:580 */     int lastLocation = -1;
/* 324:582 */     for (int act_loc = 0; act_loc < rhs_length(); act_loc++) {
/* 325:583 */       if (rhs(act_loc).is_action())
/* 326:    */       {
/* 327:587 */         String declare_str = declare_labels(this._rhs, act_loc, "");
/* 328:    */         
/* 329:    */ 
/* 330:590 */         non_terminal new_nt = non_terminal.create_new(null, lhs().the_symbol().stack_type());
/* 331:591 */         new_nt.is_embedded_action = true;
/* 332:    */         
/* 333:    */ 
/* 334:594 */         production new_prod = new action_production(this, new_nt, null, 0, declare_str + ((action_part)rhs(act_loc)).code_string(), lastLocation == -1 ? -1 : act_loc - lastLocation);
/* 335:    */         
/* 336:    */ 
/* 337:    */ 
/* 338:598 */         this._rhs[act_loc] = new symbol_part(new_nt);
/* 339:599 */         lastLocation = act_loc;
/* 340:    */       }
/* 341:    */     }
/* 342:    */   }
/* 343:    */   
/* 344:    */   public boolean check_nullable()
/* 345:    */     throws internal_error
/* 346:    */   {
/* 347:617 */     if (nullable_known()) {
/* 348:617 */       return nullable();
/* 349:    */     }
/* 350:620 */     if (rhs_length() == 0) {
/* 351:623 */       return set_nullable(true);
/* 352:    */     }
/* 353:627 */     for (int pos = 0; pos < rhs_length(); pos++)
/* 354:    */     {
/* 355:629 */       production_part part = rhs(pos);
/* 356:632 */       if (!part.is_action())
/* 357:    */       {
/* 358:634 */         symbol sym = ((symbol_part)part).the_symbol();
/* 359:637 */         if (!sym.is_non_term()) {
/* 360:638 */           return set_nullable(false);
/* 361:    */         }
/* 362:640 */         if (!((non_terminal)sym).nullable()) {
/* 363:642 */           return false;
/* 364:    */         }
/* 365:    */       }
/* 366:    */     }
/* 367:647 */     return set_nullable(true);
/* 368:    */   }
/* 369:    */   
/* 370:    */   boolean set_nullable(boolean v)
/* 371:    */   {
/* 372:653 */     this._nullable_known = true;
/* 373:654 */     this._nullable = v;
/* 374:655 */     return v;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public terminal_set check_first_set()
/* 378:    */     throws internal_error
/* 379:    */   {
/* 380:670 */     for (int part = 0; part < rhs_length(); part++) {
/* 381:673 */       if (!rhs(part).is_action())
/* 382:    */       {
/* 383:675 */         symbol sym = ((symbol_part)rhs(part)).the_symbol();
/* 384:678 */         if (sym.is_non_term())
/* 385:    */         {
/* 386:681 */           this._first_set.add(((non_terminal)sym).first_set());
/* 387:684 */           if (!((non_terminal)sym).nullable()) {
/* 388:    */             break;
/* 389:    */           }
/* 390:    */         }
/* 391:    */         else
/* 392:    */         {
/* 393:690 */           this._first_set.add((terminal)sym);
/* 394:    */           
/* 395:    */ 
/* 396:693 */           break;
/* 397:    */         }
/* 398:    */       }
/* 399:    */     }
/* 400:699 */     return first_set();
/* 401:    */   }
/* 402:    */   
/* 403:    */   public boolean equals(production other)
/* 404:    */   {
/* 405:707 */     if (other == null) {
/* 406:707 */       return false;
/* 407:    */     }
/* 408:708 */     return other._index == this._index;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public boolean equals(Object other)
/* 412:    */   {
/* 413:716 */     if (!(other instanceof production)) {
/* 414:717 */       return false;
/* 415:    */     }
/* 416:719 */     return equals((production)other);
/* 417:    */   }
/* 418:    */   
/* 419:    */   public int hashCode()
/* 420:    */   {
/* 421:728 */     return this._index * 13;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public String toString()
/* 425:    */   {
/* 426:    */     String result;
/* 427:    */     try
/* 428:    */     {
/* 429:740 */       result = "production [" + index() + "]: ";
/* 430:741 */       result = result + (lhs() != null ? lhs().toString() : "$$NULL-LHS$$");
/* 431:742 */       result = result + " :: = ";
/* 432:743 */       for (int i = 0; i < rhs_length(); i++) {
/* 433:744 */         result = result + rhs(i) + " ";
/* 434:    */       }
/* 435:745 */       result = result + ";";
/* 436:746 */       if ((action() != null) && (action().code_string() != null)) {
/* 437:747 */         result = result + " {" + action().code_string() + "}";
/* 438:    */       }
/* 439:749 */       if (nullable_known()) {
/* 440:750 */         if (nullable()) {
/* 441:751 */           result = result + "[NULLABLE]";
/* 442:    */         } else {
/* 443:753 */           result = result + "[NOT NULLABLE]";
/* 444:    */         }
/* 445:    */       }
/* 446:    */     }
/* 447:    */     catch (internal_error e)
/* 448:    */     {
/* 449:757 */       e.crash();
/* 450:758 */       result = null;
/* 451:    */     }
/* 452:761 */     return result;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public String to_simple_string()
/* 456:    */     throws internal_error
/* 457:    */   {
/* 458:771 */     String result = lhs() != null ? lhs().the_symbol().name() : "NULL_LHS";
/* 459:772 */     result = result + " ::= ";
/* 460:773 */     for (int i = 0; i < rhs_length(); i++) {
/* 461:774 */       if (!rhs(i).is_action()) {
/* 462:775 */         result = result + ((symbol_part)rhs(i)).the_symbol().name() + " ";
/* 463:    */       }
/* 464:    */     }
/* 465:777 */     return result;
/* 466:    */   }
/* 467:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.production
 * JD-Core Version:    0.7.0.1
 */
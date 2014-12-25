/*   1:    */ package java_cup;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.InputStreamReader;
/*   6:    */ import java.io.Reader;
/*   7:    */ import java_cup.runtime.ComplexSymbolFactory;
/*   8:    */ import java_cup.runtime.ComplexSymbolFactory.Location;
/*   9:    */ import java_cup.runtime.Scanner;
/*  10:    */ import java_cup.runtime.Symbol;
/*  11:    */ 
/*  12:    */ public class Lexer
/*  13:    */   implements sym, Scanner
/*  14:    */ {
/*  15:    */   public static final int YYEOF = -1;
/*  16:    */   private static final int ZZ_BUFFERSIZE = 16384;
/*  17:    */   public static final int CODESEG = 2;
/*  18:    */   public static final int YYINITIAL = 0;
/*  19: 35 */   private static final int[] ZZ_LEXSTATE = { 0, 0, 1, 1 };
/*  20:    */   private static final String ZZ_CMAP_PACKED = "";
/*  21:157 */   private static final char[] ZZ_CMAP = zzUnpackCMap("");
/*  22:162 */   private static final int[] ZZ_ACTION = zzUnpackAction();
/*  23:    */   private static final String ZZ_ACTION_PACKED_0 = "";
/*  24:    */   
/*  25:    */   private static int[] zzUnpackAction()
/*  26:    */   {
/*  27:176 */     int[] result = new int[''];
/*  28:177 */     int offset = 0;
/*  29:178 */     offset = zzUnpackAction("", offset, result);
/*  30:179 */     return result;
/*  31:    */   }
/*  32:    */   
/*  33:    */   private static int zzUnpackAction(String packed, int offset, int[] result)
/*  34:    */   {
/*  35:183 */     int i = 0;
/*  36:184 */     int j = offset;
/*  37:185 */     int l = packed.length();
/*  38:    */     int count;
/*  39:186 */     for (; i < l; count > 0)
/*  40:    */     {
/*  41:187 */       count = packed.charAt(i++);
/*  42:188 */       int value = packed.charAt(i++);
/*  43:189 */       result[(j++)] = value;count--;
/*  44:    */     }
/*  45:191 */     return j;
/*  46:    */   }
/*  47:    */   
/*  48:198 */   private static final int[] ZZ_ROWMAP = zzUnpackRowMap();
/*  49:    */   private static final String ZZ_ROWMAP_PACKED_0 = "";
/*  50:    */   
/*  51:    */   private static int[] zzUnpackRowMap()
/*  52:    */   {
/*  53:220 */     int[] result = new int[''];
/*  54:221 */     int offset = 0;
/*  55:222 */     offset = zzUnpackRowMap("", offset, result);
/*  56:223 */     return result;
/*  57:    */   }
/*  58:    */   
/*  59:    */   private static int zzUnpackRowMap(String packed, int offset, int[] result)
/*  60:    */   {
/*  61:227 */     int i = 0;
/*  62:228 */     int j = offset;
/*  63:229 */     int l = packed.length();
/*  64:230 */     while (i < l)
/*  65:    */     {
/*  66:231 */       int high = packed.charAt(i++) << '\020';
/*  67:232 */       result[(j++)] = (high | packed.charAt(i++));
/*  68:    */     }
/*  69:234 */     return j;
/*  70:    */   }
/*  71:    */   
/*  72:240 */   private static final int[] ZZ_TRANS = zzUnpackTrans();
/*  73:    */   private static final String ZZ_TRANS_PACKED_0 = "";
/*  74:    */   private static final int ZZ_UNKNOWN_ERROR = 0;
/*  75:    */   private static final int ZZ_NO_MATCH = 1;
/*  76:    */   private static final int ZZ_PUSHBACK_2BIG = 2;
/*  77:    */   
/*  78:    */   private static int[] zzUnpackTrans()
/*  79:    */   {
/*  80:336 */     int[] result = new int[4116];
/*  81:337 */     int offset = 0;
/*  82:338 */     offset = zzUnpackTrans("", offset, result);
/*  83:339 */     return result;
/*  84:    */   }
/*  85:    */   
/*  86:    */   private static int zzUnpackTrans(String packed, int offset, int[] result)
/*  87:    */   {
/*  88:343 */     int i = 0;
/*  89:344 */     int j = offset;
/*  90:345 */     int l = packed.length();
/*  91:    */     int count;
/*  92:346 */     for (; i < l; count > 0)
/*  93:    */     {
/*  94:347 */       count = packed.charAt(i++);
/*  95:348 */       int value = packed.charAt(i++);
/*  96:349 */       value--;
/*  97:350 */       result[(j++)] = value;count--;
/*  98:    */     }
/*  99:352 */     return j;
/* 100:    */   }
/* 101:    */   
/* 102:362 */   private static final String[] ZZ_ERROR_MSG = { "Unkown internal scanner error", "Error: could not match input", "Error: pushback value was too large" };
/* 103:371 */   private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();
/* 104:    */   private static final String ZZ_ATTRIBUTE_PACKED_0 = "";
/* 105:    */   private Reader zzReader;
/* 106:    */   private int zzState;
/* 107:    */   
/* 108:    */   private static int[] zzUnpackAttribute()
/* 109:    */   {
/* 110:380 */     int[] result = new int[''];
/* 111:381 */     int offset = 0;
/* 112:382 */     offset = zzUnpackAttribute("", offset, result);
/* 113:383 */     return result;
/* 114:    */   }
/* 115:    */   
/* 116:    */   private static int zzUnpackAttribute(String packed, int offset, int[] result)
/* 117:    */   {
/* 118:387 */     int i = 0;
/* 119:388 */     int j = offset;
/* 120:389 */     int l = packed.length();
/* 121:    */     int count;
/* 122:390 */     for (; i < l; count > 0)
/* 123:    */     {
/* 124:391 */       count = packed.charAt(i++);
/* 125:392 */       int value = packed.charAt(i++);
/* 126:393 */       result[(j++)] = value;count--;
/* 127:    */     }
/* 128:395 */     return j;
/* 129:    */   }
/* 130:    */   
/* 131:405 */   private int zzLexicalState = 0;
/* 132:409 */   private char[] zzBuffer = new char[16384];
/* 133:    */   private int zzMarkedPos;
/* 134:    */   private int zzCurrentPos;
/* 135:    */   private int zzStartRead;
/* 136:    */   private int zzEndRead;
/* 137:    */   private int yyline;
/* 138:    */   private int yychar;
/* 139:    */   private int yycolumn;
/* 140:439 */   private boolean zzAtBOL = true;
/* 141:    */   private boolean zzAtEOF;
/* 142:    */   private boolean zzEOFDone;
/* 143:    */   private StringBuffer sb;
/* 144:    */   private ComplexSymbolFactory symbolFactory;
/* 145:    */   private int csline;
/* 146:    */   private int cscolumn;
/* 147:    */   
/* 148:    */   public Lexer(ComplexSymbolFactory sf)
/* 149:    */   {
/* 150:449 */     this(new InputStreamReader(System.in));
/* 151:450 */     this.symbolFactory = sf;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Symbol symbol(String name, int code)
/* 155:    */   {
/* 156:456 */     return this.symbolFactory.newSymbol(name, code, new ComplexSymbolFactory.Location(this.yyline + 1, this.yycolumn + 1 - yylength()), new ComplexSymbolFactory.Location(this.yyline + 1, this.yycolumn + 1));
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Symbol symbol(String name, int code, String lexem)
/* 160:    */   {
/* 161:459 */     return this.symbolFactory.newSymbol(name, code, new ComplexSymbolFactory.Location(this.yyline + 1, this.yycolumn + 1), new ComplexSymbolFactory.Location(this.yyline + 1, this.yycolumn + yylength()), lexem);
/* 162:    */   }
/* 163:    */   
/* 164:    */   protected void emit_warning(String message)
/* 165:    */   {
/* 166:462 */     ErrorManager.getManager().emit_warning("Scanner at " + (this.yyline + 1) + "(" + (this.yycolumn + 1) + "): " + message);
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected void emit_error(String message)
/* 170:    */   {
/* 171:465 */     ErrorManager.getManager().emit_error("Scanner at " + (this.yyline + 1) + "(" + (this.yycolumn + 1) + "): " + message);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Lexer(Reader in)
/* 175:    */   {
/* 176:476 */     this.zzReader = in;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Lexer(InputStream in)
/* 180:    */   {
/* 181:486 */     this(new InputStreamReader(in));
/* 182:    */   }
/* 183:    */   
/* 184:    */   private static char[] zzUnpackCMap(String packed)
/* 185:    */   {
/* 186:496 */     char[] map = new char[65536];
/* 187:497 */     int i = 0;
/* 188:498 */     int j = 0;
/* 189:    */     int count;
/* 190:499 */     for (; i < 2200; count > 0)
/* 191:    */     {
/* 192:500 */       count = packed.charAt(i++);
/* 193:501 */       char value = packed.charAt(i++);
/* 194:502 */       map[(j++)] = value;count--;
/* 195:    */     }
/* 196:504 */     return map;
/* 197:    */   }
/* 198:    */   
/* 199:    */   private boolean zzRefill()
/* 200:    */     throws IOException
/* 201:    */   {
/* 202:518 */     if (this.zzStartRead > 0)
/* 203:    */     {
/* 204:519 */       System.arraycopy(this.zzBuffer, this.zzStartRead, this.zzBuffer, 0, this.zzEndRead - this.zzStartRead);
/* 205:    */       
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:524 */       this.zzEndRead -= this.zzStartRead;
/* 210:525 */       this.zzCurrentPos -= this.zzStartRead;
/* 211:526 */       this.zzMarkedPos -= this.zzStartRead;
/* 212:527 */       this.zzStartRead = 0;
/* 213:    */     }
/* 214:531 */     if (this.zzCurrentPos >= this.zzBuffer.length)
/* 215:    */     {
/* 216:533 */       char[] newBuffer = new char[this.zzCurrentPos * 2];
/* 217:534 */       System.arraycopy(this.zzBuffer, 0, newBuffer, 0, this.zzBuffer.length);
/* 218:535 */       this.zzBuffer = newBuffer;
/* 219:    */     }
/* 220:539 */     int numRead = this.zzReader.read(this.zzBuffer, this.zzEndRead, this.zzBuffer.length - this.zzEndRead);
/* 221:542 */     if (numRead > 0)
/* 222:    */     {
/* 223:543 */       this.zzEndRead += numRead;
/* 224:544 */       return false;
/* 225:    */     }
/* 226:547 */     if (numRead == 0)
/* 227:    */     {
/* 228:548 */       int c = this.zzReader.read();
/* 229:549 */       if (c == -1) {
/* 230:550 */         return true;
/* 231:    */       }
/* 232:552 */       this.zzBuffer[(this.zzEndRead++)] = ((char)c);
/* 233:553 */       return false;
/* 234:    */     }
/* 235:558 */     return true;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public final void yyclose()
/* 239:    */     throws IOException
/* 240:    */   {
/* 241:566 */     this.zzAtEOF = true;
/* 242:567 */     this.zzEndRead = this.zzStartRead;
/* 243:569 */     if (this.zzReader != null) {
/* 244:570 */       this.zzReader.close();
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:    */   public final void yyreset(Reader reader)
/* 249:    */   {
/* 250:585 */     this.zzReader = reader;
/* 251:586 */     this.zzAtBOL = true;
/* 252:587 */     this.zzAtEOF = false;
/* 253:588 */     this.zzEOFDone = false;
/* 254:589 */     this.zzEndRead = (this.zzStartRead = 0);
/* 255:590 */     this.zzCurrentPos = (this.zzMarkedPos = 0);
/* 256:591 */     this.yyline = (this.yychar = this.yycolumn = 0);
/* 257:592 */     this.zzLexicalState = 0;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public final int yystate()
/* 261:    */   {
/* 262:600 */     return this.zzLexicalState;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public final void yybegin(int newState)
/* 266:    */   {
/* 267:610 */     this.zzLexicalState = newState;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public final String yytext()
/* 271:    */   {
/* 272:618 */     return new String(this.zzBuffer, this.zzStartRead, this.zzMarkedPos - this.zzStartRead);
/* 273:    */   }
/* 274:    */   
/* 275:    */   public final char yycharat(int pos)
/* 276:    */   {
/* 277:634 */     return this.zzBuffer[(this.zzStartRead + pos)];
/* 278:    */   }
/* 279:    */   
/* 280:    */   public final int yylength()
/* 281:    */   {
/* 282:642 */     return this.zzMarkedPos - this.zzStartRead;
/* 283:    */   }
/* 284:    */   
/* 285:    */   private void zzScanError(int errorCode)
/* 286:    */   {
/* 287:    */     String message;
/* 288:    */     try
/* 289:    */     {
/* 290:663 */       message = ZZ_ERROR_MSG[errorCode];
/* 291:    */     }
/* 292:    */     catch (ArrayIndexOutOfBoundsException e)
/* 293:    */     {
/* 294:666 */       message = ZZ_ERROR_MSG[0];
/* 295:    */     }
/* 296:669 */     throw new Error(message);
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void yypushback(int number)
/* 300:    */   {
/* 301:682 */     if (number > yylength()) {
/* 302:683 */       zzScanError(2);
/* 303:    */     }
/* 304:685 */     this.zzMarkedPos -= number;
/* 305:    */   }
/* 306:    */   
/* 307:    */   private void zzDoEOF()
/* 308:    */     throws IOException
/* 309:    */   {
/* 310:694 */     if (!this.zzEOFDone)
/* 311:    */     {
/* 312:695 */       this.zzEOFDone = true;
/* 313:696 */       yyclose();
/* 314:    */     }
/* 315:    */   }
/* 316:    */   
/* 317:    */   public Symbol next_token()
/* 318:    */     throws IOException
/* 319:    */   {
/* 320:715 */     int zzEndReadL = this.zzEndRead;
/* 321:716 */     char[] zzBufferL = this.zzBuffer;
/* 322:717 */     char[] zzCMapL = ZZ_CMAP;
/* 323:    */     
/* 324:719 */     int[] zzTransL = ZZ_TRANS;
/* 325:720 */     int[] zzRowMapL = ZZ_ROWMAP;
/* 326:721 */     int[] zzAttrL = ZZ_ATTRIBUTE;
/* 327:    */     for (;;)
/* 328:    */     {
/* 329:724 */       int zzMarkedPosL = this.zzMarkedPos;
/* 330:    */       
/* 331:726 */       boolean zzR = false;
/* 332:727 */       for (int zzCurrentPosL = this.zzStartRead; zzCurrentPosL < zzMarkedPosL; zzCurrentPosL++) {
/* 333:729 */         switch (zzBufferL[zzCurrentPosL])
/* 334:    */         {
/* 335:    */         case '\013': 
/* 336:    */         case '\f': 
/* 337:    */         case '': 
/* 338:    */         case ' ': 
/* 339:    */         case ' ': 
/* 340:735 */           this.yyline += 1;
/* 341:736 */           this.yycolumn = 0;
/* 342:737 */           zzR = false;
/* 343:738 */           break;
/* 344:    */         case '\r': 
/* 345:740 */           this.yyline += 1;
/* 346:741 */           this.yycolumn = 0;
/* 347:742 */           zzR = true;
/* 348:743 */           break;
/* 349:    */         case '\n': 
/* 350:745 */           if (zzR)
/* 351:    */           {
/* 352:746 */             zzR = false;
/* 353:    */           }
/* 354:    */           else
/* 355:    */           {
/* 356:748 */             this.yyline += 1;
/* 357:749 */             this.yycolumn = 0;
/* 358:    */           }
/* 359:751 */           break;
/* 360:    */         default: 
/* 361:753 */           zzR = false;
/* 362:754 */           this.yycolumn += 1;
/* 363:    */         }
/* 364:    */       }
/* 365:758 */       if (zzR)
/* 366:    */       {
/* 367:    */         boolean zzPeek;
/* 368:    */         boolean zzPeek;
/* 369:761 */         if (zzMarkedPosL < zzEndReadL)
/* 370:    */         {
/* 371:762 */           zzPeek = zzBufferL[zzMarkedPosL] == '\n';
/* 372:    */         }
/* 373:    */         else
/* 374:    */         {
/* 375:    */           boolean zzPeek;
/* 376:763 */           if (this.zzAtEOF)
/* 377:    */           {
/* 378:764 */             zzPeek = false;
/* 379:    */           }
/* 380:    */           else
/* 381:    */           {
/* 382:766 */             boolean eof = zzRefill();
/* 383:767 */             zzEndReadL = this.zzEndRead;
/* 384:768 */             zzMarkedPosL = this.zzMarkedPos;
/* 385:769 */             zzBufferL = this.zzBuffer;
/* 386:    */             boolean zzPeek;
/* 387:770 */             if (eof) {
/* 388:771 */               zzPeek = false;
/* 389:    */             } else {
/* 390:773 */               zzPeek = zzBufferL[zzMarkedPosL] == '\n';
/* 391:    */             }
/* 392:    */           }
/* 393:    */         }
/* 394:775 */         if (zzPeek) {
/* 395:775 */           this.yyline -= 1;
/* 396:    */         }
/* 397:    */       }
/* 398:777 */       int zzAction = -1;
/* 399:    */       
/* 400:779 */       zzCurrentPosL = this.zzCurrentPos = this.zzStartRead = zzMarkedPosL;
/* 401:    */       
/* 402:781 */       this.zzState = ZZ_LEXSTATE[this.zzLexicalState];
/* 403:    */       int zzInput;
/* 404:    */       for (;;)
/* 405:    */       {
/* 406:    */         int zzInput;
/* 407:787 */         if (zzCurrentPosL < zzEndReadL)
/* 408:    */         {
/* 409:788 */           zzInput = zzBufferL[(zzCurrentPosL++)];
/* 410:    */         }
/* 411:    */         else
/* 412:    */         {
/* 413:789 */           if (this.zzAtEOF)
/* 414:    */           {
/* 415:790 */             int zzInput = -1;
/* 416:791 */             break;
/* 417:    */           }
/* 418:795 */           this.zzCurrentPos = zzCurrentPosL;
/* 419:796 */           this.zzMarkedPos = zzMarkedPosL;
/* 420:797 */           boolean eof = zzRefill();
/* 421:    */           
/* 422:799 */           zzCurrentPosL = this.zzCurrentPos;
/* 423:800 */           zzMarkedPosL = this.zzMarkedPos;
/* 424:801 */           zzBufferL = this.zzBuffer;
/* 425:802 */           zzEndReadL = this.zzEndRead;
/* 426:803 */           if (eof)
/* 427:    */           {
/* 428:804 */             int zzInput = -1;
/* 429:805 */             break;
/* 430:    */           }
/* 431:808 */           zzInput = zzBufferL[(zzCurrentPosL++)];
/* 432:    */         }
/* 433:811 */         int zzNext = zzTransL[(zzRowMapL[this.zzState] + zzCMapL[zzInput])];
/* 434:812 */         if (zzNext == -1) {
/* 435:    */           break;
/* 436:    */         }
/* 437:813 */         this.zzState = zzNext;
/* 438:    */         
/* 439:815 */         int zzAttributes = zzAttrL[this.zzState];
/* 440:816 */         if ((zzAttributes & 0x1) == 1)
/* 441:    */         {
/* 442:817 */           zzAction = this.zzState;
/* 443:818 */           zzMarkedPosL = zzCurrentPosL;
/* 444:819 */           if ((zzAttributes & 0x8) == 8) {
/* 445:    */             break;
/* 446:    */           }
/* 447:    */         }
/* 448:    */       }
/* 449:826 */       this.zzMarkedPos = zzMarkedPosL;
/* 450:828 */       switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction])
/* 451:    */       {
/* 452:    */       case 37: 
/* 453:830 */         return symbol("PRECEDENCE", 20);
/* 454:    */       case 39: 
/* 455:    */         break;
/* 456:    */       case 6: 
/* 457:834 */         return symbol("SEMI", 13);
/* 458:    */       case 40: 
/* 459:    */         break;
/* 460:    */       case 14: 
/* 461:838 */         return symbol("LT", 29);
/* 462:    */       case 41: 
/* 463:    */         break;
/* 464:    */       case 10: 
/* 465:842 */         return symbol("LBRACK", 25);
/* 466:    */       case 42: 
/* 467:    */         break;
/* 468:    */       case 9: 
/* 469:846 */         return symbol("BAR", 19);
/* 470:    */       case 43: 
/* 471:    */         break;
/* 472:    */       case 5: 
/* 473:850 */         return symbol("QESTION", 30);
/* 474:    */       case 44: 
/* 475:    */         break;
/* 476:    */       case 19: 
/* 477:854 */         return symbol("NON", 8);
/* 478:    */       case 45: 
/* 479:    */         break;
/* 480:    */       case 34: 
/* 481:858 */         return symbol("EXTENDS", 32);
/* 482:    */       case 46: 
/* 483:    */         break;
/* 484:    */       case 35: 
/* 485:862 */         return symbol("PARSER", 7);
/* 486:    */       case 47: 
/* 487:    */         break;
/* 488:    */       case 18: 
/* 489:866 */         return symbol("COLON_COLON_EQUALS", 18);
/* 490:    */       case 48: 
/* 491:    */         break;
/* 492:    */       case 16: 
/* 493:870 */         this.sb = new StringBuffer();this.csline = (this.yyline + 1);this.cscolumn = (this.yycolumn + 1);yybegin(2);
/* 494:    */       case 49: 
/* 495:    */         break;
/* 496:    */       case 11: 
/* 497:874 */         return symbol("RBRACK", 26);
/* 498:    */       case 50: 
/* 499:    */         break;
/* 500:    */       case 26: 
/* 501:878 */         return symbol("RIGHT", 22);
/* 502:    */       case 51: 
/* 503:    */         break;
/* 504:    */       case 29: 
/* 505:882 */         return symbol("SUPER", 31);
/* 506:    */       case 52: 
/* 507:    */         break;
/* 508:    */       case 28: 
/* 509:886 */         return symbol("START", 12);
/* 510:    */       case 53: 
/* 511:    */         break;
/* 512:    */       case 32: 
/* 513:890 */         return symbol("IMPORT", 3);
/* 514:    */       case 54: 
/* 515:    */         break;
/* 516:    */       case 12: 
/* 517:894 */         return symbol("COLON", 17);
/* 518:    */       case 55: 
/* 519:    */         break;
/* 520:    */       case 15: 
/* 521:898 */         this.sb.append(yytext());
/* 522:    */       case 56: 
/* 523:    */         break;
/* 524:    */       case 30: 
/* 525:902 */         return symbol("PARSER", 6);
/* 526:    */       case 57: 
/* 527:    */         break;
/* 528:    */       case 31: 
/* 529:906 */         return symbol("ACTION", 5);
/* 530:    */       case 58: 
/* 531:    */         break;
/* 532:    */       case 4: 
/* 533:910 */         return symbol("ID", 34, yytext());
/* 534:    */       case 59: 
/* 535:    */         break;
/* 536:    */       case 13: 
/* 537:914 */         return symbol("GT", 28);
/* 538:    */       case 60: 
/* 539:    */         break;
/* 540:    */       case 8: 
/* 541:918 */         return symbol("DOT", 16);
/* 542:    */       case 61: 
/* 543:    */         break;
/* 544:    */       case 36: 
/* 545:922 */         return symbol("NONASSOC", 23);
/* 546:    */       case 62: 
/* 547:    */         break;
/* 548:    */       case 33: 
/* 549:926 */         return symbol("PACKAGE", 2);
/* 550:    */       case 63: 
/* 551:    */         break;
/* 552:    */       case 27: 
/* 553:930 */         return symbol("CLASS", 33);
/* 554:    */       case 64: 
/* 555:    */         break;
/* 556:    */       case 22: 
/* 557:934 */         return symbol("LEFT", 21);
/* 558:    */       case 65: 
/* 559:    */         break;
/* 560:    */       case 17: 
/* 561:938 */         yybegin(0);return this.symbolFactory.newSymbol("CODE_STRING", 35, new ComplexSymbolFactory.Location(this.csline, this.cscolumn), new ComplexSymbolFactory.Location(this.yyline + 1, this.yycolumn + 1 + yylength()), this.sb.toString());
/* 562:    */       case 66: 
/* 563:    */         break;
/* 564:    */       case 7: 
/* 565:942 */         return symbol("COMMA", 14);
/* 566:    */       case 67: 
/* 567:    */         break;
/* 568:    */       case 23: 
/* 569:946 */         return symbol("SCAN", 10);
/* 570:    */       case 68: 
/* 571:    */         break;
/* 572:    */       case 38: 
/* 573:950 */         return symbol("NONTERMINAL", 27);
/* 574:    */       case 69: 
/* 575:    */         break;
/* 576:    */       case 3: 
/* 577:954 */         return symbol("STAR", 15);
/* 578:    */       case 70: 
/* 579:    */         break;
/* 580:    */       case 21: 
/* 581:958 */         return symbol("INIT", 9);
/* 582:    */       case 71: 
/* 583:    */         break;
/* 584:    */       case 24: 
/* 585:962 */         return symbol("WITH", 11);
/* 586:    */       case 72: 
/* 587:    */         break;
/* 588:    */       case 25: 
/* 589:966 */         return symbol("PERCENT_PREC", 24);
/* 590:    */       case 73: 
/* 591:    */         break;
/* 592:    */       case 1: 
/* 593:970 */         emit_warning("Unrecognized character '" + yytext() + "' -- ignored");
/* 594:    */       case 74: 
/* 595:    */         break;
/* 596:    */       case 20: 
/* 597:974 */         return symbol("CODE", 4);
/* 598:    */       case 75: 
/* 599:    */         break;
/* 600:    */       case 2: 
/* 601:    */       case 76: 
/* 602:    */         break;
/* 603:    */       default: 
/* 604:982 */         if ((zzInput == -1) && (this.zzStartRead == this.zzCurrentPos))
/* 605:    */         {
/* 606:983 */           this.zzAtEOF = true;
/* 607:984 */           zzDoEOF();
/* 608:985 */           return this.symbolFactory.newSymbol("EOF", 0);
/* 609:    */         }
/* 610:989 */         zzScanError(1);
/* 611:    */       }
/* 612:    */     }
/* 613:    */   }
/* 614:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.Lexer
 * JD-Core Version:    0.7.0.1
 */
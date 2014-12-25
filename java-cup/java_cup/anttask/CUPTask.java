/*   1:    */ package java_cup.anttask;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileReader;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import java_cup.Main;
/*  10:    */ import org.apache.tools.ant.BuildException;
/*  11:    */ import org.apache.tools.ant.Task;
/*  12:    */ 
/*  13:    */ public class CUPTask
/*  14:    */   extends Task
/*  15:    */ {
/*  16: 40 */   private String srcfile = null;
/*  17: 41 */   private String parser = null;
/*  18: 42 */   private String _package = null;
/*  19: 43 */   private String symbols = null;
/*  20: 44 */   private String destdir = null;
/*  21: 45 */   private boolean _interface = false;
/*  22: 46 */   private boolean nonterms = false;
/*  23: 47 */   private String expect = null;
/*  24: 48 */   private boolean compact_red = false;
/*  25: 49 */   private boolean nowarn = false;
/*  26: 50 */   private boolean nosummary = false;
/*  27: 51 */   private boolean progress = false;
/*  28: 52 */   private boolean dump_grammar = false;
/*  29: 53 */   private boolean dump_states = false;
/*  30: 54 */   private boolean dump_tables = false;
/*  31: 55 */   private boolean dump = false;
/*  32: 56 */   private boolean time = false;
/*  33: 57 */   private boolean debug = false;
/*  34: 58 */   private boolean nopositions = false;
/*  35: 59 */   private boolean xmlactions = false;
/*  36: 60 */   private boolean genericlabels = false;
/*  37: 61 */   private boolean locations = true;
/*  38: 62 */   private boolean noscanner = false;
/*  39: 63 */   private boolean force = false;
/*  40: 64 */   private boolean quiet = false;
/*  41:    */   
/*  42:    */   public void execute()
/*  43:    */     throws BuildException
/*  44:    */   {
/*  45: 74 */     List sc = new ArrayList();
/*  46: 76 */     if (this.parser != null)
/*  47:    */     {
/*  48: 76 */       sc.add("-parser");sc.add(this.parser);
/*  49:    */     }
/*  50:    */     else
/*  51:    */     {
/*  52: 77 */       this.parser = "parser";
/*  53:    */     }
/*  54: 78 */     if (this._package != null)
/*  55:    */     {
/*  56: 78 */       sc.add("-package");sc.add(this._package);
/*  57:    */     }
/*  58: 79 */     if (this.symbols != null)
/*  59:    */     {
/*  60: 79 */       sc.add("-symbols");sc.add(this.symbols);
/*  61:    */     }
/*  62:    */     else
/*  63:    */     {
/*  64: 80 */       this.symbols = "sym";
/*  65:    */     }
/*  66: 81 */     if (this.expect != null)
/*  67:    */     {
/*  68: 81 */       sc.add("-expect");sc.add(this.expect);
/*  69:    */     }
/*  70: 82 */     if (this._interface) {
/*  71: 82 */       sc.add("-interface");
/*  72:    */     }
/*  73: 83 */     if (this.nonterms) {
/*  74: 83 */       sc.add("-nonterms");
/*  75:    */     }
/*  76: 84 */     if (this.compact_red) {
/*  77: 84 */       sc.add("-compact_red");
/*  78:    */     }
/*  79: 85 */     if (this.nowarn) {
/*  80: 85 */       sc.add("-nowarn");
/*  81:    */     }
/*  82: 86 */     if (this.nosummary) {
/*  83: 86 */       sc.add("-nosummary");
/*  84:    */     }
/*  85: 87 */     if (this.progress) {
/*  86: 87 */       sc.add("-progress");
/*  87:    */     }
/*  88: 88 */     if (this.dump_grammar) {
/*  89: 88 */       sc.add("-dump_grammar");
/*  90:    */     }
/*  91: 89 */     if (this.dump_states) {
/*  92: 89 */       sc.add("-dump_states");
/*  93:    */     }
/*  94: 90 */     if (this.dump_tables) {
/*  95: 90 */       sc.add("-dump_tables");
/*  96:    */     }
/*  97: 91 */     if (this.dump) {
/*  98: 91 */       sc.add("-dump");
/*  99:    */     }
/* 100: 92 */     if (this.time) {
/* 101: 92 */       sc.add("-time");
/* 102:    */     }
/* 103: 93 */     if (this.debug) {
/* 104: 93 */       sc.add("-debug");
/* 105:    */     }
/* 106: 94 */     if (this.nopositions) {
/* 107: 94 */       sc.add("-nopositions");
/* 108:    */     }
/* 109: 95 */     if (this.locations) {
/* 110: 95 */       sc.add("-locations");
/* 111:    */     }
/* 112: 96 */     if (this.genericlabels) {
/* 113: 96 */       sc.add("-genericlabels");
/* 114:    */     }
/* 115: 97 */     if (this.xmlactions) {
/* 116: 97 */       sc.add("-xmlactions");
/* 117:    */     }
/* 118: 98 */     if (this.noscanner) {
/* 119: 98 */       sc.add("-noscanner");
/* 120:    */     }
/* 121: 99 */     if (!this.quiet) {
/* 122: 99 */       log("This is CUP v0.11b 20140808 (SVN rev 54)");
/* 123:    */     }
/* 124:100 */     if (!this.quiet) {
/* 125:100 */       log("Authors : Scott E. Hudson, Frank Flannery, Michael Petter and C. Scott Ananian");
/* 126:    */     }
/* 127:101 */     if (!this.quiet) {
/* 128:101 */       log("Bugreports to petter@cs.tum.edu");
/* 129:    */     }
/* 130:104 */     String packagename = inspect(this.srcfile);
/* 131:108 */     if (this.destdir == null)
/* 132:    */     {
/* 133:109 */       this.destdir = System.getProperty("user.dir");
/* 134:110 */       if (!this.quiet) {
/* 135:110 */         log("No destination directory specified; using working directory: " + this.destdir);
/* 136:    */       }
/* 137:    */     }
/* 138:112 */     File dest = new File(this.destdir + packagename);
/* 139:113 */     if (!dest.exists())
/* 140:    */     {
/* 141:114 */       if (!this.quiet) {
/* 142:114 */         log("Destination directory didn't exist; creating new one: " + this.destdir + packagename);
/* 143:    */       }
/* 144:115 */       dest.mkdirs();
/* 145:116 */       this.force = true;
/* 146:    */     }
/* 147:    */     else
/* 148:    */     {
/* 149:119 */       if ((this.force) && (!this.quiet)) {
/* 150:119 */         log("anyway, this generation will be processed because of option force set to \"true\"");
/* 151:120 */       } else if (!this.quiet) {
/* 152:120 */         log("checking, whether this run is necessary");
/* 153:    */       }
/* 154:122 */       File parserfile = new File(this.destdir + packagename, this.parser + ".java");
/* 155:123 */       File symfile = new File(this.destdir + packagename, this.symbols + ".java");
/* 156:124 */       File cupfile = new File(this.srcfile);
/* 157:126 */       if ((!parserfile.exists()) || (!symfile.exists()))
/* 158:    */       {
/* 159:127 */         if (!this.quiet) {
/* 160:127 */           log("Either Parserfile or Symbolfile didn't exist");
/* 161:    */         }
/* 162:128 */         this.force = true;
/* 163:    */       }
/* 164:129 */       else if (!this.quiet)
/* 165:    */       {
/* 166:129 */         log("Parserfile and symbolfile are existing");
/* 167:    */       }
/* 168:132 */       if (parserfile.lastModified() <= cupfile.lastModified())
/* 169:    */       {
/* 170:133 */         if (!this.quiet) {
/* 171:133 */           log("Parserfile " + parserfile + " isn't actual");
/* 172:    */         }
/* 173:134 */         this.force = true;
/* 174:    */       }
/* 175:135 */       else if (!this.quiet)
/* 176:    */       {
/* 177:135 */         log("Parserfile " + parserfile + " is actual");
/* 178:    */       }
/* 179:137 */       if (symfile.lastModified() <= cupfile.lastModified())
/* 180:    */       {
/* 181:138 */         if (!this.quiet) {
/* 182:138 */           log("Symbolfile " + symfile + " isn't actual");
/* 183:    */         }
/* 184:139 */         this.force = true;
/* 185:    */       }
/* 186:140 */       else if (!this.quiet)
/* 187:    */       {
/* 188:140 */         log("Symbolfile" + symfile + " is actual");
/* 189:    */       }
/* 190:143 */       if (!this.force)
/* 191:    */       {
/* 192:144 */         if (!this.quiet) {
/* 193:144 */           log("skipping generation of " + this.srcfile);
/* 194:    */         }
/* 195:145 */         if (!this.quiet) {
/* 196:145 */           log("use option force=\"true\" to override");
/* 197:    */         }
/* 198:146 */         return;
/* 199:    */       }
/* 200:    */     }
/* 201:150 */     sc.add("-destdir");
/* 202:151 */     sc.add(dest.getAbsolutePath());
/* 203:154 */     if (this.srcfile == null) {
/* 204:154 */       throw new BuildException("Input file needed: Specify <cup srcfile=\"myfile.cup\"> ");
/* 205:    */     }
/* 206:155 */     if (!new File(this.srcfile).exists()) {
/* 207:155 */       throw new BuildException("Input file not found: srcfile=\"" + this.srcfile + "\" ");
/* 208:    */     }
/* 209:157 */     sc.add(this.srcfile);
/* 210:158 */     String[] args = new String[sc.size()];
/* 211:159 */     for (int i = 0; i < args.length; i++) {
/* 212:159 */       args[i] = ((String)sc.get(i));
/* 213:    */     }
/* 214:    */     try
/* 215:    */     {
/* 216:163 */       Main.main(args);
/* 217:    */     }
/* 218:    */     catch (Exception e)
/* 219:    */     {
/* 220:165 */       log("CUP error occured int CUP task: " + e);
/* 221:    */     }
/* 222:    */   }
/* 223:    */   
/* 224:    */   protected String inspect(String cupfile)
/* 225:    */   {
/* 226:    */     try
/* 227:    */     {
/* 228:179 */       BufferedReader br = new BufferedReader(new FileReader(cupfile));
/* 229:180 */       while (br.ready())
/* 230:    */       {
/* 231:181 */         String line = br.readLine();
/* 232:182 */         if ((line.startsWith("package")) && (line.indexOf(";") != -1))
/* 233:    */         {
/* 234:184 */           String result = line.substring(8, line.indexOf(";"));
/* 235:185 */           result = result.replace('.', System.getProperty("file.separator").charAt(0));
/* 236:186 */           return System.getProperty("file.separator") + result;
/* 237:    */         }
/* 238:    */       }
/* 239:    */     }
/* 240:    */     catch (IOException ioe) {}
/* 241:192 */     return "";
/* 242:    */   }
/* 243:    */   
/* 244:    */   public boolean getQuiet()
/* 245:    */   {
/* 246:201 */     return this.quiet;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setQuiet(boolean argquiet)
/* 250:    */   {
/* 251:210 */     this.quiet = argquiet;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public boolean getForce()
/* 255:    */   {
/* 256:218 */     return this.force;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setForce(boolean argforce)
/* 260:    */   {
/* 261:227 */     this.force = argforce;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public String getPackage()
/* 265:    */   {
/* 266:235 */     return this._package;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setPackage(String arg_package)
/* 270:    */   {
/* 271:244 */     this._package = arg_package;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public String getDestdir()
/* 275:    */   {
/* 276:253 */     return this.destdir;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setDestdir(String destdir)
/* 280:    */   {
/* 281:262 */     this.destdir = destdir;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public boolean isInterface()
/* 285:    */   {
/* 286:271 */     return this._interface;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setInterface(boolean arg_interface)
/* 290:    */   {
/* 291:280 */     this._interface = arg_interface;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public String getSrcfile()
/* 295:    */   {
/* 296:288 */     return this.srcfile;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setSrcfile(String newSrcfile)
/* 300:    */   {
/* 301:296 */     this.srcfile = newSrcfile;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public String getParser()
/* 305:    */   {
/* 306:307 */     return this.parser;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setParser(String argParser)
/* 310:    */   {
/* 311:316 */     this.parser = argParser;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public String getSymbols()
/* 315:    */   {
/* 316:325 */     return this.symbols;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setSymbols(String argSymbols)
/* 320:    */   {
/* 321:334 */     this.symbols = argSymbols;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public boolean isNonterms()
/* 325:    */   {
/* 326:343 */     return this.nonterms;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setNonterms(boolean argNonterms)
/* 330:    */   {
/* 331:352 */     this.nonterms = argNonterms;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public String getExpect()
/* 335:    */   {
/* 336:361 */     return this.expect;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setExpect(String argExpect)
/* 340:    */   {
/* 341:370 */     this.expect = argExpect;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public boolean isCompact_red()
/* 345:    */   {
/* 346:379 */     return this.compact_red;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void setCompact_red(boolean argCompact_red)
/* 350:    */   {
/* 351:388 */     this.compact_red = argCompact_red;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public boolean isNowarn()
/* 355:    */   {
/* 356:397 */     return this.nowarn;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void setNowarn(boolean argNowarn)
/* 360:    */   {
/* 361:406 */     this.nowarn = argNowarn;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public boolean isNosummary()
/* 365:    */   {
/* 366:415 */     return this.nosummary;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setNosummary(boolean argNosummary)
/* 370:    */   {
/* 371:424 */     this.nosummary = argNosummary;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public boolean isProgress()
/* 375:    */   {
/* 376:433 */     return this.progress;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setProgress(boolean argProgress)
/* 380:    */   {
/* 381:442 */     this.progress = argProgress;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public boolean isDump_grammar()
/* 385:    */   {
/* 386:451 */     return this.dump_grammar;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void setDump_grammar(boolean argDump_grammar)
/* 390:    */   {
/* 391:460 */     this.dump_grammar = argDump_grammar;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public boolean isDump_states()
/* 395:    */   {
/* 396:469 */     return this.dump_states;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public void setDump_states(boolean argDump_states)
/* 400:    */   {
/* 401:478 */     this.dump_states = argDump_states;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public boolean isDump_tables()
/* 405:    */   {
/* 406:487 */     return this.dump_tables;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public void setDump_tables(boolean argDump_tables)
/* 410:    */   {
/* 411:496 */     this.dump_tables = argDump_tables;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public boolean isDump()
/* 415:    */   {
/* 416:505 */     return this.dump;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public void setDump(boolean argDump)
/* 420:    */   {
/* 421:514 */     this.dump = argDump;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public boolean isTime()
/* 425:    */   {
/* 426:523 */     return this.time;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public void setTime(boolean argTime)
/* 430:    */   {
/* 431:532 */     this.time = argTime;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public boolean isDebug()
/* 435:    */   {
/* 436:541 */     return this.debug;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public void setDebug(boolean argDebug)
/* 440:    */   {
/* 441:550 */     this.debug = argDebug;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public boolean isNopositions()
/* 445:    */   {
/* 446:559 */     return this.nopositions;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void setNopositions(boolean argNopositions)
/* 450:    */   {
/* 451:568 */     this.nopositions = argNopositions;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public boolean isLocations()
/* 455:    */   {
/* 456:576 */     return this.locations;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public void setLocations(boolean argLocations)
/* 460:    */   {
/* 461:585 */     this.locations = argLocations;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public boolean isNoscanner()
/* 465:    */   {
/* 466:594 */     return this.noscanner;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public void setNoscanner(boolean argNoscanner)
/* 470:    */   {
/* 471:603 */     this.noscanner = argNoscanner;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public boolean isXmlactions()
/* 475:    */   {
/* 476:607 */     return this.xmlactions;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public void setXmlactions(boolean xmlactions)
/* 480:    */   {
/* 481:611 */     this.xmlactions = xmlactions;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public boolean isGenericlabels()
/* 485:    */   {
/* 486:615 */     return this.genericlabels;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public void setGenericlabels(boolean genericlabels)
/* 490:    */   {
/* 491:619 */     this.genericlabels = genericlabels;
/* 492:    */   }
/* 493:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.anttask.CUPTask
 * JD-Core Version:    0.7.0.1
 */
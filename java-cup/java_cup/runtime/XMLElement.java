/*   1:    */ package java_cup.runtime;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.LinkedList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.xml.stream.XMLStreamException;
/*   8:    */ import javax.xml.stream.XMLStreamWriter;
/*   9:    */ 
/*  10:    */ public abstract class XMLElement
/*  11:    */ {
/*  12:    */   protected String tagname;
/*  13:    */   
/*  14:    */   public abstract List<XMLElement> selectById(String paramString);
/*  15:    */   
/*  16:    */   public static void dump(XMLStreamWriter writer, XMLElement elem, String... blacklist)
/*  17:    */     throws XMLStreamException
/*  18:    */   {
/*  19: 18 */     dump(null, writer, elem, blacklist);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static void dump(ScannerBuffer buffer, XMLStreamWriter writer, XMLElement elem, String... blacklist)
/*  23:    */     throws XMLStreamException
/*  24:    */   {
/*  25: 21 */     writer.writeStartDocument("utf-8", "1.0");
/*  26: 22 */     writer.writeProcessingInstruction("xml-stylesheet", "href=\"tree.xsl\" type=\"text/xsl\"");
/*  27: 23 */     writer.writeStartElement("document");
/*  28: 25 */     if (blacklist.length > 0)
/*  29:    */     {
/*  30: 26 */       writer.writeStartElement("blacklist");
/*  31: 27 */       for (String s : blacklist)
/*  32:    */       {
/*  33: 28 */         writer.writeStartElement("symbol");
/*  34: 29 */         writer.writeCharacters(s);
/*  35: 30 */         writer.writeEndElement();
/*  36:    */       }
/*  37: 32 */       writer.writeEndElement();
/*  38:    */     }
/*  39: 35 */     writer.writeStartElement("parsetree");
/*  40: 36 */     elem.dump(writer);
/*  41: 37 */     writer.writeEndElement();
/*  42: 39 */     if (buffer != null)
/*  43:    */     {
/*  44: 40 */       writer.writeStartElement("tokensequence");
/*  45: 41 */       for (Symbol s : buffer.getBuffered()) {
/*  46: 42 */         if ((s instanceof ComplexSymbolFactory.ComplexSymbol))
/*  47:    */         {
/*  48: 43 */           ComplexSymbolFactory.ComplexSymbol cs = (ComplexSymbolFactory.ComplexSymbol)s;
/*  49: 44 */           if (cs.value != null)
/*  50:    */           {
/*  51: 45 */             writer.writeStartElement("token");
/*  52: 46 */             writer.writeAttribute("name", cs.getName());
/*  53: 47 */             cs.getLeft().toXML(writer, "left");
/*  54: 48 */             writer.writeCharacters(cs.value + "");
/*  55: 49 */             cs.getRight().toXML(writer, "right");
/*  56: 50 */             writer.writeEndElement();
/*  57:    */           }
/*  58:    */           else
/*  59:    */           {
/*  60: 53 */             writer.writeStartElement("keyword");
/*  61: 54 */             writer.writeAttribute("left", cs.getLeft() + "");
/*  62: 55 */             writer.writeAttribute("right", cs.getRight() + "");
/*  63: 56 */             writer.writeCharacters(cs.getName() + "");
/*  64: 57 */             writer.writeEndElement();
/*  65:    */           }
/*  66:    */         }
/*  67: 61 */         else if ((s instanceof Symbol))
/*  68:    */         {
/*  69: 62 */           writer.writeStartElement("token");
/*  70: 63 */           writer.writeCharacters(s.toString());
/*  71: 64 */           writer.writeEndElement();
/*  72:    */         }
/*  73:    */       }
/*  74: 67 */       writer.writeEndElement();
/*  75:    */     }
/*  76: 69 */     writer.writeEndElement();
/*  77: 70 */     writer.writeEndDocument();
/*  78: 71 */     writer.flush();
/*  79: 72 */     writer.close();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public abstract ComplexSymbolFactory.Location right();
/*  83:    */   
/*  84:    */   public abstract ComplexSymbolFactory.Location left();
/*  85:    */   
/*  86:    */   protected abstract void dump(XMLStreamWriter paramXMLStreamWriter)
/*  87:    */     throws XMLStreamException;
/*  88:    */   
/*  89:    */   public static class NonTerminal
/*  90:    */     extends XMLElement
/*  91:    */   {
/*  92:    */     private int variant;
/*  93:    */     LinkedList<XMLElement> list;
/*  94:    */     
/*  95:    */     public List<XMLElement> selectById(String s)
/*  96:    */     {
/*  97: 82 */       LinkedList<XMLElement> response = new LinkedList();
/*  98: 83 */       if (this.tagname.equals(s)) {
/*  99: 84 */         response.add(this);
/* 100:    */       }
/* 101: 85 */       for (XMLElement e : this.list)
/* 102:    */       {
/* 103: 86 */         List<XMLElement> selection = e.selectById(s);
/* 104: 87 */         response.addAll(selection);
/* 105:    */       }
/* 106: 89 */       return response;
/* 107:    */     }
/* 108:    */     
/* 109:    */     public NonTerminal(String tagname, int variant, XMLElement... l)
/* 110:    */     {
/* 111: 94 */       this.tagname = tagname;
/* 112: 95 */       this.variant = variant;
/* 113: 96 */       this.list = new LinkedList(Arrays.asList(l));
/* 114:    */     }
/* 115:    */     
/* 116:    */     public ComplexSymbolFactory.Location left()
/* 117:    */     {
/* 118:100 */       for (XMLElement e : this.list)
/* 119:    */       {
/* 120:101 */         ComplexSymbolFactory.Location loc = e.left();
/* 121:102 */         if (loc != null) {
/* 122:102 */           return loc;
/* 123:    */         }
/* 124:    */       }
/* 125:104 */       return null;
/* 126:    */     }
/* 127:    */     
/* 128:    */     public ComplexSymbolFactory.Location right()
/* 129:    */     {
/* 130:107 */       for (Iterator<XMLElement> it = this.list.descendingIterator(); it.hasNext();)
/* 131:    */       {
/* 132:108 */         ComplexSymbolFactory.Location loc = ((XMLElement)it.next()).left();
/* 133:109 */         if (loc != null) {
/* 134:109 */           return loc;
/* 135:    */         }
/* 136:    */       }
/* 137:111 */       return null;
/* 138:    */     }
/* 139:    */     
/* 140:    */     public String toString()
/* 141:    */     {
/* 142:115 */       if (this.list.isEmpty()) {
/* 143:116 */         return "<nonterminal id=\"" + this.tagname + "\" variant=\"" + this.variant + "\" />";
/* 144:    */       }
/* 145:118 */       String ret = "<nonterminal id=\"" + this.tagname + "\" left=\"" + left() + "\" right=\"" + right() + "\" variant=\"" + this.variant + "\">";
/* 146:120 */       for (XMLElement e : this.list) {
/* 147:121 */         ret = ret + e.toString();
/* 148:    */       }
/* 149:122 */       return ret + "</nonterminal>";
/* 150:    */     }
/* 151:    */     
/* 152:    */     protected void dump(XMLStreamWriter writer)
/* 153:    */       throws XMLStreamException
/* 154:    */     {
/* 155:126 */       writer.writeStartElement("nonterminal");
/* 156:127 */       writer.writeAttribute("id", this.tagname);
/* 157:128 */       writer.writeAttribute("variant", this.variant + "");
/* 158:    */       
/* 159:130 */       ComplexSymbolFactory.Location loc = left();
/* 160:131 */       if (loc != null) {
/* 161:131 */         loc.toXML(writer, "left");
/* 162:    */       }
/* 163:133 */       for (XMLElement e : this.list) {
/* 164:134 */         e.dump(writer);
/* 165:    */       }
/* 166:135 */       loc = right();
/* 167:136 */       if (loc != null) {
/* 168:136 */         loc.toXML(writer, "right");
/* 169:    */       }
/* 170:137 */       writer.writeEndElement();
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public static class Error
/* 175:    */     extends XMLElement
/* 176:    */   {
/* 177:    */     ComplexSymbolFactory.Location l;
/* 178:    */     ComplexSymbolFactory.Location r;
/* 179:    */     
/* 180:    */     public List<XMLElement> selectById(String s)
/* 181:    */     {
/* 182:144 */       return new LinkedList();
/* 183:    */     }
/* 184:    */     
/* 185:    */     public Error(ComplexSymbolFactory.Location l, ComplexSymbolFactory.Location r)
/* 186:    */     {
/* 187:148 */       this.l = l;
/* 188:149 */       this.r = r;
/* 189:    */     }
/* 190:    */     
/* 191:    */     public ComplexSymbolFactory.Location left()
/* 192:    */     {
/* 193:151 */       return this.l;
/* 194:    */     }
/* 195:    */     
/* 196:    */     public ComplexSymbolFactory.Location right()
/* 197:    */     {
/* 198:152 */       return this.r;
/* 199:    */     }
/* 200:    */     
/* 201:    */     public String toString()
/* 202:    */     {
/* 203:155 */       return "<error left=\"" + this.l + "\" right=\"" + this.r + "\"/>";
/* 204:    */     }
/* 205:    */     
/* 206:    */     protected void dump(XMLStreamWriter writer)
/* 207:    */       throws XMLStreamException
/* 208:    */     {
/* 209:159 */       writer.writeStartElement("error");
/* 210:160 */       writer.writeAttribute("left", left() + "");
/* 211:161 */       writer.writeAttribute("right", right() + "");
/* 212:162 */       writer.writeEndElement();
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   public static class Terminal
/* 217:    */     extends XMLElement
/* 218:    */   {
/* 219:    */     ComplexSymbolFactory.Location l;
/* 220:    */     ComplexSymbolFactory.Location r;
/* 221:    */     Object value;
/* 222:    */     
/* 223:    */     public List<XMLElement> selectById(String s)
/* 224:    */     {
/* 225:168 */       List<XMLElement> ret = new LinkedList();
/* 226:169 */       if (this.tagname.equals(s)) {
/* 227:169 */         ret.add(this);
/* 228:    */       }
/* 229:170 */       return ret;
/* 230:    */     }
/* 231:    */     
/* 232:    */     public Terminal(ComplexSymbolFactory.Location l, String symbolname, ComplexSymbolFactory.Location r)
/* 233:    */     {
/* 234:176 */       this(l, symbolname, null, r);
/* 235:    */     }
/* 236:    */     
/* 237:    */     public Terminal(ComplexSymbolFactory.Location l, String symbolname, Object i, ComplexSymbolFactory.Location r)
/* 238:    */     {
/* 239:180 */       this.l = l;
/* 240:181 */       this.r = r;
/* 241:182 */       this.value = i;
/* 242:183 */       this.tagname = symbolname;
/* 243:    */     }
/* 244:    */     
/* 245:    */     public Object value()
/* 246:    */     {
/* 247:186 */       return this.value;
/* 248:    */     }
/* 249:    */     
/* 250:    */     public ComplexSymbolFactory.Location left()
/* 251:    */     {
/* 252:187 */       return this.l;
/* 253:    */     }
/* 254:    */     
/* 255:    */     public ComplexSymbolFactory.Location right()
/* 256:    */     {
/* 257:188 */       return this.r;
/* 258:    */     }
/* 259:    */     
/* 260:    */     public String toString()
/* 261:    */     {
/* 262:191 */       return "<terminal id=\"" + this.tagname + "\" left=\"" + this.l + "\" right=\"" + this.r + "\">" + this.value + "</terminal>";
/* 263:    */     }
/* 264:    */     
/* 265:    */     protected void dump(XMLStreamWriter writer)
/* 266:    */       throws XMLStreamException
/* 267:    */     {
/* 268:198 */       writer.writeStartElement("terminal");
/* 269:199 */       writer.writeAttribute("id", this.tagname);
/* 270:200 */       writer.writeAttribute("left", left() + "");
/* 271:201 */       writer.writeAttribute("right", right() + "");
/* 272:202 */       if (this.value != null) {
/* 273:202 */         writer.writeCharacters(this.value + "");
/* 274:    */       }
/* 275:203 */       writer.writeEndElement();
/* 276:    */     }
/* 277:    */   }
/* 278:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.runtime.XMLElement
 * JD-Core Version:    0.7.0.1
 */
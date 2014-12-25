/*   1:    */ package java_cup.runtime;
/*   2:    */ 
/*   3:    */ import javax.xml.stream.XMLStreamException;
/*   4:    */ import javax.xml.stream.XMLStreamWriter;
/*   5:    */ 
/*   6:    */ public class ComplexSymbolFactory
/*   7:    */   implements SymbolFactory
/*   8:    */ {
/*   9:    */   public static class Location
/*  10:    */   {
/*  11: 20 */     private String unit = "unknown";
/*  12:    */     private int line;
/*  13:    */     private int column;
/*  14: 21 */     private int offset = -1;
/*  15:    */     
/*  16:    */     public Location(String unit, int line, int column, int offset)
/*  17:    */     {
/*  18: 31 */       this(unit, line, column);
/*  19: 32 */       this.offset = offset;
/*  20:    */     }
/*  21:    */     
/*  22:    */     public Location(String unit, int line, int column)
/*  23:    */     {
/*  24: 42 */       this.unit = unit;
/*  25: 43 */       this.line = line;
/*  26: 44 */       this.column = column;
/*  27:    */     }
/*  28:    */     
/*  29:    */     public Location(int line, int column, int offset)
/*  30:    */     {
/*  31: 54 */       this(line, column);
/*  32: 55 */       this.offset = offset;
/*  33:    */     }
/*  34:    */     
/*  35:    */     public Location(int line, int column)
/*  36:    */     {
/*  37: 64 */       this.line = line;
/*  38: 65 */       this.column = column;
/*  39:    */     }
/*  40:    */     
/*  41:    */     public int getColumn()
/*  42:    */     {
/*  43: 72 */       return this.column;
/*  44:    */     }
/*  45:    */     
/*  46:    */     public int getLine()
/*  47:    */     {
/*  48: 79 */       return this.line;
/*  49:    */     }
/*  50:    */     
/*  51:    */     public String getUnit()
/*  52:    */     {
/*  53: 86 */       return this.unit;
/*  54:    */     }
/*  55:    */     
/*  56:    */     public String toString()
/*  57:    */     {
/*  58: 93 */       return getUnit() + ":" + getLine() + "/" + getColumn() + "(" + this.offset + ")";
/*  59:    */     }
/*  60:    */     
/*  61:    */     public void toXML(XMLStreamWriter writer, String orientation)
/*  62:    */       throws XMLStreamException
/*  63:    */     {
/*  64:102 */       writer.writeStartElement("location");
/*  65:103 */       writer.writeAttribute("compilationunit", this.unit);
/*  66:104 */       writer.writeAttribute("orientation", orientation);
/*  67:105 */       writer.writeAttribute("linenumber", this.line + "");
/*  68:106 */       writer.writeAttribute("columnnumber", this.column + "");
/*  69:107 */       writer.writeAttribute("offset", this.offset + "");
/*  70:108 */       writer.writeEndElement();
/*  71:    */     }
/*  72:    */     
/*  73:    */     public int getOffset()
/*  74:    */     {
/*  75:115 */       return this.offset;
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static class ComplexSymbol
/*  80:    */     extends Symbol
/*  81:    */   {
/*  82:    */     protected String name;
/*  83:    */     public ComplexSymbolFactory.Location xleft;
/*  84:    */     public ComplexSymbolFactory.Location xright;
/*  85:    */     
/*  86:    */     public ComplexSymbol(String name, int id)
/*  87:    */     {
/*  88:125 */       super();
/*  89:126 */       this.name = name;
/*  90:    */     }
/*  91:    */     
/*  92:    */     public ComplexSymbol(String name, int id, Object value)
/*  93:    */     {
/*  94:129 */       super(value);
/*  95:130 */       this.name = name;
/*  96:    */     }
/*  97:    */     
/*  98:    */     public String toString()
/*  99:    */     {
/* 100:133 */       if ((this.xleft == null) || (this.xright == null)) {
/* 101:133 */         return "Symbol: " + this.name;
/* 102:    */       }
/* 103:134 */       return "Symbol: " + this.name + " (" + this.xleft + " - " + this.xright + ")";
/* 104:    */     }
/* 105:    */     
/* 106:    */     public String getName()
/* 107:    */     {
/* 108:137 */       return this.name;
/* 109:    */     }
/* 110:    */     
/* 111:    */     public ComplexSymbol(String name, int id, int state)
/* 112:    */     {
/* 113:140 */       super(state);
/* 114:141 */       this.name = name;
/* 115:    */     }
/* 116:    */     
/* 117:    */     public ComplexSymbol(String name, int id, Symbol left, Symbol right)
/* 118:    */     {
/* 119:144 */       super(left, right);
/* 120:145 */       this.name = name;
/* 121:146 */       if (left != null) {
/* 122:146 */         this.xleft = ((ComplexSymbol)left).xleft;
/* 123:    */       }
/* 124:147 */       if (right != null) {
/* 125:147 */         this.xright = ((ComplexSymbol)right).xright;
/* 126:    */       }
/* 127:    */     }
/* 128:    */     
/* 129:    */     public ComplexSymbol(String name, int id, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right)
/* 130:    */     {
/* 131:150 */       super(ComplexSymbolFactory.Location.access$000(left), ComplexSymbolFactory.Location.access$000(right));
/* 132:151 */       this.name = name;
/* 133:152 */       this.xleft = left;
/* 134:153 */       this.xright = right;
/* 135:    */     }
/* 136:    */     
/* 137:    */     public ComplexSymbol(String name, int id, Symbol left, Symbol right, Object value)
/* 138:    */     {
/* 139:156 */       super(left.left, right.right, value);
/* 140:157 */       this.name = name;
/* 141:158 */       if (left != null) {
/* 142:158 */         this.xleft = ((ComplexSymbol)left).xleft;
/* 143:    */       }
/* 144:159 */       if (right != null) {
/* 145:159 */         this.xright = ((ComplexSymbol)right).xright;
/* 146:    */       }
/* 147:    */     }
/* 148:    */     
/* 149:    */     public ComplexSymbol(String name, int id, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right, Object value)
/* 150:    */     {
/* 151:162 */       super(ComplexSymbolFactory.Location.access$000(left), ComplexSymbolFactory.Location.access$000(right), value);
/* 152:163 */       this.name = name;
/* 153:164 */       this.xleft = left;
/* 154:165 */       this.xright = right;
/* 155:    */     }
/* 156:    */     
/* 157:    */     public ComplexSymbolFactory.Location getLeft()
/* 158:    */     {
/* 159:168 */       return this.xleft;
/* 160:    */     }
/* 161:    */     
/* 162:    */     public ComplexSymbolFactory.Location getRight()
/* 163:    */     {
/* 164:171 */       return this.xright;
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Symbol newSymbol(String name, int id, Location left, Location right, Object value)
/* 169:    */   {
/* 170:183 */     return new ComplexSymbol(name, id, left, right, value);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Symbol newSymbol(String name, int id, Location left, Location right)
/* 174:    */   {
/* 175:191 */     return new ComplexSymbol(name, id, left, right);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Symbol newSymbol(String name, int id, Symbol left, Symbol right, Object value)
/* 179:    */   {
/* 180:194 */     return new ComplexSymbol(name, id, left, right, value);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Symbol newSymbol(String name, int id, Symbol left, Symbol right)
/* 184:    */   {
/* 185:197 */     return new ComplexSymbol(name, id, left, right);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public Symbol newSymbol(String name, int id)
/* 189:    */   {
/* 190:200 */     return new ComplexSymbol(name, id);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Symbol newSymbol(String name, int id, Object value)
/* 194:    */   {
/* 195:203 */     return new ComplexSymbol(name, id, value);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Symbol startSymbol(String name, int id, int state)
/* 199:    */   {
/* 200:206 */     return new ComplexSymbol(name, id, state);
/* 201:    */   }
/* 202:    */ }


/* Location:           E:\TAU\Year3\Compiler\Project\PA02\java-cup\java-cup-11b.jar
 * Qualified Name:     java_cup.runtime.ComplexSymbolFactory
 * JD-Core Version:    0.7.0.1
 */
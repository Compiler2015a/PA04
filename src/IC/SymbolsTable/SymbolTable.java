package IC.SymbolsTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {
	  /** map from String to Symbol **/	  
	  
	  private String id;
	  private SymbolTableTypes tableType;
	  
	  private Map<String,SymbolEntry> entries;
	  private SymbolTable parentSymbolTable;

	  private Map<String, SymbolTable> children;
	  
	  private List<SymbolEntry> sorted_entries; // is used for prints in printTable() method
	  private List<SymbolTable> sorted_children; // is used for prints in printTable() method
	  
	  private Map<IDSymbolsKinds, Integer> entryKindsCounter;
	  
	  public SymbolTable(String id, SymbolTableTypes tableType) {
	    this.id = id;
	    this.tableType = tableType;
	    this.entries = new HashMap<String,SymbolEntry>();
	    this.children = new HashMap<String, SymbolTable>();
	    
	    this.sorted_entries = new ArrayList<SymbolEntry>();
	    this.sorted_children = new ArrayList<SymbolTable>();
	    
	    this.entryKindsCounter = generateKindsCounter();
	    this.parentSymbolTable = null;
	  }


	  public String getId() {
		return id;
	  }
	  
	  public Map<String,SymbolEntry> getEntries()
	  {
		  return this.entries;
	  }
	  
	  public SymbolTableTypes getTableType() {
		return tableType;
	  }
	  
	  public SymbolTable getClassScope(String className) {
			if (id.equals(className)) 
			{
				return this;
			}
			
			for (SymbolTable subTable : children.values()) {
				SymbolTable table = subTable.getClassScope(className);
				if (table != null)
					return table;
			}
			
			return null;
		}
	  
	  public void addEntry(String key, SymbolEntry entry) {
		  this.entryKindsCounter.put(entry.getKind(), entryKindsCounter.get(entry.getKind()) + 1);
		  entry.setGlobalId(entry.getKind().getShortRepr() + 
				  entryKindsCounter.get(entry.getKind()).toString() + "_" + entry.getId());
		  this.entries.put(key, entry);
		  this.sorted_entries.add(entry);
	  }
	  
	  public Boolean hasEntry(String key) {
		  return this.entries.containsKey(key);
	  }
	  
	  public SymbolEntry getEntry(String key) {
		  return this.entries.get(key);
	  }
	  
	  public void addTableChild(String key, SymbolTable symbolTable) {
		  this.children.put(key, symbolTable);
		  this.sorted_children.add(symbolTable);
	  }
	  
	  public void printTable() {
		  if (this.tableType != SymbolTableTypes.STATEMENT_BLOCK)
			  System.out.println(tableType.toString() + " Symbol Table: " + id);
		  else
			  System.out.println(tableType.toString() + " Symbol Table ( located in " + 
					  this.parentSymbolTable.toString() + " )");
		  
		  for (int i = 0; i < sorted_entries.size(); i++) 
			  System.out.println("    " +  sorted_entries.get(i).toString());
		  
		  if (sorted_children.size() > 0) {
			  if (this.tableType == SymbolTableTypes.CLASS)
				  moveClassChildrenToEnd();
			  System.out.print("Children tables: ");
			  Boolean isFirstChild = true;
			  for (int i = 0; i < sorted_children.size(); i++)  {
				  if (!isFirstChild)
					 System.out.print(", ");
				  else
					  isFirstChild = false;
				  System.out.print(sorted_children.get(i).toString()); 
			  }
			  System.out.println();
		  }
		  
		  System.out.println();
		  for (int i = 0; i < sorted_children.size(); i++)
			  sorted_children.get(i).printTable();
	  }
	  
	  public SymbolTable findChildSymbolTable(String id) {
		  return findChildSymbolTableRecursive(this, id);
	  }
	  
	  public SymbolTable getParentSymbolTable() {
		  return parentSymbolTable;
	  }

	  public void setParentSymbolTable(SymbolTable parentSymbolTable) {
		  this.parentSymbolTable = parentSymbolTable;
	  }
	  
	  @Override
	  public String toString() {
		  if (this.tableType != SymbolTableTypes.STATEMENT_BLOCK)
			  return id;
		  else
			  return "statement block in " + parentSymbolTable.toString();
	  }
	  
	  private Map<IDSymbolsKinds, Integer> generateKindsCounter() {
		  Map<IDSymbolsKinds, Integer> entryKindsCounter = new HashMap<IDSymbolsKinds, Integer>();
		  entryKindsCounter.put(IDSymbolsKinds.CLASS, 0);
		  entryKindsCounter.put(IDSymbolsKinds.FIELD, 0);
		  entryKindsCounter.put(IDSymbolsKinds.FORMAL, 0);
		  entryKindsCounter.put(IDSymbolsKinds.STATIC_METHOD, 0);
		  entryKindsCounter.put(IDSymbolsKinds.VIRTUAL_METHOD, 0);
		  entryKindsCounter.put(IDSymbolsKinds.VARIABLE, 0);
		  return entryKindsCounter;
	  }
	  private SymbolTable findChildSymbolTableRecursive(SymbolTable root, String id) {
		  if (root.children.containsKey(id))
			  return root.children.get(id);

		  for (String tableID : root.children.keySet()) {
			  SymbolTable result = findChildSymbolTableRecursive(
						root.children.get(tableID), id);
				  if (result != null)
					  return result;
		  }

		  return null;
	  }
	  
	  private void moveClassChildrenToEnd() {
		  int classesCounter = 0;
		  for (int i = 0; i < sorted_children.size() - classesCounter; i++) {
			  if (sorted_children.get(i).tableType == SymbolTableTypes.CLASS) {
				  SymbolTable clsSymbolTable = sorted_children.get(i);
				  sorted_children.remove(clsSymbolTable);
				  sorted_children.add(clsSymbolTable);
				  classesCounter++;
			  }
		  }
	  }
}






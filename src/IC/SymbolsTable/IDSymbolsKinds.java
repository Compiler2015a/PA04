package IC.SymbolsTable;

public enum IDSymbolsKinds {
	 //Class, Method, Variable, Field;
	
	CLASS("Class"),
	STATIC_METHOD("Static method"),
	VIRTUAL_METHOD("Virtual method"),
	VARIABLE("Local variable"),
	FORMAL("Parameter"),
	FIELD("Field");
	
	private final String repr;       

	private IDSymbolsKinds(String s) {
			repr = s;
	}
	
	public Boolean isMethodKind() {
		return ((this == STATIC_METHOD) || (this == VIRTUAL_METHOD));
	}
	
	public String toString(){
		    return repr;
	}
}
	
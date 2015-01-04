package IC.lir;

public enum CommonLabels {
	TRUE_LABEL("_true_label"), FALSE_LABEL("_false_label"), 
	TEST_LABEL("_test_label"), END_LABEL("_end_label");
	
	private String description;
	
	private CommonLabels(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return description;
	}
}
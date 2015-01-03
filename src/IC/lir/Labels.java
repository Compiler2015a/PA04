package IC.lir;

import java.util.HashMap;
import java.util.Map;

import IC.lir.Instructions.Label;

public class Labels {
	Map<String, Label> labels;
	
	public Labels() {
		this.labels = new HashMap<String,Label>();
	}
	
	public Label request(String name) {
		if (!labels.containsKey(name))
			labels.put(name, new Label(name));
		return labels.get(name);
	}
}

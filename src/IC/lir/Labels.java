package IC.lir;

import java.util.HashMap;
import java.util.Map;

import IC.lir.Instructions.Label;

public class Labels {
	Map<String, Label> labels;
	int labelsCounter;

	public Labels() {
		this.labels = new HashMap<String,Label>();
		this.labelsCounter = 0;
	}
	
	public Label innerLabelRequest(CommonLabels label) {
		return requestStr(label.toString() + labelsCounter);
	}
	
	public Label innerLabelRequest(CommonLabels label, int labelsCounter) {
		return requestStr(label.toString() + labelsCounter);
	}
	
	public Label requestStr(String name) {
		if (!labels.containsKey(name))
			labels.put(name, new Label(name));
		return labels.get(name);
	}
	
	public int getLabelsCounter() {
		return labelsCounter;
	}
	
	public int increaseLabelsCounter() {
		return ++labelsCounter;
	}
}

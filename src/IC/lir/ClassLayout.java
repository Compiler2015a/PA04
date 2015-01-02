package IC.lir;

import java.util.HashMap;
import java.util.Map;

import IC.AST.Field;
import IC.AST.Method;

public class ClassLayout {
	Map<Method, Integer> methodToOffset;
	//DVPtr = 0;
	Map<Field, Integer> fieldToOffset;

	public ClassLayout() {
		methodToOffset = new HashMap<Method, Integer>();
		fieldToOffset = new HashMap<Field, Integer>();
	}

	/**
	 * 
	 * @param m Method to add to method offset
	 * @return the method's offset
	 */
	int addMethod(Method m) {
		if(!methodToOffset.containsKey(m))
			methodToOffset.put(m, methodToOffset.size());
		return methodToOffset.get(m);
	}

	/**
	 * 
	 * @param f Field to add to field offset
	 * @return the field's offset
	 */
	int addField(Field f) {
		if(!fieldToOffset.containsKey(f))
			fieldToOffset.put(f, fieldToOffset.size());
		return fieldToOffset.get(f);
	}
	
	@Override
	public String toString() {
		//TODO: DO THIS
		return null;
	}
}

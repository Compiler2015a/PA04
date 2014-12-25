package IC.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import IC.AST.Formal;
import IC.AST.ICClass;
import IC.AST.Method;
import IC.AST.PrimitiveType;
import IC.AST.UserType;
import IC.DataTypes;
import IC.LiteralTypes;

public class TypeTable {

	private String id;
	
	private Map<Type, Integer> values;
	// Maps element types to array types
	private Map<Type,ArrayType> uniqueArrayTypes;
	private Map<String,ClassType> uniqueClassTypes;
	private Map<String,MethodType> uniqueMethodTypes;
	
	private Type intType;
	private Type boolType;
	private Type nullType;
	private Type stringType;
	private Type voidType;
	
	private int idCounter;
	
	public TypeTable(String tableId) {
		this.id = tableId;
		this.idCounter = 0;
		this.uniqueArrayTypes = new HashMap<Type, ArrayType>();
		this.uniqueClassTypes = new HashMap<String,ClassType>();
		this.uniqueMethodTypes = new HashMap<String,MethodType>();
		
		this.values = new HashMap<Type, Integer>();
	}
	
	public Type getTypeFromASTTypeNode(IC.AST.Type typeNode) {
		if (typeNode instanceof PrimitiveType) {
			PrimitiveType pt = (PrimitiveType)typeNode;
			Type primitive = getPrimitiveType(typeNode.getName());
			if (pt.getDimension() == 0) 
				return primitive;
			else
				return getArrayFromType(primitive, pt.getDimension());
		}
		else {
			UserType ut = (UserType)typeNode;
			Type clsType = uniqueClassTypes.get(ut.getName());
			if (ut.getDimension() == 0)
				return clsType;
			else 
				return getArrayFromType(clsType, ut.getDimension());
		}
	}
	
	public void printTable() {
		System.out.println("Type Table: " + id);
		System.out.println("    " + values.get(intType) + ": Primitive type: " + intType.toString());
		System.out.println("    " + values.get(boolType) + ": Primitive type: " + boolType.toString());
		System.out.println("    " + values.get(nullType) + ": Primitive type: " + nullType.toString());
		System.out.println("    " + values.get(stringType) + ": Primitive type: " + stringType.toString());
		System.out.println("    " + values.get(voidType) + ": Primitive type: " + voidType.toString());
		
		List<Map.Entry<String,ClassType>> sorted_uniqueClassTypes =
	            new ArrayList<Map.Entry<String,ClassType>>( uniqueClassTypes.entrySet() );
		Collections.sort(sorted_uniqueClassTypes, new Comparator<Map.Entry<String,ClassType>>() {
	           public int compare( Map.Entry<String,ClassType> o1, Map.Entry<String,ClassType> o2 ) {
	                return Integer.compare(values.get(o1.getValue()), values.get(o2.getValue()));
	            }
		});
		for (Map.Entry<String,ClassType> entry : sorted_uniqueClassTypes) {
			System.out.print("    " + values.get(entry.getValue()) + ": Class: " + entry.getValue().toString());
			if (entry.getValue().hasSuperClass())
				System.out.print(", Superclass ID: " + values.get(uniqueClassTypes.get(
						entry.getValue().getSuperClassType().getClassName())));
			System.out.println();
		}
		
		List<Map.Entry<Type,ArrayType>> sorted_uniqueArrayTypes =
	            new ArrayList<Map.Entry<Type,ArrayType>>( uniqueArrayTypes.entrySet() );
		Collections.sort(sorted_uniqueArrayTypes, new Comparator<Map.Entry<Type,ArrayType>>() {
	           public int compare( Map.Entry<Type,ArrayType> o1, Map.Entry<Type,ArrayType> o2 ) {
	                return Integer.compare(values.get(o1.getValue()), values.get(o2.getValue()));
	            }
		});
		
		for (Map.Entry<Type,ArrayType> entry : sorted_uniqueArrayTypes) 
			System.out.println("    " + values.get(entry.getValue()) + ": Array type: " + entry.getValue().toString());
		
		List<Map.Entry<String,MethodType>> sorted_uniqueMethodTypes =
	            new ArrayList<Map.Entry<String,MethodType>>( uniqueMethodTypes.entrySet() );
		Collections.sort(sorted_uniqueMethodTypes, new Comparator<Map.Entry<String,MethodType>>() {
	           public int compare( Map.Entry<String,MethodType> o1, Map.Entry<String,MethodType> o2 ) {
	                return Integer.compare(values.get(o1.getValue()), values.get(o2.getValue()));
	            }
		});
		
		for (Map.Entry<String,MethodType> entry : sorted_uniqueMethodTypes) 
			System.out.println("    " + values.get(entry.getValue()) + ": Method type: {" + entry.getValue().toString() + "}");
	}
	
	public void addPrimitiveTypes() {
		this.intType = new IntType();
		this.boolType = new BoolType();
		this.nullType = new NullType();
		this.stringType = new StringType();
		this.voidType = new VoidType();
		values.put(intType, 1);
		values.put(boolType, 2);
		values.put(nullType, 3);
		values.put(stringType, 4);
		values.put(voidType, 5);
		this.idCounter = 6;
	}
	
	public void addArrayType(IC.AST.Type typeNode) {
		Type currArrType;
		if (typeNode instanceof PrimitiveType) 
			currArrType = getPrimitiveType(typeNode.getName()); 
		else
			currArrType = uniqueClassTypes.get(typeNode.getName());
		
		for (int i = 0; i < typeNode.getDimension(); i++) 
			currArrType = addAndReturnArraySingleType(currArrType);
	}
	
	/**
	 * 
	 * @param classAST
	 * @return false if and only if the class is extending a class which dosen't exist
	 */
	public Boolean addClassType(ICClass classAST) {
		if (uniqueClassTypes.containsKey(classAST.getName()))
			return true;
		ClassType superClassType = null;
		if (classAST.hasSuperClass()) {
			if (!uniqueClassTypes.containsKey(classAST.getSuperClassName()))
				return false;
			superClassType = uniqueClassTypes.get(classAST.getSuperClassName());
		}
		ClassType clst = new ClassType(classAST.getName(), superClassType);

		uniqueClassTypes.put(classAST.getName(), clst);
		values.put(clst, idCounter);
		idCounter++;
		
		return true;
	}
	
	public ClassType getClassType(String clsName) {
		return uniqueClassTypes.get(clsName);
	}
	
	public void addMethodType(Method method) {
		MethodType methodType = generateMethodType(method);
		if (uniqueMethodTypes.containsKey(methodType.toString()))
			return;
		uniqueMethodTypes.put(methodType.toString(), methodType);
		values.put(methodType, idCounter);
		idCounter++;
	}
	
	public MethodType getMethodType(Method method) {
		MethodType methodType = generateMethodType(method);
		return uniqueMethodTypes.get(methodType.toString());
	}
	
	public Type getReturnTypeFromMethodType(Type type) {
		MethodType methodType = (MethodType)type;
		return methodType.getReturnType();
	}
	
	public Type getPrimitiveType(String dataTypeName) {
		if (dataTypeName == DataTypes.INT.getDescription())
			return intType;
		if (dataTypeName == DataTypes.STRING.getDescription())
			return stringType;
		if (dataTypeName == DataTypes.VOID.getDescription())
			return voidType;
		if (dataTypeName == DataTypes.BOOLEAN.getDescription())
			return boolType;
		
		return null;
	}
	
	public Type getLiteralType(String literalTypeName) {
		if (literalTypeName == LiteralTypes.INTEGER.getDescription())
			return intType;
		if (literalTypeName == LiteralTypes.STRING.getDescription())
			return stringType;
		if ((literalTypeName == LiteralTypes.TRUE.getDescription()) || (literalTypeName == LiteralTypes.FALSE.getDescription()))
			return boolType;
		if (literalTypeName == LiteralTypes.NULL.getDescription())
			return nullType;
		
		return null;

	}
	
	public ArrayType getArrayFromType(Type original, int dimention) {
		Type currArrType = original;
		for (int i = 0; i < dimention; i++) 
			currArrType = uniqueArrayTypes.get(currArrType);
		
		return (ArrayType)currArrType;
	}
	
	public Type getTypeFromArray(Type type) {
		ArrayType arrayType = (ArrayType)type;
		return arrayType.getElemType();
	}
	
	private ArrayType addAndReturnArraySingleType(Type elemType) {
		if (uniqueArrayTypes.containsKey(elemType))
			return uniqueArrayTypes.get(elemType);
		
		ArrayType arrt = new ArrayType(elemType);
		uniqueArrayTypes.put(elemType, arrt);
		values.put(arrt, idCounter);
		idCounter++;
		return arrt;
	}
	
	private MethodType generateMethodType(Method method) {
		Type[] params = new Type[method.getFormals().size()];
		List<Formal> formals = method.getFormals(); 
		for (int i = 0; i < params.length; i++)
			params[i] = getTypeFromASTTypeNode(formals.get(i).getType());
		MethodType methodType = new MethodType(params, getTypeFromASTTypeNode(method.getType()));
		return methodType;
	}
}

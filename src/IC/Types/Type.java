package IC.Types;

public abstract class Type {
	protected String name;
	
	public Type(String name) 
	{
		this.name=name;
	}
	
	public boolean subTypeOf(Type t)
	{
		if (this.equals(t))
			return true;
		if ((this.isNullType()) && (t.isClassType()))
			return true;
		if (this.isClassType()) {
			ClassType classType = (ClassType)this;
			if (classType.hasSuperClass())
				return classType.getSuperClassType().subTypeOf(t);
		}
		return false;
	}
	
	public boolean isClassType() {
		return (this instanceof ClassType);
	}
	
	public boolean isMethodType() {
		return (this instanceof MethodType);
	}
	
	public boolean isArrayType() {
		return (this instanceof ArrayType);
	}
	
	public boolean isIntType() {
		return (this instanceof IntType);
	}
	
	public boolean isStringType() {
		return (this instanceof StringType);
	}
	
	public boolean isBoolType() {
		return (this instanceof BoolType);
	}
	
	public boolean isVoidType() {
		return (this instanceof VoidType);
	}
	
	public boolean isNullType() {
		return (this instanceof NullType);
	}
	
	public Type getReturnType() {
		if (!this.isMethodType())
			return null;
		MethodType thisMethodType = (MethodType)this;
		return thisMethodType.getReturnType();
	}
	
	public boolean isNullAssignable() {
		if ((this.isClassType()) || (this.isArrayType()) || (this.isStringType()))
			return true;
		if (this instanceof MethodType) {
			MethodType methodType = (MethodType)this;
			return methodType.getReturnType().isNullAssignable();
		}
		return false;
	}
	public String getName()
	{
		return this.name;
	}
}
class IntType extends Type 
{
	public IntType()
	{
		super("IntType");
	}
	
	@Override
	public String toString() {
		return "int";
	}
}

class BoolType extends Type 
{
	public BoolType()
	{
		super("BoolType");
	}
	
	@Override
	public String toString() {
		return "boolean";
	}
}

class NullType extends Type 
{
	public NullType()
	{
		super("NullType");
	}
	
	@Override
	public String toString() {
		return "null";
	}
}

class StringType extends Type 
{
	public StringType()
	{
		super("StringType");
	}
	
	@Override
	public String toString() {
		return "string";
	}
}

class VoidType extends Type 
{
	public VoidType()
	{
		super("VoidType");
	}
	
	@Override
	public String toString() {
		return "void";
	}
}

class ArrayType extends Type 
{
	private Type elemType;
	public ArrayType(Type elemType)
	{
		super("ArrayType");
		this.elemType=elemType;
	}
	
	@Override
	public String toString() {
		return elemType.toString() + "[]";
	}
	
	public Type getElemType() {
		return elemType;
	}
}

class MethodType extends Type 
{  
	private Type[] paramTypes;

	private Type returnType;

	public MethodType(Type[] paramTypes,Type returnType)
	{
		super("MethodType");
		this.paramTypes=paramTypes;
		this.returnType=returnType;
	}
	
	public Type getReturnType() {
		return returnType;
	}
	
	public Type[] getParamTypes() {
		return paramTypes;
	}
	
	@Override
	public String toString() {
		String paramTypesStr = "";
		for (int i = 0; i < paramTypes.length; i++) {
			if (i == 0)
				paramTypesStr += paramTypes[i].toString();
			else
				paramTypesStr += ", " + paramTypes[i].toString();
		}
		return paramTypesStr + " -> " + returnType.toString();
	}
}

class ClassType extends Type 
{   
	ClassType superClassType;
	String clsName;

	public ClassType(String clsName, ClassType superClassType)
	{
		super("ClassType");
		this.clsName = clsName;
		this.superClassType = superClassType;
	}
	
	public ClassType getSuperClassType() {
		return superClassType;
	}

	public Boolean hasSuperClass() {
		return (superClassType != null);
	}
	
	public String getClassName() {
		return this.clsName;
	}
	
	@Override
	public String toString() {
		return clsName;
	}
}

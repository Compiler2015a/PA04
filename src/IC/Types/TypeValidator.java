package IC.Types;

import java.util.List;

import IC.DataTypes;
import IC.AST.*;
import IC.SemanticAnalysis.SemanticError;
import IC.SemanticAnalysis.SemanticErrorThrower;
import IC.SymbolsTable.IDSymbolsKinds;
import IC.SymbolsTable.SymbolTable;

public class TypeValidator implements Visitor {

	private int loopNesting;
	private TypeTable typeTable;
	private SemanticErrorThrower semanticErrorThrower;
	
	public TypeValidator(TypeTable typeTable) {
		this.typeTable = typeTable;
	}
	
	// Scans the program tree recursively and check the following semantic rules:
	// 1) Type checking
	// 2) Each method has return statement in all of the computation paths.
	// 3) No continue and break keywords appear outside a while scope.
	// 4) This expression is not called from a static method.
	// In addition, the scan sets types to expressions during the type checking.
	public void validate(Program program) throws SemanticError {
		if (!(Boolean)program.accept(this))
			this.semanticErrorThrower.execute();
	}
	
	@Override
	public Object visit(Program program) {
		loopNesting = 0;
		for (ICClass cls : program.getClasses()) {
			if (!(Boolean)cls.accept(this))
				return false;
		}
		return true;
	}

	@Override
	public Object visit(ICClass icClass) {
		for (Method method : icClass.getMethods()) 
			if (!(Boolean)method.accept(this))
				return false;
		
		return true;
	}

	@Override
	public Object visit(Field field) {
		return true;
	}
	
	@Override
	public Object visit(VirtualMethod method) {
		return visitMethod(method);
	}

	@Override
	public Object visit(StaticMethod method) {
		return visitMethod(method);
	}

	@Override
	public Object visit(LibraryMethod method) {
		return visitMethod(method);
	}

	@Override
	public Object visit(Formal formal) {
		return true;
	}

	@Override
	public Object visit(PrimitiveType type) { 
		// Not called
		return null;
	}

	@Override
	public Object visit(UserType type) {
		// Not called
		return null;
	}

	@Override
	public Object visit(Assignment assignment) {
		if (!(Boolean)assignment.getVariable().accept(this))
			return false;
		Type typeTo = assignment.getVariable().getEntryType();
		if (!(Boolean)assignment.getAssignment().accept(this))
			return false;
		Type typeFrom = assignment.getAssignment().getEntryType();
		
		// Checks the types are legal for assignment
		if (!isLegalAssignment(typeTo, typeFrom)) {
			semanticErrorThrower =  new SemanticErrorThrower(assignment.getLine(), "Value assigned to local variable type mismatch");
			return false;
		} 

		return true;
	}

	@Override
	public Object visit(CallStatement callStatement) {
		if (!(Boolean)callStatement.getCall().accept(this))
			return false;

		return true;
	}

	@Override
	public Object visit(Return returnStatement) {
		// Checks the return value type corresponds the method return type. 
		Type typeInFact;
		if (returnStatement.hasValue()) {
			if (!(Boolean)returnStatement.getValue().accept(this))
				return false;
			typeInFact = returnStatement.getValue().getEntryType();
		}
		else
			typeInFact = typeTable.getPrimitiveType(DataTypes.VOID.getDescription());
		
		MethodType methodType = (MethodType)returnStatement.getMethodType();
		if (!isLegalAssignment(methodType.getReturnType(), typeInFact)) {
			semanticErrorThrower =  new SemanticErrorThrower(returnStatement.getLine(), String.format(
					"Return statement is not of type %s", methodType.getReturnType().toString()));
			return false;
		}
		return true;
	}

	@Override
	public Object visit(If ifStatement) {
		if (!(Boolean)ifStatement.getCondition().accept(this))
			return false;
		
		Type typeCondition = ifStatement.getCondition().getEntryType();
		// condition expression must have a boolean type
		if (!typeCondition.isBoolType()) {
			semanticErrorThrower =  new SemanticErrorThrower(ifStatement.getLine(), "Non boolean condition for if statement");
			return false;
		}
		
		if (!(Boolean)ifStatement.getOperation().accept(this))
			return false;
		
		if (ifStatement.hasElse())
			if (!(Boolean)ifStatement.getElseOperation().accept(this))
				return false;
		return true;
	}

	@Override
	public Object visit(While whileStatement) {
		if (!(Boolean)whileStatement.getCondition().accept(this))
			return false;
		// condition expression must have a boolean type
		Type typeCondition = whileStatement.getCondition().getEntryType();
		if (!typeCondition.isBoolType()) {
			semanticErrorThrower =  new SemanticErrorThrower(whileStatement.getLine(), "Non boolean condition for while statement");
			return false;
		}
		loopNesting++;
		if (!(Boolean)whileStatement.getOperation().accept(this))
			return false;
		
		loopNesting--;
		return true;
	}

	@Override
	public Object visit(Break breakStatement) {
		// Checks if the break statement was called from a while scope.
		if (!isBreakContinueValid()) {
			semanticErrorThrower =  new SemanticErrorThrower(breakStatement.getLine(), 
					"Use of 'break' statement outside of loop not allowed");
			return false;
		}
		return true;
	}

	@Override
	public Object visit(Continue continueStatement) {
		// Checks if the continue statement was called from a while scope.
		if (!isBreakContinueValid()) {
			semanticErrorThrower =  new SemanticErrorThrower(continueStatement.getLine(), 
					"Use of 'continue' statement outside of loop not allowed");
			return false;
		}
		return true;
	}

	@Override
	public Object visit(StatementsBlock statementsBlock) {
		// Type validation on all the statements in a statement block
		for (Statement statement : statementsBlock.getStatements()) 
			if (!(Boolean)statement.accept(this))
				return false;
		return true;
	}

	@Override
	public Object visit(LocalVariable localVariable) {
		if (localVariable.hasInitValue()) 
		{
			if (!(Boolean)localVariable.getInitValue().accept(this))
				return false;
			Type varType = localVariable.getEntryType();
			Type initType = localVariable.getInitValue().getEntryType();
			// Checks the types are legal for assignment
			if (!isLegalAssignment(varType, initType)) {
				semanticErrorThrower =  new SemanticErrorThrower(localVariable.getLine(), "Value assigned to local variable type mismatch");
				return false;
			} 
		}
		return true;
	}

	@Override
	public Object visit(VariableLocation location) {
		if (location.isExternal()) {
			if (!(Boolean)location.getLocation().accept(this))
				return false;
			// Outside location must be an instance of a class.
			if (!location.getLocation().getEntryType().isClassType()) {
				semanticErrorThrower =  new SemanticErrorThrower(location.getLine(), "External location must have a class type");
				return false;
			}
		}
		return true;
	}

	@Override
	public Object visit(ArrayLocation location) {
		if (!(Boolean)location.getIndex().accept(this))
			return false;
		if (!(Boolean)location.getArray().accept(this))
			return false;
		Type typeIndex = location.getIndex().getEntryType();
		
		Type typeArray = location.getArray().getEntryType();
		// Checks the index expression has an int type.
		if (!typeIndex.isIntType()) {
			semanticErrorThrower = new SemanticErrorThrower(location.getLine(), "Array index must be an integer");
			return false;
		}
		
		if (!typeArray.isArrayType()) {
			semanticErrorThrower = new SemanticErrorThrower(location.getLine(), "Array location must have an array type");
			return false;
		}
		// Set the type of the array location with array T[] to be T.
		location.setEntryType(typeTable.getTypeFromArray(typeArray));
		return true;
	}

	@Override
	public Object visit(StaticCall call) {
		for (Expression arg : call.getArguments())
			if (!(Boolean)arg.accept(this))
				return false;
		MethodType calledMethodType = (MethodType)call.getMethodType();
		if (call.getArguments().size() != calledMethodType.getParamTypes().length) {
			semanticErrorThrower = new SemanticErrorThrower(call.getLine(), 
					String.format("Method except %d arguments but gets %d", 
							calledMethodType.getParamTypes().length, call.getArguments().size()));
			return false;
		}

		for (int i = 0; i < call.getArguments().size(); i++) {
				if (!isLegalAssignment(calledMethodType.getParamTypes()[i], 
					call.getArguments().get(i).getEntryType())) {
				semanticErrorThrower = new SemanticErrorThrower(call.getLine(), 
						"Argument type dosen't match the method parameter type");
				return false;
			}
		}
		call.setEntryType(typeTable.getReturnTypeFromMethodType(calledMethodType));
		return true; 
	}

	@Override
	public Object visit(VirtualCall call) {
		if (call.isExternal()) {
			if (!(Boolean)call.getLocation().accept(this))
				return false;
			
			Type locationType = call.getLocation().getEntryType();
			if (!locationType.isClassType()) {
				semanticErrorThrower = new SemanticErrorThrower(call.getLine(), "Object is not of class type");
				return false;
			}
		}	
		
		for (Expression arg : call.getArguments())
			if (!(Boolean)arg.accept(this))
				return false;
		MethodType calledMethodType = (MethodType)call.getMethodType();
		if (call.getArguments().size() != calledMethodType.getParamTypes().length) {
			semanticErrorThrower = new SemanticErrorThrower(call.getLine(), 
					String.format("Method expects %d arguments but gets %d", 
							calledMethodType.getParamTypes().length, call.getArguments().size()));
			return false;
		}
		for (int i = 0; i < call.getArguments().size(); i++) {
			if (!isLegalAssignment(calledMethodType.getParamTypes()[i], 
					call.getArguments().get(i).getEntryType())) {
				semanticErrorThrower = new SemanticErrorThrower(call.getLine(), "Argument type dosen't match the method parameter type");
				return false;
			}
		}
		call.setEntryType(typeTable.getReturnTypeFromMethodType(calledMethodType));
		return true; 
	}

	@Override
	public Object visit(This thisExpression) {
		SymbolTable scope = thisExpression.getSymbolsTable();
		while (scope.getId().contains("block#")) 
			scope = scope.getParentSymbolTable();
		if (scope.getParentSymbolTable().getEntry(scope.getId()).getKind() == IDSymbolsKinds.STATIC_METHOD) {
			semanticErrorThrower = new SemanticErrorThrower(thisExpression.getLine(), 
					"Use of 'this' expression inside static method is not allowed");
			return false;
		}
		scope = scope.getParentSymbolTable();
		
		thisExpression.setEntryType(typeTable.getClassType(scope.getId()));
		return true;
	}

	@Override
	public Object visit(NewClass newClass) {
		newClass.setEntryType(typeTable.getClassType(newClass.getName()));
		return true;
	}

	@Override
	public Object visit(NewArray newArray) {
		if (!(Boolean)newArray.getSize().accept(this))
			return false;
		
		Type typeSize = newArray.getSize().getEntryType();
		// Checks the array size expression is of type int.
		if (typeSize == null || !typeSize.isIntType()) {
			semanticErrorThrower = new SemanticErrorThrower(newArray.getLine(), "Array size must be an integer");
			return false;
		}
		
		if (newArray.getType() instanceof PrimitiveType)
			newArray.setEntryType(typeTable.getArrayFromType(typeTable.getPrimitiveType(newArray.getType().getName()), 
					newArray.getType().getDimension()));
		if (newArray.getType() instanceof UserType)
			newArray.setEntryType(typeTable.getArrayFromType(typeTable.getClassType(newArray.getType().getName()), 
					newArray.getType().getDimension()));

		return true;
	}

	@Override
	public Object visit(Length length) {
		if (!(Boolean)length.getArray().accept(this))
			return false;
		Type type = length.getArray().getEntryType();
		// the expression which is evaluated by its length must have an array type. 
		if (!type.isArrayType()) {
			semanticErrorThrower = new SemanticErrorThrower(length.getLine(), "Length expression must have an array type");
			return false;
		}
		length.setEntryType(typeTable.getPrimitiveType(DataTypes.INT.getDescription()));
		return true;
	}

	@Override
	public Object visit(Literal literal) {
		literal.setEntryType(typeTable.getLiteralType(literal.getType().getDescription()));
		return true;
	}

	@Override
	public Object visit(LogicalUnaryOp unaryOp) {
		if (!(Boolean)unaryOp.getOperand().accept(this))
			return false;
		Type type = unaryOp.getOperand().getEntryType();
		switch(unaryOp.getOperator()) {
			case LNEG:
				if (type.isBoolType()) {
					unaryOp.setEntryType(type);
					return true;
				}
				break;
			default:
				break;
		}
		
		semanticErrorThrower = new SemanticErrorThrower(unaryOp.getLine(), "Operand of unary operator has an invalid type");
		return false;
	}

	@Override
	public Object visit(LogicalBinaryOp binaryOp) {
		if (!(Boolean)binaryOp.getFirstOperand().accept(this))
			return false;
			if (!(Boolean)binaryOp.getSecondOperand().accept(this))
			return false;
		Type typeFirst = binaryOp.getFirstOperand().getEntryType();
		Type typeSecond = binaryOp.getSecondOperand().getEntryType();

		String onWhat = "";
		String opType = "";
		switch(binaryOp.getOperator()) {
			case LAND:
			case LOR:
				if (typeFirst.isBoolType() && typeSecond.isBoolType()) {
					binaryOp.setEntryType(typeTable.getPrimitiveType(DataTypes.BOOLEAN.getDescription()));
					return true;
				}
				onWhat = "non-boolean";
				opType = "logical";
				break;
			case LT:
			case LTE:
			case GT:
			case GTE:
				if (typeFirst.isIntType() && typeSecond.isIntType()) {
					binaryOp.setEntryType(typeTable.getPrimitiveType(DataTypes.BOOLEAN.getDescription()));
					return true;
				}
				onWhat = "non-integer";
				opType = "logical";
				break;
			case EQUAL:
			case NEQUAL:
				if ((typeFirst.equals(typeSecond))
				|| ((typeFirst.isNullAssignable()) && (typeSecond.isNullType()))	
				|| ((typeFirst.isNullType()) && (typeSecond.isNullAssignable()))
				|| ((typeFirst.subTypeOf(typeSecond)))
				|| ((typeSecond.subTypeOf(typeFirst)))) {
					binaryOp.setEntryType(typeTable.getPrimitiveType(DataTypes.BOOLEAN.getDescription()));
					return true;
				}
				onWhat = "not-fitting";
				opType = "logical";
				break;
			default:
				break;
		}
		
		semanticErrorThrower = new SemanticErrorThrower(binaryOp.getLine(), String.format("Invalid %s binary op (%s) on %s expression",
				opType, binaryOp.getOperator().toString(), onWhat));
		return false;
	}


	@Override
	public Object visit(MathUnaryOp unaryOp) {
		if (!(Boolean)unaryOp.getOperand().accept(this))
			return false;
		Type type = unaryOp.getOperand().getEntryType();
		switch(unaryOp.getOperator()) {
			case UMINUS:
				if (type.isIntType()) {
					unaryOp.setEntryType(type);
					return true;
				}
				break;
			default:
				break;
		}
		semanticErrorThrower = new SemanticErrorThrower(unaryOp.getLine(), "Operand of unary operator has an invalid type");
		return false;
	}

	@Override
	public Object visit(ExpressionBlock expressionBlock) {
		if (!(Boolean)expressionBlock.getExpression().accept(this))
			return false;
		expressionBlock.setEntryType(expressionBlock.getExpression().getEntryType());
		return true;
	}



	@Override
	public Object visit(MathBinaryOp binaryOp) {
		if (!(Boolean)binaryOp.getFirstOperand().accept(this))
			return false;
			if (!(Boolean)binaryOp.getSecondOperand().accept(this))
			return false;
		Type typeFirst = binaryOp.getFirstOperand().getEntryType();
		Type typeSecond = binaryOp.getSecondOperand().getEntryType();

		String onWhat = "";
		String opType = "";
		switch(binaryOp.getOperator()) {
			case PLUS:
				if ((typeFirst.isIntType() && typeSecond.isIntType()) 
						|| (typeFirst.isStringType() && typeSecond.isStringType())) {
						binaryOp.setEntryType(typeFirst);
						return true;
				}
				onWhat = "non-integer or non-string";
				opType = "arithmetic";
				break;
			case MINUS:
			case MULTIPLY:
			case DIVIDE:
			case MOD:
				if (typeFirst.isIntType() && typeSecond.isIntType()) {
					binaryOp.setEntryType(typeFirst);
					return true;
				}
				onWhat = "non-integer";
				opType = "arithmetic";
				break;
				default:
					break;
		}
		semanticErrorThrower = new SemanticErrorThrower(binaryOp.getLine(), String.format("Invalid %s binary op (%s) on %s expression",
				opType, binaryOp.getOperator().toString(), onWhat));
		return false;
	}
	
	private Object visitMethod(Method method) {
		
		// Type checking of all the statements
		for (Formal formal : method.getFormals()) 
			if (!(Boolean)formal.accept(this))
				return false;
		for (Statement statement : method.getStatements()) 
			if (!(Boolean)statement.accept(this))
				return false;
		
		// Checking that the method has a return statement in each computation path:
		MethodType methodType = (MethodType)method.getEntryType();
		
		
		if((method instanceof LibraryMethod)) // if this is a library method, no return statement is needed.
			return true;
		

		if (!testRetrunPaths(method.getStatements())) { // No return statement error:
			if (!methodType.getReturnType().isVoidType()) {// if this is a void type method, no return statement is needed.
				semanticErrorThrower =  new SemanticErrorThrower(method.getLine(), String.format("Method %s has no return statement", method.getName()));
				return false;
			}
			return true;
		}
		method.setHasFlowWithoutReturn();
		return true;
	}
	
	private Boolean isLegalAssignment(Type varType, Type assignmentType) {
		return (varType.isNullAssignable() && assignmentType.isNullType()) || 
				(assignmentType.subTypeOf(varType));
	}
	
	private Boolean testRetrunPaths(List<Statement> statements) {
		for (Statement stmnt : statements) 
			if (testRetrunPaths(stmnt))
				return true;
		
		return false;
	}
	
	private Boolean testRetrunPaths(Statement stmnt) {
		if (stmnt instanceof Return)
			return true;
		if (stmnt instanceof StatementsBlock) 
			return testRetrunPaths(((StatementsBlock)stmnt).getStatements());
		if (stmnt instanceof If) {
			If ifStmnt = (If)stmnt;
			if (ifStmnt.hasElse())
				return (testRetrunPaths(ifStmnt.getOperation()) && testRetrunPaths(ifStmnt.getElseOperation()));
		}
		return false;
	}
	
	// Helps to check if a continue or a break statement were called from a while scope
	private boolean isBreakContinueValid() {
		return loopNesting > 0;
	}
}

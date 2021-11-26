package part15;

import part15.ast.Num;
import part15.ast.Param;
import part15.ast.ProcedureDecl;
import part15.ast.Program;
import part15.ast.UnaryOp;
import part15.ast.Var;
import part15.ast.VarDecl;
import part15.ast.Visitor;
import part15.errors.ErrorCode;
import part15.errors.SemanticException;
import part15.symbols.ProcedureSymbol;
import part15.symbols.ScopedSymbolTable;
import part15.symbols.Symbol;
import part15.symbols.VarSymbol;

public class SemanticAnalyzer implements Visitor<Void> {
	public ScopedSymbolTable currentScope;  // represents our stack pointer
	
	public Void error(ErrorCode code, Token token) {
		return error(code, token, "");
	}
	
	public Void error(ErrorCode code, Token token, String message) {
		throw new SemanticException(code, token, String.format("%s -> %s%n%s", code.message, token, message));
	}
	
	@Override
	public Void visit(Program node) {
		Logger.log("Entered 'global' scope");
		currentScope = new ScopedSymbolTable("global", 1, currentScope)
			.initBuiltins();	// only initialise built-ins once
		visit(node.block);
//		currentScope.printContents();
		currentScope = currentScope.enclosingScope;		// reset stack pointer
		Logger.log("Left 'global' scope");
		return null;
	}
	
	@Override
	public Void visit(ProcedureDecl node) {
		String procName = node.name;
		ProcedureSymbol procSymbol = new ProcedureSymbol(procName);
		currentScope.insert(procSymbol);
		// Create scope for procedure
		Logger.log("Entering scope: "+procName);
		ScopedSymbolTable procedureScope = currentScope.createNested(procName);
		currentScope = procedureScope;
		// Insert parameters into the procedure scope
		for (Param param : node.params) {
			Symbol paramType = currentScope.lookup(param.type.getValue());
	 		String paramName = param.var.token.getValue();
			VarSymbol varSymbol = new VarSymbol(paramName, paramType);
			currentScope.insert(varSymbol);
			procSymbol.params.add(varSymbol);
		}
		// Execute procedure
		visit(node.block);
//		currentScope.printContents();
		currentScope = currentScope.enclosingScope;
		Logger.log("Left %s's scope", procName);
		return null;
	}
	
	@Override
	public Void visit(VarDecl node) {
		String typeName = node.type.token.getValue();
		Symbol type = currentScope.lookup(typeName);
		String varName = node.var.token.getValue();
		VarSymbol varSymbol = new VarSymbol(varName, type);
		if (currentScope.lookup(varName, true) != null)
			error(ErrorCode.DUPLICATE_ID, node.var.token);
		currentScope.insert(varSymbol);
		return null;
	}
	
	@Override
	public Void visit(Var node) {
		String varName = node.token.getValue();
		Symbol varSymbol = currentScope.lookup(varName);
//		currentScope.printContents();
		if (varSymbol == null) 
			error(ErrorCode.ID_NOT_FOUND, node.token);
		return null;
	}
	
	@Override
	public Void visit(Num node) {
		return null;
	}
	
	@Override
	public Void visit(UnaryOp node) {
		return null;
	}
}

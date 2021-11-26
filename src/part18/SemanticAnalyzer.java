package part18;

import part18.ast.AST;
import part18.ast.Param;
import part18.ast.ProcedureCall;
import part18.ast.ProcedureDecl;
import part18.ast.Program;
import part18.ast.Var;
import part18.ast.VarDecl;
import part18.ast.Visitor;
import part18.errors.ErrorCode;
import part18.symbols.ProcedureSymbol;
import part18.symbols.ScopedSymbolTable;
import part18.symbols.Symbol;
import part18.symbols.VarSymbol;

public class SemanticAnalyzer implements Visitor<Void> {
	public ScopedSymbolTable currentScope;  // represents our stack pointer
	
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
		// Hookup reference of procedure body to procSymbol
		procSymbol.block = node.block;
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
	public Void visit(ProcedureCall node) {
		Symbol symbol = currentScope.lookup(node.name);
		if (!ProcedureSymbol.class.isInstance(symbol))
			error(ErrorCode.ID_NOT_FOUND, node.token);
		ProcedureSymbol procSymbol = ProcedureSymbol.class.cast(symbol);
		if (node.params.size() != procSymbol.params.size())
			return error(ErrorCode.WRONG_PARAMS_NUM, node.token);
		node.procSymbol = procSymbol;
		for (AST param : node.params)
			visit(param);
		return null;
	}
}

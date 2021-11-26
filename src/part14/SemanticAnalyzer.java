package part14;

import part14.ast.Param;
import part14.ast.ProcedureDecl;
import part14.ast.Program;
import part14.ast.Var;
import part14.ast.VarDecl;
import part14.ast.Visitor;
import part14.symbols.ProcedureSymbol;
import part14.symbols.ScopedSymbolTable;
import part14.symbols.Symbol;
import part14.symbols.VarSymbol;

public class SemanticAnalyzer implements Visitor<Void> {
	public ScopedSymbolTable currentScope;  // represents our stack pointer
	
	@Override
	public Void visit(Program node) {
		System.out.println("Entered 'global' scope");
		currentScope = new ScopedSymbolTable("global", 1, currentScope)
			.initBuiltins();	// only initialise built-ins once
		visit(node.block);
		currentScope.printContents();
		currentScope = currentScope.enclosingScope;		// reset stack pointer
		System.out.println("Left 'global' scope");
		return null;
	}
	
	@Override
	public Void visit(ProcedureDecl node) {
		String procName = node.name;
		ProcedureSymbol procSymbol = new ProcedureSymbol(procName);
		currentScope.insert(procSymbol);
		// Create scope for procedure
		System.out.println("Entering scope: "+procName);
		ScopedSymbolTable procedureScope = currentScope.createNested(procName);
		currentScope = procedureScope;
		// Insert parameters into the procedure scope
		for (Param param : node.params) {
			Symbol paramType = currentScope.lookup(param.type.getValue());
	 		String paramName = param.var.token.getValue();
			VarSymbol varSymbol = new VarSymbol(paramName, paramType.type);
			currentScope.insert(varSymbol);
			procSymbol.params.add(varSymbol);
		}
		// Execute procedure
		visit(node.block);
		currentScope.printContents();
		currentScope = currentScope.enclosingScope;
		System.out.printf("Left %s's scope%n", procName);
		return null;
	}
	
	@Override
	public Void visit(VarDecl node) {
		String typeName = node.type.token.getValue();
		Symbol type = currentScope.lookup(typeName);
		String varName = node.var.token.getValue();
		VarSymbol varSymbol = new VarSymbol(varName, type.type);
		if (currentScope.lookup(varName, true) != null)
			error("Duplicate identifier found for '%s'", varName);
		currentScope.insert(varSymbol);
		return null;
	}
	
	@Override
	public Void visit(Var node) {
		String varName = node.token.getValue();
		Symbol varSymbol = currentScope.lookup(varName);
		currentScope.printContents();
		if (varSymbol == null) 
			error("Variable '%s' was never declared", varName);
		return null;
	}
}

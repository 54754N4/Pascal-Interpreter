package part13;

import java.util.function.BiConsumer;

import part13.ast.Var;
import part13.ast.VarDecl;
import part13.ast.Visitor;
import part13.symbols.Symbol;
import part13.symbols.SymbolTable;
import part13.symbols.VarSymbol;

public class SemanticAnalyzer implements Visitor<Void> {
	private SymbolTable symbolTable;
	
	public SemanticAnalyzer() {
		symbolTable = new SymbolTable();
	}
	
	@Override
	public Void visit(VarDecl node) {
		String typeName = node.type.token.getValue();
		Symbol type = symbolTable.lookup(typeName);
		String varName = node.var.token.getValue();
		VarSymbol varSymbol = new VarSymbol(varName, type);
		if (symbolTable.lookup(varName) != null)
			error("Duplicate identifier found for '"+varName+"'");
		symbolTable.insert(varSymbol);
		return null;
	}
	
	@Override
	public Void visit(Var node) {
		String varName = node.token.getValue();
		Symbol varSymbol = symbolTable.lookup(varName);
		if (varSymbol == null) 
			error("Variable '"+varName+"' was never declared.");
		return null;
	}
	
	public void printContents() {
		System.out.println("Symbol table contents :");
		BiConsumer<String, Symbol> symbolPrinter = (name, symbol) -> 
			System.out.printf("\t%s: %s%n", name, symbol.toString()); 
		symbolTable.symbols.forEach(symbolPrinter);
	}
}

package part12;

import java.util.function.BiConsumer;

import part12.ast.Assign;
import part12.ast.Var;
import part12.ast.VarDecl;
import part12.ast.Visitor;
import part12.symbols.Symbol;
import part12.symbols.SymbolTable;
import part12.symbols.VarSymbol;

public class SymbolTableBuilder implements Visitor<Void> {
	public final SymbolTable SYMBOL_TABLE;
	
	public SymbolTableBuilder() {
		SYMBOL_TABLE = new SymbolTable();
	}
	
	@Override
	public Void visit(VarDecl declaration) {
		String typeName = declaration.type.getValue();
		Symbol typeSymbol = SYMBOL_TABLE.lookup(typeName);
		String varName = declaration.var.token.getValue();
		VarSymbol varSymbol = new VarSymbol(varName, typeSymbol);
		SYMBOL_TABLE.define(varSymbol);
		return null;
	}
	
	@Override
	public Void visit(Assign assign) {
		String name = assign.left.token.getValue();
		if (SYMBOL_TABLE.lookup(name) == null)
			throw new IllegalArgumentException(name+" has not been defined.");
		return visit(assign.right);
	}
	
	@Override
	public Void visit(Var variable) {
		String name = variable.token.getValue();
		if (SYMBOL_TABLE.lookup(name) == null)
			throw new IllegalArgumentException(name+" has not been defined.");
		return null;
	}
	
	public void printContents() {
		System.out.println("Symbol table contents :");
		BiConsumer<String, Symbol> symbolPrinter = (name, symbol) -> 
			System.out.printf("\t%s: %s%n", name, symbol.toString()); 
		SYMBOL_TABLE.symbols.forEach(symbolPrinter);
	}
}

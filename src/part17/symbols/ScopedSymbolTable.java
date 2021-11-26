package part17.symbols;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import part17.Logger;

public class ScopedSymbolTable {
	public static final BuiltinTypeSymbol 
		INTEGER = new BuiltinTypeSymbol("INTEGER"),
		REAL = new BuiltinTypeSymbol("REAL");
	
	public final Map<String, Symbol> symbols;
	public final String scopeName;
	public final int scopeLevel;
	public final ScopedSymbolTable enclosingScope;
	
	public ScopedSymbolTable(String scopeName, int scopeLevel, ScopedSymbolTable enclosingScope) {
		symbols = new HashMap<>();
		this.scopeName = scopeName;
		this.scopeLevel = scopeLevel;
		this.enclosingScope = enclosingScope;
	}
	
	public ScopedSymbolTable initBuiltins() {
		insert(INTEGER);
		insert(REAL);
		return this;
	}
	
	public void insert(Symbol symbol) {
		Logger.log("Insert: %s (Scope: %s)", symbol.name, scopeName);
		symbols.put(symbol.name, symbol);
	}
	
	public Symbol lookup(String name) {
		return lookup(name, false);
	}
	
	public Symbol lookup(String name, boolean currentOnly) {
		Logger.log("Lookup: %s (Scope: %s)", name, scopeName);
		var symbol = symbols.get(name);
		if (symbol != null)
			return symbol;
		else if (!currentOnly && enclosingScope != null)
			return enclosingScope.lookup(name);
		return null;
	}
	
	// Debug
	
	public void printContents() {
		Logger.log("Scope: Name = %s | Level = %s | Enclosing Scope = %s", 
				scopeName, 
				scopeLevel, 
				enclosingScope == null ? null : enclosingScope.scopeName);
		Logger.log("Symbol table contents :");
		BiConsumer<String, Symbol> symbolPrinter = (name, symbol) -> 
			Logger.log("\t%s: %s", name, symbol.toString()); 
		symbols.forEach(symbolPrinter);
	}
	
	// Convenience method for nested scopes
	public ScopedSymbolTable createNested(String name) {
		return new ScopedSymbolTable(name, scopeLevel+1, this);	// this == enclosing scope
	}
}

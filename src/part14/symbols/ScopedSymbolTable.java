package part14.symbols;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

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
		System.out.printf("Insert: %s (Scope: %s)%n", symbol.name, scopeName);
		symbols.put(symbol.name, symbol);
	}
	
	public Symbol lookup(String name) {
		return lookup(name, false);
	}
	
	public Symbol lookup(String name, boolean currentOnly) {
		System.out.printf("Lookup: %s (Scope: %s)%n", name, scopeName);
		var symbol = symbols.get(name);
		if (symbol != null)
			return symbol;
		else if (!currentOnly && enclosingScope != null)
			return enclosingScope.lookup(name);
		return null;
	}
	
	// Debug
	
	public void printContents() {
		System.out.printf("Scope: Name = %s | Level = %s | Enclosing Scope = %s%n", 
				scopeName, 
				scopeLevel, 
				enclosingScope == null ? null : enclosingScope.scopeName);
		System.out.println("Symbol table contents :");
		BiConsumer<String, Symbol> symbolPrinter = (name, symbol) -> 
			System.out.printf("\t%s: %s%n", name, symbol.toString()); 
		symbols.forEach(symbolPrinter);
	}
	
	// Convenience method for nested scopes
	public ScopedSymbolTable createNested(String name) {
		return new ScopedSymbolTable(name, scopeLevel+1, this);	// this == enclosing scope
	}
}

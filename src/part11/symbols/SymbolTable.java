package part11.symbols;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
	public final Map<String, Symbol> symbols;
	
	public SymbolTable() {
		symbols = new HashMap<>();
		initBuiltins();
	}
	
	private void initBuiltins() {
		define(new BuiltinTypeSymbol("INTEGER"));
		define(new BuiltinTypeSymbol("REAL"));
	}
	
	public void define(Symbol symbol) {
		System.out.println("Defined: "+symbol);
		symbols.put(symbol.name, symbol);
	}
	
	public Symbol lookup(String name) {
		System.out.println("Lookup: "+name);
		return symbols.get(name);
	}
}

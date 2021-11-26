package part13.symbols;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
	public static final BuiltinTypeSymbol 
		INTEGER = new BuiltinTypeSymbol("INTEGER"),
		REAL = new BuiltinTypeSymbol("REAL");
	
	public final Map<String, Symbol> symbols;
	
	public SymbolTable() {
		symbols = new HashMap<>();
		initBuiltins();
	}
	
	private void initBuiltins() {
		insert(INTEGER);
		insert(REAL);
	}
	
	public void insert(Symbol symbol) {
		System.out.println("Defined: "+symbol);
		symbols.put(symbol.name, symbol);
	}
	
	public Symbol lookup(String name) {
		System.out.println("Lookup: "+name);
		return symbols.get(name);
	}
}

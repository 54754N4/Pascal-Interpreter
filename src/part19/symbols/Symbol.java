package part19.symbols;

public class Symbol {
	public final String name;
	public int scopeLevel;
	public Symbol type;
	
	public Symbol(String name, Symbol type) {
		this.name = name;
		this.type = type;
		scopeLevel = 0;
	}
	
	public Symbol(String name) {
		this(name, null);
	}
	
	@Override
	public String toString() {
		return name;
	}
}

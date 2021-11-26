package part13.symbols;

public class BuiltinTypeSymbol extends Symbol {
	public BuiltinTypeSymbol(String name) {
		super(name);
	}
	
	@Override
	public String toString() {
		return String.format("%s<%s>", getClass().getSimpleName(), name);
	}
}

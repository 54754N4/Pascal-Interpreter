package part14.symbols;

public class VarSymbol extends Symbol {
	public VarSymbol(String name, Symbol type) {
		super(name, type);
	}
	
	@Override
	public String toString() {
		return String.format("%s<%s: %s>", getClass().getSimpleName(), name, type.name);
	}
}

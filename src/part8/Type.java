package part8;

public enum Type {
	INTEGER, 
	PLUS("+"), MINUS("-"), MULTIPLY("*"), DIVIDE("/"), 
	LPAREN("("), RPAREN(")"), 
	EOF;
	
	private String representation;
	
	private Type() {
		representation = name();	// falls-back to enum name by default
	}
	
	private Type(String representation) {
		this.representation = representation;
	}
	
	@Override
	public String toString() {
		return representation;
	}
}

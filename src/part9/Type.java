package part9;

public enum Type {
	ID,
	BEGIN, END, NOP,
	DOT("."), ASSIGN(":="),
	SEMI(";"),
	
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

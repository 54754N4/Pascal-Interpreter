package part10;

public enum Type {
	ID,
	BEGIN, END, NOP,
	DOT("."), ASSIGN(":="),
	SEMI(";"),
	PROGRAM, VAR,
	COLON(":"), COMMA(","),
	// Data Types
	INTEGER, REAL,
	
	INTEGER_CONST, REAL_CONST,
	PLUS("+"), MINUS("-"), MULTIPLY("*"), FLOAT_DIVIDE("/"), INT_DIVIDE("DIV"), 
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

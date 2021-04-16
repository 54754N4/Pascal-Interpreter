package part15;

import java.util.ArrayList;
import java.util.List;

public enum Type {
	DOT("."), SEMI(";"), COLON(":"), COMMA(","),
	PLUS("+"), MINUS("-"), MULTIPLY("*"), FLOAT_DIVIDE("/"),
	LPAREN("("), RPAREN(")"),
	
	ID, INTEGER_CONST, REAL_CONST,
	ASSIGN(":="), EOF,
	
	// Insert reserved words between PROGRAM and END
	PROGRAM, 
	VAR, 
	INTEGER,
	REAL, 
	INT_DIVIDE("DIV"),
	BEGIN, 
	NOP, 
	PROCEDURE, 
	END;
	
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
	
	public static Token[] reservedWords() {
		int start = Type.PROGRAM.ordinal(),
			end = Type.END.ordinal();
		List<Token> list = new ArrayList<>();
		for (Type type : values()) 
			if (type.ordinal() >= start && type.ordinal() <= end)
				list.add(new Token(type, type.toString(), null, null));
		return list.toArray(Token[]::new);
	}
}

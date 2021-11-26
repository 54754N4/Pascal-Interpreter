package part15;

import java.util.HashMap;
import java.util.Map;

public enum Type {
	DOT("."), SEMI(";"), COLON(":"), COMMA(","),
	PLUS("+"), MINUS("-"), MULTIPLY("*"), FLOAT_DIVIDE("/"),
	LPAREN("("), RPAREN(")"),
	
	ID, INTEGER_CONST, REAL_CONST,
	ASSIGN(":="), EOF,
	
	// Insert reserved words between PROGRAM and END so Type::reservedWords works correctly
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
	
	public static Map<String, Type> reservedWords() {
		int start = Type.PROGRAM.ordinal(),
			end = Type.END.ordinal();
		Map<String, Type> dict = new HashMap<>();
		for (Type type : values()) 
			if (type.ordinal() >= start && type.ordinal() <= end)
				dict.put(type.representation, type);
		return dict;
	}
}

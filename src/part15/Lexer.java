package part15;

import java.util.HashMap;
import java.util.Map;

import part15.errors.LexerException;
import part15.errors.ParserException;

public class Lexer {
	private static final char EOF = '\0'; // null-char 
	private String text;
	private int pos, line, col;
	private char current;
	
	// Reserved keywords table
	
	public static Map<String, Token> RESERVED = new HashMap<>();
	
	static {
		RESERVED.put("NOP", new Token(Type.NOP));
		RESERVED.put("PROGRAM", new Token(Type.PROGRAM));
		RESERVED.put("VAR", new Token(Type.VAR));
		RESERVED.put("DIV", new Token(Type.INT_DIVIDE));
		RESERVED.put("INTEGER", new Token(Type.INTEGER));
		RESERVED.put("REAL", new Token(Type.REAL));
		RESERVED.put("BEGIN", new Token(Type.BEGIN));
		RESERVED.put("END", new Token(Type.END));
		RESERVED.put("PROCEDURE", new Token(Type.PROCEDURE));
	}
	
	public Lexer(String text) {
		this.text = text;
		pos = 0;
		current = text.charAt(pos);
		line = col = 1;
	}
	
	// Exception generation
	
	protected void error() throws ParserException {
		error(String.format("Lexer error on %c (line|col): (%d|%d) ", current, line, col));
	}
	
	protected void error(String message) throws ParserException {
		throw new LexerException(message);
	}
	
	// Convenience methods
	
	protected boolean peek(String target) {
		return text.substring(pos)
				.toLowerCase()
				.startsWith(target);
	}
	
	protected boolean is(char c) {
		return current == c;
	}
	
	protected boolean isFinished() {
		return is(EOF);
	}
	
	protected void advance() {
		if (is('\n')) {
			line++;
			col = 0;
		}
		pos++;
		if (pos > text.length() - 1)
			current = EOF;
		else {
			current = text.charAt(pos);
			col++;
		}
	}
	
	protected void advance(int i) {
		while (i-->0) advance();
	}
	
	protected void skipWhitespace() {
		while (!isFinished() && Character.isWhitespace(current)) {
			if (is('\n'))
				line++;
			advance();
		}
	}
	
	protected void skipComments() {
		while (!is('}')) 
			advance();
		advance();	// consume '}' too
	}
	
	/* Lexer methods */
	
	// Return a (multidigit) integer or float consumed from the input
	private Token number() {
		StringBuilder result = new StringBuilder();
		while (!isFinished() && Character.isDigit(current)) {
			result.append(current);
			advance();
		}
		Token token;
		if (is('.')) {	// e.g. is actually float
			// consume dot
			result.append(current);
			advance();
			// consume decimal part
			while (!isFinished() && Character.isDigit(current)) {
				result.append(current);
				advance();
			}
			token = new Token(Type.REAL_CONST, Double.parseDouble(result.toString()));
		} else
			token = new Token(Type.INTEGER_CONST, Integer.parseInt(result.toString()));
		return token;
	}
	
	private Token identifier() {
		StringBuilder result = new StringBuilder();
		while (!isFinished() && Character.isLetterOrDigit(current)) {
			result.append(current);
			advance();
		};
		String name = result.toString();
		return RESERVED.getOrDefault(name.toUpperCase(), new Token(Type.ID, name));
	}
	
	public Token getNextToken() throws ParserException {
		while (!isFinished()) {
			if (Character.isWhitespace(current)) {
				skipWhitespace();
				continue;
			} if (is('{')) {
				advance();
				skipComments();
				continue;
			} if (Character.isLetter(current))
				return identifier();
			if (Character.isDigit(current))
				return number();
			if (peek(":=")) {
				advance(2);
				return new Token(Type.ASSIGN);
			} if (is('+')) {
				advance();
				return new Token(Type.PLUS);
			} if (is('-')) {
				advance();
				return new Token(Type.MINUS);
			} if (is('*')) {
				advance();
				return new Token(Type.MULTIPLY);
			} if (is('/')) {
				advance();
				return new Token(Type.FLOAT_DIVIDE);
			} if (is('(')) {
				advance();
				return new Token(Type.LPAREN);
			} if (is(')')) {
				advance();
				return new Token(Type.RPAREN);
			} if (is('.')) {
				advance();
				return new Token(Type.DOT);
			} if (is(';')) {
				advance();
				return new Token(Type.SEMI);
			} if (is(':')) {
				advance();
				return new Token(Type.COLON);
			} if (is(',')) {
				advance();
				return new Token(Type.COMMA);
			}
			error("Unrecognized char : "+current);
		}
		return new Token(Type.EOF);
	}
}
package part18;

import java.util.HashMap;
import java.util.Map;

import part18.errors.LexerException;
import part18.errors.ParserException;

public class Lexer {
	private static final char EOF = '\0'; // null-char 
	private String text;
	private int pos, line, col;
	private char current;
	
	// Reserved keywords table
	
	public static Map<String, Token> RESERVED = new HashMap<>();
	
	static {
		for (Token reserved : Type.reservedWords()) 
			RESERVED.put(reserved.getType().name(), reserved);
	}
	
	public Lexer(String text) {
		this.text = text;
		pos = 0;
		current = text.charAt(pos);
		line = col = 1;
	}
	
	public char current() {
		return current;
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
		while (!isFinished() && Character.isWhitespace(current)) 
			advance();
	}
	
	protected void skipComments() {
		while (!is('}')) 
			advance();
		advance();	// consume '}' too
	}
	
	private Token createToken(Type type, Object value) {
		return new Token(type, value, line, col);
	}
	
	private Token createToken(Type type) {
		return createToken(type, type.toString());
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
			token = createToken(Type.REAL_CONST, Double.parseDouble(result.toString()));
		} else
			token = createToken(Type.INTEGER_CONST, Integer.parseInt(result.toString()));
		return token;
	}
	
	private Token identifier() {
		StringBuilder result = new StringBuilder();
		while (!isFinished() && Character.isLetterOrDigit(current)) {
			result.append(current);
			advance();
		};
		String name = result.toString();
		return RESERVED.getOrDefault(name.toUpperCase(), createToken(Type.ID, name));
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
				return createToken(Type.ASSIGN);
			} if (is('+')) {
				advance();
				return createToken(Type.PLUS);
			} if (is('-')) {
				advance();
				return createToken(Type.MINUS);
			} if (is('*')) {
				advance();
				return createToken(Type.MULTIPLY);
			} if (is('/')) {
				advance();
				return createToken(Type.FLOAT_DIVIDE);
			} if (is('(')) {
				advance();
				return createToken(Type.LPAREN);
			} if (is(')')) {
				advance();
				return createToken(Type.RPAREN);
			} if (is('.')) {
				advance();
				return createToken(Type.DOT);
			} if (is(';')) {
				advance();
				return createToken(Type.SEMI);
			} if (is(':')) {
				advance();
				return createToken(Type.COLON);
			} if (is(',')) {
				advance();
				return createToken(Type.COMMA);
			}
			error("Unrecognized char : "+current);
		}
		return createToken(Type.EOF);
	}
}
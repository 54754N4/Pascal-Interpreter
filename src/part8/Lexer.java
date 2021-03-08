package part8;

public class Lexer {
	private static final char EOF = '\0'; // null-char 
	private String text;
	private int pos, line;
	private char current;
	
	public Lexer(String text) {
		this.text = text;
		pos = line = 0;
		current = text.charAt(pos);
	}
	
	// Exception generation
	
	protected void error() throws ParsingException {
		error("Error parsing input");
	}
	
	protected void error(String message) throws ParsingException {
		throw new ParsingException(message, line, pos);
	}
	
	// Convenience methods
	
	protected boolean is(char c) {
		return current == c;
	}
	
	protected boolean isFinished() {
		return is('\0');
	}
	
	protected void advance() {
		pos++;
		if (pos > text.length() - 1)
			current = EOF;
		else 
			current = text.charAt(pos);
	}
	
	protected void skipWhitespace() {
		while (!isFinished() && Character.isWhitespace(current))
			advance();
	}
	
	protected int integer() {
		StringBuilder result = new StringBuilder();
		while (!isFinished() && Character.isDigit(current)) {
			result.append(current);
			advance();
		}
		return Integer.parseInt(result.toString());
	}
	
	public Token getNextToken() throws ParsingException {
		while (current != EOF) {
			if (Character.isWhitespace(current)) {
				skipWhitespace();
				continue;
			}
			if (Character.isDigit(current))
				return new Token(Type.INTEGER, integer());
			if (is('+')) {
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
				return new Token(Type.DIVIDE);
			} if (is('(')) {
				advance();
				return new Token(Type.LPAREN);
			} if (is(')')) {
				advance();
				return new Token(Type.RPAREN);
			}
			error("Unrecognized char : "+current);
		}
		return new Token(Type.EOF);
	}
}
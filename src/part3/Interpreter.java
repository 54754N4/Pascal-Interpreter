package part3;

public class Interpreter {
	private static final char EOF = '\0'; // null-char 
	private String text;
	private int pos, line;
	private char currentChar;
	private Token currentToken;
	
	public Interpreter(String text) {
		this.text = text;
		pos = line = 0;
		currentChar = text.charAt(pos);
	}
	
	// Exception generation
	
	protected void error() throws ParsingException {
		error("Error parsing input");
	}
	
	protected void error(String message) throws ParsingException {
		throw new ParsingException(message, line, pos);
	}
	
	// Lexing
	
	protected boolean is(char c) {
		return currentChar == c;
	}
	
	protected boolean isFinished() {
		return is('\0');
	}
	
	protected void advance() {
		pos++;
		if (pos > text.length() - 1)
			currentChar = EOF;
		else 
			currentChar = text.charAt(pos);
	}
	
	protected void skipWhitespace() {
		while (!isFinished() && Character.isWhitespace(currentChar)) {
			if (is('\n'))
				line++;
			advance();
		}
	}
	
	protected int integer() {
		StringBuilder result = new StringBuilder();
		while (!isFinished() && Character.isDigit(currentChar)) {
			result.append(currentChar);
			advance();
		}
		return Integer.parseInt(result.toString());
	}
	
	public Token getNextToken() throws ParsingException {
		while (currentChar != EOF) {
			if (Character.isWhitespace(currentChar)) {
				skipWhitespace();
				continue;
			}
			if (Character.isDigit(currentChar))
				return new Token(Type.INTEGER, integer());
			if (currentChar == '+') {
				advance();
				return new Token(Type.PLUS);
			} if (currentChar == '-') {
				advance();
				return new Token(Type.MINUS);
			}
			error("Unrecognized char : "+currentChar);
		}
		return new Token(Type.EOF);
	}
	
	// Interpreter: Primitive token handling

	protected void consume(Type type) {
		if (currentToken.getType().equals(type))
			currentToken = getNextToken();
		else
			error("Expected type : "+type);
	}
	
	private int term() {
		Token token = currentToken;
		consume(Type.INTEGER);
		return Integer.parseInt(token.getValue());
	}
	
	public int expr() {
		currentToken = getNextToken();
		Token token;
		int result = term();
		while (currentToken.getType() == Type.PLUS ||
				currentToken.getType() == Type.MINUS) {
			token = currentToken;
			switch (token.getType()) {
				case PLUS:
					consume(Type.PLUS);
					result += term();
					break;
				case MINUS:
					consume(Type.MINUS);
					result -= term();
					break;
				default:
					error("Expected + or -, got : "+token);
			}
		}
		return result;
	}
}
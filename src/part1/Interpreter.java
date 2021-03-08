package part1;

public class Interpreter {
	private String text;
	private int pos, line;
	private char currentChar;
	private Token currentToken;
	
	public Interpreter(String text) {
		this.text = text;
		pos = line = 0;
	}
	
	// Exception generation
	
	protected void error() throws ParsingException {
		error("Error parsing input");
	}
	
	protected void error(String message) throws ParsingException {
		throw new ParsingException(message, line, pos);
	}
	
	// Lexing: Primitive token parsing
	
	public Token getNextToken() throws ParsingException {
		String text = this.text;
		if (pos > text.length()-1)
			return new Token(Type.EOF);
		currentChar = text.charAt(pos);
		Token token;
		if (Character.isDigit(currentChar)) {
			token = new Token(Type.INTEGER, currentChar);
			pos++;
			return token;
		}
		if (currentChar == '+') {
			token = new Token(Type.PLUS);
			pos++;
			return token;
		}
		error();
		return null;	// unreachable
	}
	
	// Interpreter: Primitive token handling

	private void consume(Type type) {
		if (currentToken.getType().equals(type))
			currentToken = getNextToken();
		else
			error();
	}
	
	@SuppressWarnings("unused")
	public int expr() {
		currentToken = getNextToken();
		Token left = currentToken;
		consume(Type.INTEGER);
		Token op = currentToken;
		consume(Type.PLUS);
		Token right = currentToken;
		consume(Type.INTEGER);
		return Integer.parseInt(left.getValue()) + Integer.parseInt(right.getValue());
	}
}
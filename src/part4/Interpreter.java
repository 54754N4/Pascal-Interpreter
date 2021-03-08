package part4;

public class Interpreter {
	private Lexer lexer;
	private Token currentToken;
	
	public Interpreter(Lexer lexer) {
		this.lexer = lexer;
		currentToken = lexer.getNextToken();
	}

	protected void consume(Type type) {
		if (currentToken.getType().equals(type))
			currentToken = lexer.getNextToken();
		else
			lexer.error("Expected type : "+type);
	}
	
	private int factor() {
		Token token = currentToken;
		consume(Type.INTEGER);
		return Integer.parseInt(token.getValue());
	}
	
	public int expr() {
		int result = factor();
		while (currentToken.getType() == Type.MULTIPLY ||
				currentToken.getType() == Type.DIVIDE) {
			switch (currentToken.getType()) {
				case MULTIPLY:
					consume(Type.MULTIPLY);
					result *= factor();
					break;
				case DIVIDE:
					consume(Type.DIVIDE);
					result /= factor();
					break;
				default:
					lexer.error("Expected + or -, got : "+currentToken);
			}
		}
		return result;
	}
}
package part6;

public class Interpreter {
	private Lexer lexer;
	private Token currentToken;
	
	public Interpreter(Lexer lexer) {
		this.lexer = lexer;
		currentToken = lexer.getNextToken();
	}

	protected boolean is(Type...types) {
		if (currentToken == null)
			return false;
		for (Type type : types)
			if (type == currentToken.getType())
				return true;
		return false;
	} 
	
	protected void consume(Type type) {
		if (currentToken.getType().equals(type))
			currentToken = lexer.getNextToken();
		else
			lexer.error("Expected type : "+type);
	}
	
	// factor : INTEGER | LPAREN expr RPAREN
	private int factor() {
		Token token = currentToken;
		if (is(Type.INTEGER)) {
			consume(Type.INTEGER);
			return Integer.parseInt(token.getValue());
		} else if (is(Type.LPAREN)) {
			consume(Type.LPAREN);
			int result = expr();
			consume(Type.RPAREN);
			return result;
		} 
		lexer.error("Expected an integer or sub-expression.");
		return -1;	// unreachable since error throws
	}
	
	// term : factor ((MUL | DIV) factor)*
	public int term() {
		int result = factor();
		while (is(Type.MULTIPLY, Type.DIVIDE)) {
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
					lexer.error("Expected * or /, got : "+currentToken);
			}			
		}
		return result;
	}
	
	// expr   : term ((PLUS | MINUS) term)*
	public int expr() {
		int result = term();
		while (is(Type.PLUS, Type.MINUS)) {
			switch (currentToken.getType()) {
				case PLUS:
					consume(Type.PLUS);
					result += term();
					break;
				case MINUS:
					consume(Type.MINUS);
					result -= term();
					break;
				default:
					lexer.error("Expected + or -, got : "+currentToken);
			}
		}
		return result;
	}
}
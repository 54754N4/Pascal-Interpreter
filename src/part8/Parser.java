package part8;

import part8.ast.AST;
import part8.ast.BinOp;
import part8.ast.Num;
import part8.ast.UnaryOp;

public class Parser {
	private Lexer lexer;
	private Token current;
	
	public Parser(Lexer lexer) {
		this.lexer = lexer;
		current = lexer.getNextToken();
	}
	
	public Lexer getLexer() {
		return lexer;
	}
	
	protected boolean is(Type...types) {
		if (current == null)
			return false;
		for (Type type : types)
			if (type == current.getType())
				return true;
		return false;
	} 
	
	protected void consume(Type type) {
		if (current.getType().equals(type))
			current = lexer.getNextToken();
		else
			lexer.error("Expected type : "+type);
	}
	
	// factor : (PLUS | MINUS) factor | INTEGER | LPAREN expr RPAREN
	private AST factor() {
		Token token = current;
		if (is(Type.PLUS, Type.MINUS)) {
			consume(token.getType());
			return new UnaryOp(token, factor());
		} else if (is(Type.INTEGER)) {
			consume(Type.INTEGER);
			return new Num(token);
		} else if (is(Type.LPAREN)) {
			consume(Type.LPAREN);
			AST result = expr();
			consume(Type.RPAREN);
			return result;
		} 
		lexer.error("Expected an integer or sub-expression.");
		return null;	// unreachable since error throws
	}
	
	// term : factor ((MUL | DIV) factor)*
	private AST term() {
		AST result = factor();
		Token op;
		while (is(Type.MULTIPLY, Type.DIVIDE)) {
			op = current;
			consume(current.getType());
			result = new BinOp(result, op, factor());
		}
		return result;
	}
	
	// expr   : term ((PLUS | MINUS) term)*
	private AST expr() {
		AST result = term();
		Token op;
		while (is(Type.PLUS, Type.MINUS)) {
			op = current;
			consume(current.getType());
			result = new BinOp(result, op, term());
		}
		return result;
	}
	
	public AST parse() {
		return expr();	// start rule
	}
}

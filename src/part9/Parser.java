package part9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import part9.ast.AST;
import part9.ast.Assign;
import part9.ast.BinOp;
import part9.ast.Compound;
import part9.ast.NoOp;
import part9.ast.Num;
import part9.ast.UnaryOp;
import part9.ast.Var;

// Uses recursive-descent
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
	
	// Convenience methods
	
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
	
	/* Production rules */
	
	// program : compound_statement DOT
	private AST program() {
		AST node = compound_statement();
		consume(Type.DOT);
		return node;
	}
	
	// compound_statement: BEGIN statement_list END
	private AST compound_statement() {
		consume(Type.BEGIN);
		List<AST> nodes = statement_list();
		consume(Type.END);
		Compound root = new Compound();
		nodes.forEach(root.children::add);
		return root;
	}
	
	/*  statement_list : statement
     *       | statement SEMI statement_list
	 */
	private List<AST> statement_list() {
		AST node = statement();
		List<AST> results = new ArrayList<>(Arrays.asList(node));
		while (is(Type.SEMI)) {
			consume(Type.SEMI);
			results.add(statement());
		}
		if (is(Type.ID))
			getLexer().error("Invalid identifier location");
		return results;
	}

	/* statement : compound_statement
     *        | assignment_statement
     *        | empty
	 */
	private AST statement() {
		AST node;
		if (is(Type.BEGIN))
			node = compound_statement();
		else if (is(Type.ID))
			node = assignment_statement();
		else 
			node = empty();
		return node;
	}

	// assignment_statement : variable ASSIGN expr
	private AST assignment_statement() {
		AST left = variable();
		Token token = current;
		consume(Type.ASSIGN);
		AST right = expr();
		return new Assign(left, token, right);
	}
	
	// variable : ID
	private AST variable() {
		AST node = new Var(current);
		consume(Type.ID);
		return node;
	}

	// Empty production rule
	private AST empty() {
		return new NoOp();
	}

	/* factor : (PLUS|MINUS) factor
     *        | INTEGER
     *        | LPAREN expr RPAREN
     *        | variable
	 */
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
		} else 
			return variable();
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
		AST node = program();	// start rule
		if (!is(Type.EOF))
			lexer.error("Unparsed input: Didn't end with an EOF token");
		return node;
	}
}

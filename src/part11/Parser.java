package part11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import part11.ast.AST;
import part11.ast.Assign;
import part11.ast.BinOp;
import part11.ast.Block;
import part11.ast.Compound;
import part11.ast.NoOp;
import part11.ast.Num;
import part11.ast.Program;
import part11.ast.UnaryOp;
import part11.ast.Var;
import part11.ast.VarDecl;

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
	
	// block : declarations compound_statement
	private Block block() {
		return new Block(declarations(), compound_statement());
	}
	
	/* declarations : VAR (variable_declaration SEMI)+
     *              | empty 
	 */
	private List<VarDecl> declarations() {
		List<VarDecl> declarations = new ArrayList<>();
		if (is(Type.VAR)) {
			consume(Type.VAR);
			while (is(Type.ID)) {
				List<VarDecl> varDecl = variable_declaration();
				declarations.addAll(varDecl);
				consume(Type.SEMI);
			} 
		}
		return declarations;
	}

	// variable_declaration : ID (COMMA ID)* COLON type_spec
	private List<VarDecl> variable_declaration() {
		List<Var> vars = new ArrayList<>(Arrays.asList(new Var(current)));
		consume(Type.ID);
		while (is(Type.COMMA)) {
			consume(Type.COMMA);
			vars.add(new Var(current));
			consume(Type.ID);
		}
		consume(Type.COLON);
		part11.ast.Type type = type_spec();
		List<VarDecl> varDecls = new ArrayList<>();
		for (Var var : vars)
			varDecls.add(new VarDecl(var, type));
		return varDecls;
	}

	/* type_spec : INTEGER
     *           | REAL
	 */
	private part11.ast.Type type_spec() {
		Token token = current;
		if (is(Type.INTEGER))
			consume(Type.INTEGER);
		else
			consume(Type.REAL);
		return new part11.ast.Type(token);
	}

	// program : PROGRAM variable SEMI block DOT
	private AST program() {
		consume(Type.PROGRAM);
		Var var = variable();
		String name = var.token.getValue();
		consume(Type.SEMI);
		Block block = block();
		Program program = new Program(name, block);
		consume(Type.DOT);
		return program;
	}
	
	// compound_statement: BEGIN statement_list END
	private Compound compound_statement() {
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
	private Var variable() {
		Var node = new Var(current);
		consume(Type.ID);
		return node;
	}

	// Empty production rule
	private AST empty() {
		return new NoOp();
	}

	/* factor : (PLUS|MINUS) factor
     *        | INTEGER_CONST
     *        | REAL_CONST
     *        | LPAREN expr RPAREN
     *        | variable
	 */
	private AST factor() {
		Token token = current;
		if (is(Type.PLUS, Type.MINUS)) {
			consume(token.getType());
			return new UnaryOp(token, factor());
		} else if (is(Type.INTEGER_CONST)) {
			consume(Type.INTEGER_CONST);
			return new Num(token);
		} else if (is(Type.REAL_CONST)) {
			consume(Type.REAL_CONST);
			return new Num(token);
		} else if (is(Type.LPAREN)) {
			consume(Type.LPAREN);
			AST result = expr();
			consume(Type.RPAREN);
			return result;
		} else 
			return variable();
	}
	
	// term : factor ((MUL | INTEGER_DIV | FLOAT_DIV) factor)*
	private AST term() {
		AST result = factor();
		Token op;
		while (is(Type.MULTIPLY, Type.INT_DIVIDE, Type.FLOAT_DIVIDE)) {
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

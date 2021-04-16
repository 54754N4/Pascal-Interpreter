package part14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import part14.ast.AST;
import part14.ast.Assign;
import part14.ast.BinOp;
import part14.ast.Block;
import part14.ast.Compound;
import part14.ast.NoOp;
import part14.ast.Num;
import part14.ast.Param;
import part14.ast.ProcedureDecl;
import part14.ast.Program;
import part14.ast.UnaryOp;
import part14.ast.Var;
import part14.ast.VarDecl;

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
			lexer.error("Expected type : "+type+" but got : "+current.getType());
	}
	
	/* Production rules */
	
	// block : declarations compound_statement
	private Block block() {
		return new Block(declarations(), compound_statement());
	}
	
	/* declarations : (VAR (variable_declaration SEMI)+)*
     *              | (PROCEDURE ID (LPAREN formal_parameter_list RPAREN)? SEMI block SEMI)*
     *              | empty
	 */
	private List<AST> declarations() {
		List<AST> declarations = new ArrayList<>();
		while (is(Type.VAR, Type.PROCEDURE)) {
			if (is(Type.VAR)) {
				consume(Type.VAR);
				while (is(Type.ID)) {
					declarations.addAll(variable_declaration());
					consume(Type.SEMI);
				} 
			} else if (is(Type.PROCEDURE)) {
				consume(Type.PROCEDURE);
				String name = current.getValue();
				consume(Type.ID);
				List<Param> params = new ArrayList<>();
				if (is(Type.LPAREN)) {
					consume(Type.LPAREN);
					params = formal_parameter_list();
					consume(Type.RPAREN);
				}
				consume(Type.SEMI);
				declarations.add(new ProcedureDecl(name, params, block()));
				consume(Type.SEMI);
			}
		}
		return declarations;
	}
	
	/* formal_parameter_list : formal_parameters
     *                        | formal_parameters SEMI formal_parameter_list
	 */
	private List<Param> formal_parameter_list() {
		if (!is(Type.ID))
			return new ArrayList<>();
		List<Param> params = formal_parameters();
		while (is(Type.SEMI)) {
			consume(Type.SEMI);
			params.addAll(formal_parameters());
		}
		return params;
	}
	
	// formal_parameters : ID (COMMA ID)* COLON type_spec
	private List<Param> formal_parameters() {
		List<Param> params = new ArrayList<>();
		List<Token> tokens = new ArrayList<>(Arrays.asList(current));
		consume(Type.ID);
		while (is(Type.COMMA)) {
			consume(Type.COMMA);
			tokens.add(current);
			consume(Type.ID);
		}
		consume(Type.COLON);
		part14.ast.Type type = type_spec();
		for (Token token : tokens) 
			params.add(new Param(new Var(token), type));
		return params;
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
		part14.ast.Type type = type_spec();
		List<VarDecl> varDecls = new ArrayList<>();
		for (Var var : vars)
			varDecls.add(new VarDecl(var, type));
		return varDecls;
	}

	/* type_spec : INTEGER
     *           | REAL
	 */
	private part14.ast.Type type_spec() {
		Token token = current;
		if (is(Type.INTEGER))
			consume(Type.INTEGER);
		else
			consume(Type.REAL);
		return new part14.ast.Type(token);
	}

	// program : PROGRAM variable SEMI block DOT
	private AST program() {
		consume(Type.PROGRAM);
		Var var = variable();
		String name = var.token.getValue();
		consume(Type.SEMI);
		Program program = new Program(name, block());
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
		} else if (is(Type.INTEGER_CONST, Type.REAL_CONST)) {
			consume(token.getType());
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
		AST node = factor();
		Token token;
		while (is(Type.MULTIPLY, Type.INT_DIVIDE, Type.FLOAT_DIVIDE)) {
			token = current;
			consume(current.getType());
			node = new BinOp(node, token, factor());
		}
		return node;
	}
	
	// expr   : term ((PLUS | MINUS) term)*
	private AST expr() {
		AST node = term();
		Token op;
		while (is(Type.PLUS, Type.MINUS)) {
			op = current;
			consume(current.getType());
			node = new BinOp(node, op, term());
		}
		return node;
	}
	
	public AST parse() {
		AST node = program();	// start rule
		if (!is(Type.EOF))
			lexer.error("Unparsed input: Didn't end with an EOF token");
		return node;
	}
}
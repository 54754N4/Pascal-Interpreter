package part18;

import part18.ast.AST;
import part18.errors.LexerException;
import part18.errors.ParserException;
import part18.errors.SemanticException;

public class TestInterpreter {
	public static void main(String[] args) throws InterruptedException {
		String text = test0;
		// Lexical analysis
		Lexer lexer = new Lexer(text);
		Parser parser = new Parser(lexer);
		AST tree = null;
		try {
			tree = parser.parse();
		} catch (LexerException|ParserException e) {
			e.printStackTrace();
			System.exit(1);
		}
		// Semantic analysis
		SemanticAnalyzer analyzer = new SemanticAnalyzer();
		try {
			analyzer.visit(tree);
		} catch (SemanticException e) {
			e.printStackTrace();
			System.exit(1);
		}
		// Interpreting
		Interpreter interpreter = new Interpreter(tree);
		try {
			interpreter.interpret();
		} catch (SemanticException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Successful execution.");
	}
	
	public static final String
		test0 = "program Main;\r\n"
				+ "var x, y : integer;\r\n"
				+ "begin { Main }\r\n"
				+ "   y := 7;\r\n"
				+ "   x := (y + 3) * 3;\r\n"
				+ "end.  { Main }";
}
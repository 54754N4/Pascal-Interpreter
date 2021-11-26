package part16;

import part16.ast.AST;
import part16.errors.LexerException;
import part16.errors.ParserException;
import part16.errors.SemanticException;

public class TestInterpreter {
	public static void main(String[] args) throws InterruptedException {
		String text = tooManyArgs;
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
		interpreter.printContents();     // print global memory
		System.out.println("Successful execution.");
	}
	
	public static final String
		procCall = "program Main;\r\n"
				+ "\r\n"
				+ "procedure Alpha(a : integer; b : integer);\r\n"
				+ "var x : integer;\r\n"
				+ "begin\r\n"
				+ "   x := (a + b ) * 2;\r\n"
				+ "end;\r\n"
				+ "\r\n"
				+ "begin { Main }\r\n"
				+ "\r\n"
				+ "   Alpha(3 + 5, 7);  { procedure call }\r\n"
				+ "\r\n"
				+ "end.  { Main }",
		tooManyArgs = "program Main;\r\n"
				+ "\r\n"
				+ "procedure Alpha(a : integer; b : integer);\r\n"
				+ "var x : integer;\r\n"
				+ "begin\r\n"
				+ "   x := (a + b ) * 2;\r\n"
				+ "end;\r\n"
				+ "\r\n"
				+ "begin { Main }\r\n"
				+ "\r\n"
				+ "   Alpha(3 + 5, 7, 8);  { procedure call }\r\n"
				+ "\r\n"
				+ "end.  { Main }";;
}

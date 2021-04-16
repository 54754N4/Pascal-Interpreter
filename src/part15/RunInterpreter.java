package part15;

import part15.ast.AST;
import part15.errors.LexerException;
import part15.errors.ParserException;
import part15.errors.SemanticException;

public class RunInterpreter {
	public static void main(String[] args) throws InterruptedException {
		String text = RunInterpreter.executable;
		// Lexical analysis
		Lexer lexer = new Lexer(text);
		Parser parser = new Parser(lexer);
		AST tree = null;
		try {
			tree = parser.parse();
		} catch (LexerException|ParserException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		// Semantic analysis
		SemanticAnalyzer analyzer = new SemanticAnalyzer();
		try {
			analyzer.visit(tree);
		} catch (SemanticException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		// Interpreting
		Interpreter interpreter = new Interpreter(tree);
		try {
			interpreter.interpret();
		} catch (SemanticException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		interpreter.printContents();     // print global memory
		System.out.println("Successful execution.");
	}
	
	public static final String
		duplicateID = "program Main;\r\n"
				+ "var\r\n"
				+ "   a : integer;\r\n"
				+ "   a : real; { semantic error }\r\n"
				+ "\r\n"
				+ "begin { Main }\r\n"
				+ "   a := 5;\r\n"
				+ "end.  { Main }",
		idNotFound = "program Main;\r\n"
				+ "var\r\n"
				+ "   a : integer;\r\n"
				+ "\r\n"
				+ "begin { Main }\r\n"
				+ "   a := b;  { semantic error }\r\n"
				+ "end.  { Main }",
		lexerError = "program Main;\r\n"
				+ "\r\n"
				+ "begin { Main }\r\n"
				+ "   >  { lexical error }\r\n"
				+ "end.  { Main }",
		parserError = "program Main;\r\n"
				+ "var\r\n"
				+ "   a : integer;\r\n"
				+ "\r\n"
				+ "begin { Main }\r\n"
				+ "   a := 5 + ;  { syntax error}\r\n"
				+ "end.  { Main }",
		executable = "PROGRAM Part12;\r\n"
				+ "VAR\r\n"
				+ "   number : INTEGER;\r\n"
				+ "   a, b   : INTEGER;\r\n"
				+ "   y      : REAL;\r\n"
				+ "PROCEDURE P1;\r\n"
				+ "VAR\r\n"
				+ "   a : REAL;\r\n"
				+ "   k : INTEGER;\r\n"
				+ "   PROCEDURE P2;\r\n"
				+ "   VAR\r\n"
				+ "      a, z : INTEGER;\r\n"
				+ "   BEGIN {P2}\r\n"
				+ "      z := 777;\r\n"
				+ "   END;  {P2}\r\n"
				+ "BEGIN {P1}\r\n"
				+ "END;  {P1}\r\n"
				+ "BEGIN {Part12}\r\n"
				+ "   number := 2;\r\n"
				+ "   a := number ;\r\n"
				+ "   b := 10 * a + 10 * number / 4;\r\n"
				+ "   y := 20 / 7 + 3.14\r\n"
				+ "END.  {Part12}"
				;
}

package part12;

import part12.ast.AST;

public class Run {
	public static void main(String[] args) {
		String input = 
				  "PROGRAM Part12;\r\n"
				+ "VAR\r\n"
				+ "   a : INTEGER;\r\n"
				+ "\r\n"
				+ "PROCEDURE P1;\r\n"
				+ "VAR\r\n"
				+ "   a : REAL;\r\n"
				+ "   k : INTEGER;\r\n"
				+ "\r\n"
				+ "   PROCEDURE P2;\r\n"
				+ "   VAR\r\n"
				+ "      a, z : INTEGER;\r\n"
				+ "   BEGIN {P2}\r\n"
				+ "      z := 777;\r\n"
				+ "   END;  {P2}\r\n"
				+ "\r\n"
				+ "BEGIN {P1}\r\n"
				+ "\r\n"
				+ "END;  {P1}\r\n"
				+ "\r\n"
				+ "BEGIN {Part12}\r\n"
				+ "   a := 10;\r\n"
				+ "END.  {Part12}";
		Parser parser = new Parser(new Lexer(input));
		AST tree = parser.parse();
		// Symbol table validation
		SymbolTableBuilder builder = new SymbolTableBuilder();
		builder.visit(tree);
		builder.printContents();
		// Interpret
		Interpreter interpreter = new Interpreter(tree, parser);
		Double result = interpreter.interpret();
		System.out.println(result);				// output
		interpreter.printContents();
	}
}

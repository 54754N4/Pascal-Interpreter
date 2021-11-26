package part14;

import part14.ast.AST;

public class TestSemanticAnalyser {
	public static void main(String[] args) {
		Parser parser = new Parser(new Lexer(errorHere));
		AST tree = parser.parse();
		SemanticAnalyzer analyzer = new SemanticAnalyzer();
		analyzer.visit(tree);
	}
	
	public static final String
		text = "program Main;\r\n"
			+ "   var x, y : integer;\r\n"
			+ "begin\r\n"
			+ "   x := x + y;\r\n"
			+ "end.",
		dualScope = "program Main;\r\n"
				+ "   var x, y : real;\r\n"
				+ "\r\n"
				+ "   procedure AlphaA(a : integer);\r\n"
				+ "      var y : integer;\r\n"
				+ "   begin { AlphaA }\r\n"
				+ "\r\n"
				+ "   end;  { AlphaA }\r\n"
				+ "\r\n"
				+ "   procedure AlphaB(a : integer);\r\n"
				+ "      var b : integer;\r\n"
				+ "   begin { AlphaB }\r\n"
				+ "\r\n"
				+ "   end;  { AlphaB }\r\n"
				+ "\r\n"
				+ "begin { Main }\r\n"
				+ "\r\n"
				+ "end.  { Main }",
		nestedScope = "program Main;\r\n"
				+ "   var x, y: real;\r\n"
				+ "\r\n"
				+ "   procedure Alpha(a : integer);\r\n"
				+ "      var y : integer;\r\n"
				+ "   begin\r\n"
				+ "      x := a + x + y;\r\n"
				+ "   end;\r\n"
				+ "\r\n"
				+ "begin { Main }\r\n"
				+ "\r\n"
				+ "end.  { Main }",
		errorHere = "program Main;\r\n"
				+ "   var x, y: real;\r\n"
				+ "\r\n"
				+ "   procedure Alpha(a : integer);\r\n"
				+ "      var y : integer;\r\n"
				+ "   begin\r\n"
				+ "      x := b + x + y; { ERROR here! }\r\n"
				+ "   end;\r\n"
				+ "\r\n"
				+ "begin { Main }\r\n"
				+ "\r\n"
				+ "end.  { Main }";
}

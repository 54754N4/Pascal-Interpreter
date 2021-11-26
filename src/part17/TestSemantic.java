package part17;

import part17.ast.AST;

public class TestSemantic {
	public static void main(String[] args) {
		Parser parser = new Parser(new Lexer(nestedScope));
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
				+ "end.  { Main }";
}

package part15;

import part15.ast.AST;

public class TestSourceToSourceCompiler {
	
	public static void main(String[] args) {
		Parser parser = new Parser(new Lexer(test1));
		AST tree = parser.parse();
		SourceToSourceCompiler compiler = new SourceToSourceCompiler();
		System.out.println(compiler.visit(tree));
	}
	
	public static final String 
		test0 = "program Main;\r\n"
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
		test1 = "program Main;\r\n"
			+ "   var x, y : real;\r\n"
			+ "   var z : integer;\r\n"
			+ "\r\n"
			+ "   procedure AlphaA(a : integer);\r\n"
			+ "      var y : integer;\r\n"
			+ "   begin { AlphaA }\r\n"
			+ "      x := a + x + y;\r\n"
			+ "   end;  { AlphaA }\r\n"
			+ "\r\n"
			+ "   procedure AlphaB(a : integer);\r\n"
			+ "      var b : integer;\r\n"
			+ "   begin { AlphaB }\r\n"
			+ "   end;  { AlphaB }\r\n"
			+ "\r\n"
			+ "begin { Main }\r\n"
			+ "end.  { Main }",
		test2 = "program Main;\r\n"
				+ "   var b, x, y : real;\r\n"
				+ "   var z : integer;\r\n"
				+ "\r\n"
				+ "   procedure AlphaA(a : integer);\r\n"
				+ "      var b : integer;\r\n"
				+ "\r\n"
				+ "      procedure Beta(c : integer);\r\n"
				+ "         var y : integer;\r\n"
				+ "\r\n"
				+ "         procedure Gamma(c : integer);\r\n"
				+ "            var x : integer;\r\n"
				+ "         begin { Gamma }\r\n"
				+ "            x := a + b + c + x + y + z;\r\n"
				+ "         end;  { Gamma }\r\n"
				+ "\r\n"
				+ "      begin { Beta }\r\n"
				+ "\r\n"
				+ "      end;  { Beta }\r\n"
				+ "\r\n"
				+ "   begin { AlphaA }\r\n"
				+ "\r\n"
				+ "   end;  { AlphaA }\r\n"
				+ "\r\n"
				+ "   procedure AlphaB(a : integer);\r\n"
				+ "      var c : real;\r\n"
				+ "   begin { AlphaB }\r\n"
				+ "      c := a + b;\r\n"
				+ "   end;  { AlphaB }\r\n"
				+ "\r\n"
				+ "begin { Main }\r\n"
				+ "end.  { Main }";
}

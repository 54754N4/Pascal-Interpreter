package part13;

import part13.ast.AST;

public class Run {
	public static void main(String[] args) {
		Parser parser = new Parser(new Lexer(text1));
		AST tree = parser.parse();
		SemanticAnalyzer analyzer = new SemanticAnalyzer();
		analyzer.visit(tree);
		analyzer.printContents();
	}
	
	public static final String
		text1 = "PROGRAM SymTab1;\r\n"
				+ "   VAR x, y : INTEGER;\r\n"
				+ "\r\n"
				+ "BEGIN\r\n"
				+ "\r\n"
				+ "END.",
		text2 = "PROGRAM SymTab2;\r\n"
				+ "   VAR x, y : INTEGER;\r\n"
				+ "\r\n"
				+ "BEGIN\r\n"
				+ "\r\n"
				+ "END.",
		text3 = "PROGRAM SymTab3;\r\n"
				+ "   VAR x, y : INTEGER;\r\n"
				+ "\r\n"
				+ "BEGIN\r\n"
				+ "\r\n"
				+ "END.",
		text4 = "PROGRAM SymTab4;\r\n"
				+ "    VAR x, y : INTEGER;\r\n"
				+ "\r\n"
				+ "BEGIN\r\n"
				+ "    x := x + y;\r\n"
				+ "END.",
		text5 = "PROGRAM SymTab5;\r\n"			// y never declared
				+ "    VAR x : INTEGER;\r\n"
				+ "\r\n"
				+ "BEGIN\r\n"
				+ "    x := y;\r\n"
				+ "END.", 
		text6 = "PROGRAM SymTab6;\r\n"			// duplicate declaration
				+ "   VAR x, y : INTEGER;\r\n"
				+ "   VAR y : REAL;\r\n"
				+ "BEGIN\r\n"
				+ "   x := x + y;\r\n"
				+ "END.";
}

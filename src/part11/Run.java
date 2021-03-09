package part11;

import java.util.function.BiConsumer;

import part11.ast.AST;
import part11.symbols.Symbol;

@SuppressWarnings("unused")
public class Run {
	public static void main(String[] args) {
//		testInterpreter();
//		testSymbolsBuilder();
//		testError();
		testAll();
	}
	
	public static void testInterpreter() {
		String input = 
			    "PROGRAM Part10AST;\r\n"
			  + "VAR\r\n"
			  + "   a, b : INTEGER;\r\n"
			  + "   y    : REAL;\r\n"
			  + "\r\n"
			  + "BEGIN {Part10AST}\r\n"
			  + "   a := 2;\r\n"
			  + "   b := 10 * a + 10 * a DIV 4;\r\n"
			  + "   y := 20 / 7 + 3.14;\r\n"
			  + "END.  {Part10AST}";
		Interpreter interpreter = new Interpreter(new Parser(new Lexer(input)));
		Double result = interpreter.interpret();
		System.out.println(result);				// output
		// Global scope debug
		System.out.println("Global vars :");
		BiConsumer<String, Double> printer = (name, val) -> 
			System.out.printf("\t%s: %f%n", name, val); 
		interpreter.GLOBAL_SCOPE.forEach(printer);
	}
	
	public static void testSymbolsBuilder() {
		String input = 
		      "PROGRAM Part11;\r\n"
		    + "VAR\r\n"
		    + "   x : INTEGER;\r\n"
		    + "   y : REAL;\r\n"
		    + "\r\n"
		    + "BEGIN\r\n"
		    + "\r\n"
		    + "END.";
		SymbolTableBuilder builder = new SymbolTableBuilder();
		Parser parser = new Parser(new Lexer(input));
		AST tree = parser.parse();
		builder.visit(tree);
		// Global scope debug
		System.out.println("Global vars :");
		BiConsumer<String, Symbol> printer = (name, symbol) -> 
			System.out.printf("\t%s: %s%n", name, symbol.toString()); 
		builder.SYMBOL_TABLE.symbols.forEach(printer);
	}
	
	public static void testError() {
		String input1 = "PROGRAM NameError1;\r\n"	// error with b
					  + "VAR\r\n"
					  + "   a : INTEGER;\r\n"
					  + "\r\n"
					  + "\r\n"
					  + "BEGIN\r\n"
					  + "   a := 2 + b;\r\n"
					  + "END.",
			input2 = "PROGRAM NameError2;\r\n"		// error with a
					+ "VAR\r\n"
					+ "   b : INTEGER;\r\n"
					+ "\r\n"
					+ "BEGIN\r\n"
					+ "   b := 1;\r\n"
					+ "   a := b + 2;\r\n"
					+ "END.";
		SymbolTableBuilder builder = new SymbolTableBuilder();
		Parser parser = new Parser(new Lexer(input1));
		AST tree = parser.parse();
		builder.visit(tree);
		// Global scope debug
		System.out.println("Global vars :");
		BiConsumer<String, Symbol> printer = (name, symbol) -> 
			System.out.printf("\t%s: %s%n", name, symbol.toString()); 
		builder.SYMBOL_TABLE.symbols.forEach(printer);
	}
	
	public static void testAll() {
		String input = "PROGRAM Part11;\r\n"
					 + "VAR\r\n"
					 + "   number : INTEGER;\r\n"
					 + "   a, b   : INTEGER;\r\n"
					 + "   y      : REAL;\r\n"
					 + "\r\n"
					 + "BEGIN {Part11}\r\n"
					 + "   number := 2;\r\n"
					 + "   a := number ;\r\n"
					 + "   b := 10 * a + 10 * number DIV 4;\r\n"
					 + "   y := 20 / 7 + 3.14\r\n"
					 + "END.  {Part11}";
		// Symbol table validation
		SymbolTableBuilder builder = new SymbolTableBuilder();
		Parser parser = new Parser(new Lexer(input));
		AST tree = parser.parse();
		builder.visit(tree);
		System.out.println("Symbol table :");
		BiConsumer<String, Symbol> symbolPrinter = (name, symbol) -> 
			System.out.printf("\t%s: %s%n", name, symbol.toString()); 
		builder.SYMBOL_TABLE.symbols.forEach(symbolPrinter);
		// Interpret
		Interpreter interpreter = new Interpreter(new Parser(new Lexer(input)));
		Double result = interpreter.interpret();
		System.out.println(result);				// output
		// Global scope debug
		System.out.println("Global vars :");
		BiConsumer<String, Double> varPrinter = (name, val) -> 
			System.out.printf("\t%s: %f%n", name, val); 
		interpreter.GLOBAL_SCOPE.forEach(varPrinter);
	}
}

package part10;

import java.util.function.BiConsumer;

public class Run {
	public static void main(String[] args) {	
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
		System.out.println(result);
		System.out.println("Global vars :");
		BiConsumer<String, Double> printer = (name, val) -> 
			System.out.printf("\t%s: %f%n", name, val); 
		interpreter.GLOBAL_SCOPE.forEach(printer);
	}
}

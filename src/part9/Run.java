package part9;

import java.util.function.BiConsumer;

public class Run {
	public static void main(String[] args) {	
		String input = 
			  "BEGIN\r\n"
			+ "    BEGIN\r\n"
			+ "        number := 2;\r\n"
			+ "        a := number;\r\n"
			+ "        b := 10 * a + 10 * number / 4;\r\n"
			+ "        c := a - - b\r\n"
			+ "    END;\r\n"
			+ "    x := 11;\r\n"
			+ "END.";
		Interpreter interpreter = new Interpreter(new Parser(new Lexer(input)));
		Double result = interpreter.interpret();
		System.out.println(result);
		System.out.println("Global vars :");
		BiConsumer<String, Double> printer = (name, val) -> 
			System.out.printf("\t%s: %f%n", name, val); 
		interpreter.GLOBAL_SCOPE.forEach(printer);
	}
}

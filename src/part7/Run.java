package part7;

import java.util.Scanner;

public class Run {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			while (true) {
				System.out.print("calc> ");
				String input = sc.nextLine();
				Interpreter interpreter = new Interpreter(new Parser(new Lexer(input)));
				double result = interpreter.interpret();
				System.out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} finally {
			sc.close();
		}
	}
}

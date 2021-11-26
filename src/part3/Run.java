package part3;

import java.util.Scanner;

public class Run {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			while (true) {
				System.out.print("calc> ");
				String input = sc.nextLine();
				Interpreter interpreter = new Interpreter(input);
				int result = interpreter.expr();
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

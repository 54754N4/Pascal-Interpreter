package part15;

public class Logger {
	private static boolean _SHOULD_LOG_SCOPE = false;
	
	public static void log(String message, Object...args) {
		if (_SHOULD_LOG_SCOPE)
			System.out.printf(message+"%n", args);
	}
}

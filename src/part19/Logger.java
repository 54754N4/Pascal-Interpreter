package part19;

public class Logger {
	private static boolean _SHOULD_LOG_SCOPE = false,
			_SHOULD_LOG_STACK = true;
	
	public static void log(Object message, Object...args) {
		if (_SHOULD_LOG_SCOPE)
			System.out.printf(message+"%n", args);
	}
	
	public static void logs(Object message, Object...args) {
		if (_SHOULD_LOG_STACK)
			System.out.printf(message+"%n", args);
	}
}

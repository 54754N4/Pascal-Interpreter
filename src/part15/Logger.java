package part15;

public class Logger {
	private boolean _SHOULD_LOG_SCOPE = true;
	
	public Logger log(String message, Object...args) {
		if (_SHOULD_LOG_SCOPE)
			System.out.printf(message+"%n", args);
		return this;
	}
}

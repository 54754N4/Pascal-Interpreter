package part15.errors;

public class LexerException extends RuntimeException {
	private static final long serialVersionUID = 5658901637650178863L;

	public LexerException(String message) {
		super(message);
	}
}
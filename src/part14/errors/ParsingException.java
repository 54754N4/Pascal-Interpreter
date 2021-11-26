package part14.errors;

public class ParsingException extends RuntimeException {
	private static final long serialVersionUID = 5658901637650178863L;

	public ParsingException(String message, int line, int pos) {
		super(String.format("Error@%d:%d: %s", line, pos, message));
	}
}

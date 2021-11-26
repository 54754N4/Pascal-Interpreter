package part14.errors;

public class SemanticException extends RuntimeException {
	private static final long serialVersionUID = -6266327354745625842L;

	public SemanticException(String message) {
		super(message);
	}
}

package part15.errors;

public enum ErrorCode {
	UNEXPECTED_TOKEN("Unexpected token -> %s"),
	ID_NOT_FOUND("Identifier not found -> %s"),
	DUPLICATE_ID("Duplicate id found -> %s");
	
	public final String message;
	
	private ErrorCode(String message) {
		this.message = message;
	}
}

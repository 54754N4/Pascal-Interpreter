package part15.errors;

import part15.Token;

public class SemanticException extends TokenizedException {
	private static final long serialVersionUID = -6266327354745625842L;

	public SemanticException(ErrorCode errorCode, Token token, String message) {
		super(errorCode, token, message);
	}
	
	public SemanticException(String message) {
		super(message);
	}
}

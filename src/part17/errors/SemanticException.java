package part17.errors;

import part17.Token;

public class SemanticException extends RuntimeException {
	private static final long serialVersionUID = -6266327354745625842L;
	public final ErrorCode code;
	public final Token token;
	
	public SemanticException(ErrorCode code, Token token, String message) {
		super(message);
		this.code = code;
		this.token = token;
	}
}

package part15.errors;

import part15.Token;

public class TokenizedException extends RuntimeException {
	private static final long serialVersionUID = 5658901637650178863L;
	public final ErrorCode errorCode;
	public final Token token;
	public final String message;
	
	public TokenizedException(ErrorCode errorCode, Token token, String message) {
		this.errorCode = errorCode;
		this.token = token;
		this.message = message;
	}
	
	public TokenizedException(String message) {
		this(null, null, message);
	}
}
package part17.errors;

import part17.Token;

public class ParserException extends RuntimeException {
	private static final long serialVersionUID = 5658901637650178863L;
	public final ErrorCode errorCode;
	public final Token token;
	
	public ParserException(ErrorCode errorCode, Token token, String message) {
		super(message);
		this.errorCode = errorCode;
		this.token = token;
	}
}
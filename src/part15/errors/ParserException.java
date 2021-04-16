package part15.errors;

import part15.Token;

public class ParserException extends TokenizedException {
	private static final long serialVersionUID = 5658901637650178863L;

	public ParserException(ErrorCode errorCode, Token token, String message) {
		super(errorCode, token, message);
	}
	
	public ParserException(String message) {
		super(message);
	}
}
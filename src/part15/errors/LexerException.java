package part15.errors;

import part15.Token;

public class LexerException extends TokenizedException {
	private static final long serialVersionUID = 5658901637650178863L;

	public LexerException(ErrorCode errorCode, Token token, String message) {
		super(errorCode, token, message);
	}
	
	public LexerException(String message) {
		super(message);
	}
}
package part16.ast;

import part16.Token;

public class Type extends AST {
	public Type(Token token) {
		super(token);
	}

	public String getValue() {
		return token.getValue();
	}
}

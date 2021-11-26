package part17.ast;

import part17.Token;

public class Type extends AST {
	public Type(Token token) {
		super(token);
	}

	public String getValue() {
		return token.getValue();
	}
}

package part13.ast;

import part13.Token;

public class Type extends AST {
	public Type(Token token) {
		super(token);
	}

	public String getValue() {
		return token.getValue();
	}
}

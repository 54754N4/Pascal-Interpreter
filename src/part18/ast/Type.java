package part18.ast;

import part18.Token;

public class Type extends AST {
	public Type(Token token) {
		super(token);
	}

	public String getValue() {
		return token.getValue();
	}
}

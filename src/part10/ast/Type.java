package part10.ast;

import part10.Token;

public class Type extends AST {
	public Type(Token token) {
		super(token);
	}

	public String getValue() {
		return token.getValue();
	}
}

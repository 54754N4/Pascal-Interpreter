package part18.ast;

import part18.Token;

public class Var extends AST {
	public Var(Token token) {
		super(token);
	}

	public double getValue() {
		return Double.parseDouble(token.getValue());
	}
}

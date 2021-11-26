package part9.ast;

import part9.Token;

public class Var extends AST {
	public Var(Token token) {
		super(token);
	}

	public double getValue() {
		return Double.parseDouble(token.getValue());
	}
}

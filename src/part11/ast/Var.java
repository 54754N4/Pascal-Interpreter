package part11.ast;

import part11.Token;

public class Var extends AST {
	public Var(Token token) {
		super(token);
	}

	public double getValue() {
		return Double.parseDouble(token.getValue());
	}
}

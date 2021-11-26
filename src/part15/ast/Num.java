package part15.ast;

import part15.Token;

public class Num extends AST {
	public Num(Token token) {
		super(token);
	}
	
	public double getValue() {
		return Double.parseDouble(token.getValue());
	}
}

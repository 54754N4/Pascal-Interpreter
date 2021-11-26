package part17.ast;

import part17.Token;

public class Num extends AST {
	public Num(Token token) {
		super(token);
	}
	
	public double getValue() {
		return Double.parseDouble(token.getValue());
	}
}

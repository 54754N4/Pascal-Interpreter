package part10.ast;

import part10.Token;

public class Num extends AST {
	public Num(Token token) {
		super(token);
	}
	
	public double getValue() {
		return Double.parseDouble(token.getValue());
	}
}

package part7.ast;

import part7.Token;

public class Num extends AST {
	public Num(Token token) {
		super(token);
	}
	
	public double getValue() {
		return Double.parseDouble(op.getValue());
	}
}

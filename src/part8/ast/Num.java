package part8.ast;

import part8.Token;

public class Num extends AST {
	public Num(Token token) {
		super(token);
	}
	
	public double getValue() {
		return Double.parseDouble(op.getValue());
	}
}

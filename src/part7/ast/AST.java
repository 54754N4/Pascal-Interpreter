package part7.ast;

import part7.Token;

public abstract class AST implements Visitable<Double> {
	public final Token op;
	
	protected AST(Token token) {
		this.op = token;
	}

	@Override
	public Double accept(Visitor<Double> visitor) {
		return visitor.visit(this);
	}
}

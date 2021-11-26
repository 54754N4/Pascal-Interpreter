package part13.ast;

import part13.Token;

public abstract class AST implements Visitable<Double> {
	public final Token token;
	
	public AST(Token token) {
		this.token = token;
	}

	@Override
	public Double accept(Visitor<Double> visitor) {
		return visitor.visit(this);
	}
}

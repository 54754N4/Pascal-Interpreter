package part11.ast;

import part11.Token;

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

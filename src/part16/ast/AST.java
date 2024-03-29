package part16.ast;

import part16.Token;

public abstract class AST implements Visitable<Double> {
	public final Token token;
	
	public AST(Token token) {
		this.token = token;
	}

	@Override
	public Double accept(Visitor<Double> visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return token.getValue();
	}
}

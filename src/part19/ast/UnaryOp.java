package part19.ast;

import part19.Token;

public class UnaryOp extends AST {
	public AST expr;
	
	public UnaryOp(Token op, AST expr) {
		super(op);
		this.expr = expr;
	}
}

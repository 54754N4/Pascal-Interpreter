package part8.ast;

import part8.Token;

public class UnaryOp extends AST {
	public AST expr;
	
	public UnaryOp(Token op, AST expr) {
		super(op);
		this.expr = expr;
	}
}

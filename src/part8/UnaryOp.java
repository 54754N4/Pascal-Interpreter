package part8;

import part8.ast.AST;

public class UnaryOp extends AST {
	public AST expr;
	
	protected UnaryOp(Token op, AST expr) {
		super(op);
		this.expr = expr;
	}
}

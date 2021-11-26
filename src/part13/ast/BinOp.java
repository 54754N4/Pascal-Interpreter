package part13.ast;

import part13.Token;

public class BinOp extends AST {
	public AST left, right;
	
	public BinOp(AST left, Token op, AST right) {
		super(op);
		this.left = left;
		this.right = right;
	}
}

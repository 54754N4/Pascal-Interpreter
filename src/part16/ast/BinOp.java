package part16.ast;

import part16.Token;

public class BinOp extends AST {
	public AST left, right;
	
	public BinOp(AST left, Token op, AST right) {
		super(op);
		this.left = left;
		this.right = right;
	}
}

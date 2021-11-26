package part18.ast;

import part18.Token;

public class Assign extends AST {
	public AST left, right;
	
	public Assign(AST left, Token token, AST right) {
		super(token);
		this.left = left;
		this.right = right;
	}
}

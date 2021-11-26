package part9.ast;

import part9.Token;

public class Assign extends AST {
	public AST left, right;
	
	public Assign(AST left, Token token, AST right) {
		super(token);
		this.left = left;
		this.right = right;
	}
}

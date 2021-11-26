package part17.ast;

import part17.Token;

public class Assign extends AST {
	public AST left, right;
	
	public Assign(AST left, Token token, AST right) {
		super(token);
		this.left = left;
		this.right = right;
	}
}

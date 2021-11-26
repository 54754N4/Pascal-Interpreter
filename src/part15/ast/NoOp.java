package part15.ast;

import part15.Token;

public class NoOp extends AST {
	public NoOp() {
		super(new Token(part15.Type.NOP, "NOP", -1, -1));
	}
}

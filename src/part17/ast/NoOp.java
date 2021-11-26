package part17.ast;

import part17.Lexer;

public class NoOp extends AST {
	public NoOp() {
		super(Lexer.RESERVED.get("NOP"));
	}
}

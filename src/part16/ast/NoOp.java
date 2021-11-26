package part16.ast;

import part16.Lexer;

public class NoOp extends AST {
	public NoOp() {
		super(Lexer.RESERVED.get("NOP"));
	}
}

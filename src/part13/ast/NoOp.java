package part13.ast;

import part13.Lexer;

public class NoOp extends AST {
	public NoOp() {
		super(Lexer.RESERVED.get("NOP"));
	}
}

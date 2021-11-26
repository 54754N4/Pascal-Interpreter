package part18.ast;

import part18.Lexer;

public class NoOp extends AST {
	public NoOp() {
		super(Lexer.RESERVED.get("NOP"));
	}
}

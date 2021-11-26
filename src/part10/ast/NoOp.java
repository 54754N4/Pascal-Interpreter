package part10.ast;

import part10.Lexer;

public class NoOp extends AST {
	public NoOp() {
		super(Lexer.RESERVED.get("NOP"));
	}
}

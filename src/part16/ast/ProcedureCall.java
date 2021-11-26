package part16.ast;

import java.util.List;

import part16.Token;

public class ProcedureCall extends AST {
	public String name;
	public List<AST> params;
	
	public ProcedureCall(String name, List<AST> params, Token token) {
		super(token);
		this.name = name;
		this.params = params;
	}
}

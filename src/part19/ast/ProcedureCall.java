package part19.ast;

import java.util.List;

import part19.Token;
import part19.symbols.ProcedureSymbol;

public class ProcedureCall extends AST {
	public String name;
	public List<AST> params;
	public ProcedureSymbol procSymbol;
	
	public ProcedureCall(String name, List<AST> params, Token token) {
		super(token);
		this.name = name;
		this.params = params;
	}
}

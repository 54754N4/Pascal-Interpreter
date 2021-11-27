package part19.symbols;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import part19.ast.Block;

public class ProcedureSymbol extends Symbol {
	public final List<VarSymbol> params;
	public Block block;	// points to AST subtree of procedure body
	
	public ProcedureSymbol(String name, VarSymbol...params) {
		super(name);
		this.params = new ArrayList<>();
		for (var symbol : params)
			this.params.add(symbol);
	}
	
	@Override
	public String toString() {
		return String.format("<Procedure:%s(%s)>", name, Arrays.toString(params.toArray()));
	}
}

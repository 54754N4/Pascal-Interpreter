package part17.symbols;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcedureSymbol extends Symbol {
	public final List<VarSymbol> params;
	
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

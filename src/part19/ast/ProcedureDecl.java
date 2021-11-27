package part19.ast;

import java.util.List;

public class ProcedureDecl extends AST {
	public String name;
	public List<Param> params;
	public Block block;
	
	public ProcedureDecl(String name, List<Param> params, Block block) {
		super(null);
		this.name = name;
		this.params = params;
		this.block = block;
	}
}

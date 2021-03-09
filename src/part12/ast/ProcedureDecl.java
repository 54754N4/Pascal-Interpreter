package part12.ast;

public class ProcedureDecl extends AST {
	public String name;
	public Block block;
	
	public ProcedureDecl(String name, Block block) {
		super(null);
		this.name = name;
		this.block = block;
	}
}

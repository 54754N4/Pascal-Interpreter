package part11.ast;

public class Program extends AST {
	public String name;
	public Block block;
	
	public Program(String name, Block block) {
		super(null);
		this.name = name;
		this.block = block;
	}
}

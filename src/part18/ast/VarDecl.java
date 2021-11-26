package part18.ast;

public class VarDecl extends AST {
	public Var var;
	public Type type;
	
	public VarDecl(Var var, Type type) {
		super(null);
		this.var = var;
		this.type = type;
	}
}

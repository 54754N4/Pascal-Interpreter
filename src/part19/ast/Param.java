package part19.ast;

public class Param extends AST {
	public final Var var;
	public final Type type;
	
	public Param(Var var, Type type) {
		super(null);
		this.var = var;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", var, type);
	}
}

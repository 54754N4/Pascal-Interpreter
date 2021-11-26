package part10.ast;

import java.util.List;

public class Block extends AST {
	public List<VarDecl> declarations;
	public Compound compoundStatement;
	
	public Block(List<VarDecl> declarations, Compound compoundStatement) {
		super(null);
		this.declarations = declarations;
		this.compoundStatement = compoundStatement; 
	}
}

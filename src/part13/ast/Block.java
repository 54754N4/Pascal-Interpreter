package part13.ast;

import java.util.List;

public class Block extends AST {
	public List<AST> declarations;
	public Compound compoundStatement;
	
	public Block(List<AST> declarations, Compound compoundStatement) {
		super(null);
		this.declarations = declarations;
		this.compoundStatement = compoundStatement; 
	}
}

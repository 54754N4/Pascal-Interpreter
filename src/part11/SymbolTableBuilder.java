package part11;

import part11.ast.AST;
import part11.ast.Assign;
import part11.ast.BinOp;
import part11.ast.Block;
import part11.ast.Compound;
import part11.ast.NoOp;
import part11.ast.Num;
import part11.ast.Program;
import part11.ast.Type;
import part11.ast.UnaryOp;
import part11.ast.Var;
import part11.ast.VarDecl;
import part11.ast.Visitor;
import part11.symbols.Symbol;
import part11.symbols.SymbolTable;
import part11.symbols.VarSymbol;

public class SymbolTableBuilder implements Visitor<Void> {
	public final SymbolTable SYMBOL_TABLE;
	
	public SymbolTableBuilder() {
		SYMBOL_TABLE = new SymbolTable();
	}
	
	@Override
	public Void visit(Block block) {
		for (VarDecl declaration : block.declarations)
			visit(declaration);
		return visit(block.compoundStatement);
	}
	
	@Override
	public Void visit(Program program) {
		return visit(program.block);
	}
	
	@Override
	public Void visit(BinOp binOp) {
		visit(binOp.left);
		visit(binOp.right);
		return null;
	}

	@Override
	public Void visit(UnaryOp unaryOp) {
		return visit(unaryOp.expr);
	}

	@Override
	public Void visit(Compound compound) {
		for (AST child : compound.children)
			visit(child);
		return null;
	}
	
	@Override
	public Void visit(VarDecl declaration) {
		String typeName = declaration.type.getValue();
		Symbol typeSymbol = SYMBOL_TABLE.lookup(typeName);
		String varName = declaration.var.token.getValue();
		VarSymbol varSymbol = new VarSymbol(varName, typeSymbol);
		SYMBOL_TABLE.define(varSymbol);
		return null;
	}
	
	@Override
	public Void visit(Assign assign) {
		String name = assign.left.token.getValue();
		if (SYMBOL_TABLE.lookup(name) == null)
			throw new IllegalArgumentException(name+" has not been defined.");
		return visit(assign.right);
	}
	
	@Override
	public Void visit(Var variable) {
		String name = variable.token.getValue();
		if (SYMBOL_TABLE.lookup(name) == null)
			throw new IllegalArgumentException(name+" has not been defined.");
		return null;
	}
	
	@Override
	public Void visit(Num num) {
		return null;
	}
	
	@Override
	public Void visit(NoOp nop) {
		return null;
	}

	@Override
	public Void visit(Type type) {
		return null;
	}
}

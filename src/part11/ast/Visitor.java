package part11.ast;

public interface Visitor<V> {
	V visit(Num num);
	V visit(BinOp binOp);
	V visit(UnaryOp unaryOp);
	V visit(Compound compound);
	V visit(Assign assign);
	V visit(Var variable);
	V visit(NoOp nop);
	V visit(Program program);
	V visit(Block block);
	V visit(VarDecl declaration);
	V visit(Type type);
	
	// Visits dispatcher
	default public V visit(AST ast) {
		if (Num.class.isInstance(ast))
			return visit(Num.class.cast(ast));
		else if (BinOp.class.isInstance(ast))
			return visit(BinOp.class.cast(ast));
		else if (UnaryOp.class.isInstance(ast))
			return visit(UnaryOp.class.cast(ast));
		else if (Compound.class.isInstance(ast))
			return visit(Compound.class.cast(ast));
		else if (Assign.class.isInstance(ast))
			return visit(Assign.class.cast(ast));
		else if (Var.class.isInstance(ast))
			return visit(Var.class.cast(ast));
		else if (NoOp.class.isInstance(ast))
			return visit(NoOp.class.cast(ast));
		else if (Program.class.isInstance(ast))
			return visit(Program.class.cast(ast));
		else if (Block.class.isInstance(ast))
			return visit(Block.class.cast(ast));
		else if (VarDecl.class.isInstance(ast))
			return visit(VarDecl.class.cast(ast));
		else if (Type.class.isInstance(ast))
			return visit(Type.class.cast(ast));
		// Unhandled type
		throw new IllegalStateException(
			"Unimplemented if branch for type: "
			+ ast.getClass());
	}
}

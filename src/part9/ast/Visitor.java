package part9.ast;

public interface Visitor<V> {
	V visit(Num num);
	V visit(BinOp binOp);
	V visit(UnaryOp unaryOp);
	V visit(Compound compound);
	V visit(Assign assign);
	V visit(Var variable);
	V visit(NoOp nop);
	
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
		// Unhandled type
		throw new IllegalStateException(
			"Unimplemented if branch for type: "
			+ ast.getClass());
	}
}

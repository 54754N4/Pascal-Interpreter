package part8.ast;

public interface Visitor<V> {
	V visit(Num num);
	V visit(BinOp binOp);
	V visit(UnaryOp unaryOp);
	
	// Visits dispatcher
	default public V visit(AST ast) {
		if (Num.class.isInstance(ast))
			return visit(Num.class.cast(ast));
		else if (BinOp.class.isInstance(ast))
			return visit(BinOp.class.cast(ast));
		else if (UnaryOp.class.isInstance(ast))
			return visit(UnaryOp.class.cast(ast));
		// Unhandled type
		throw new IllegalStateException(
			"Unimplemented if branch for type: "
			+ ast.getClass());
	}
}

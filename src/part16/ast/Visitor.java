package part16.ast;

import part16.Token;
import part16.errors.ErrorCode;
import part16.errors.SemanticException;

public interface Visitor<V> {
	default V visit(BinOp op) {
		visit(op.left);
		visit(op.right);
		return null;
	}
	default V visit(UnaryOp unaryOp) {
		visit(unaryOp.expr);
		return null;
	}
	default V visit(Compound compound) {
		for (AST child : compound.children)
			visit(child);
		return null;
	}
	default V visit(Assign assign) {
		visit(assign.left);
		visit(assign.right);
		return null;
	}
	default V visit(Program program) {
		return visit(program.block);
	}
	default V visit(Block block) {
		for (AST d : block.declarations)
			visit(d);
		visit(block.compoundStatement);
		return null;
	}
	default V visit(VarDecl declaration) {
		visit(declaration.var);
		visit(declaration.type);
		return null;
	}
	default V visit(ProcedureDecl proc) {
		visit(proc.block);
		return null;
	}
	default V visit(Var variable)  {
		return null;
	}
	default V visit(NoOp nop) {
		return null;
	}
	default V visit(Type type) {
		return null;
	}
	default V visit(Num num) {
		return null;
	}
	default V visit(ProcedureCall call) {
		for (AST param : call.params)
			visit(param);
		return null;
	}
	
	// Visits dispatcher
	default V visit(AST ast) {
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
		else if (ProcedureDecl.class.isInstance(ast))
			return visit(ProcedureDecl.class.cast(ast));
		else if (ProcedureCall.class.isInstance(ast))
			return visit(ProcedureCall.class.cast(ast));
		// Unhandled type
		throw new IllegalStateException(
			"Unimplemented if branch for type: "
			+ ast.getClass());
	}
	
	default public <T> T error(ErrorCode code, Token token) {
		throw new SemanticException(code, token, String.format("%s -> %s%n", code.message, token));
	}
	
	default public <T> T error(ErrorCode code, Token token, String message) {
		throw new SemanticException(code, token, String.format("%s -> %s%n%s", code.message, token, message));
	}
}

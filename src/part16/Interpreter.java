package part16;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import part16.ast.AST;
import part16.ast.Assign;
import part16.ast.BinOp;
import part16.ast.Num;
import part16.ast.ProcedureCall;
import part16.ast.ProcedureDecl;
import part16.ast.UnaryOp;
import part16.ast.Var;
import part16.ast.VarDecl;
import part16.ast.Visitor;
import part16.errors.ErrorCode;

public class Interpreter implements Visitor<Double> {
	public final Map<String, Double> GLOBAL_MEMORY;
	private AST tree;
	
	public Interpreter(AST tree) {
		this.tree = tree;
		GLOBAL_MEMORY = new HashMap<>();
	}

	public Double interpret() {
		return visit(tree);
	}
	
	@Override
	public Double visit(Num num) {
		return num.getValue();
	}

	@Override
	public Double visit(BinOp binOp) {
		switch (binOp.token.getType()) {
			case PLUS: return visit(binOp.left) + visit(binOp.right);
			case MINUS: return visit(binOp.left) - visit(binOp.right);
			case MULTIPLY: return visit(binOp.left) * visit(binOp.right);
			case FLOAT_DIVIDE: return visit(binOp.left) / visit(binOp.right);
			case INT_DIVIDE: return (double) (visit(binOp.left).intValue() / visit(binOp.right).intValue());
			default:
				error(ErrorCode.UNEXPECTED_TOKEN, binOp.token);
				return null;	// unreachable
		}
	}

	@Override
	public Double visit(UnaryOp unaryOp) {
		return unaryOp.token.getType() == Type.PLUS ?
			visit(unaryOp.expr) :
			-visit(unaryOp.expr);
	}

	@Override
	public Double visit(Assign assign) {
		String varName = assign.left.token.getValue();
		return GLOBAL_MEMORY.put(varName, visit(assign.right));
	}
	
	@Override
	public Double visit(VarDecl decl) {
		return null;
	}
	
	@Override
	public Double visit(ProcedureCall node) {
		return null;
	}
	
	@Override
	public Double visit(ProcedureDecl node) {
		return null;
	}
	
	@Override
	public Double visit(Var variable) {
		String varName = variable.token.getValue();
		Double value = GLOBAL_MEMORY.get(varName);
		if (value == null)
			error(ErrorCode.UNINITIALIZED_VAR, variable.token);
		return value;
	}
	
	public void printContents() {
		System.out.println("Run-time global memory contents:");
		BiConsumer<String, Double> varPrinter = (name, val) -> 
			System.out.printf("\t%s: %f%n", name, val); 
		GLOBAL_MEMORY.forEach(varPrinter);
	}
}
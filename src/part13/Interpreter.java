package part13;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import part13.ast.AST;
import part13.ast.Assign;
import part13.ast.BinOp;
import part13.ast.Num;
import part13.ast.UnaryOp;
import part13.ast.Var;
import part13.ast.VarDecl;
import part13.ast.Visitor;

public class Interpreter implements Visitor<Double> {
	public final Map<String, Double> GLOBAL_MEMORY;
	private AST tree;
	private Parser parser;
	
	public Interpreter(AST tree, Parser parser) {
		this.tree = tree;
		this.parser = parser;
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
				parser.getLexer()
					.error("Unhandled binary operator type : "+binOp.token);
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
	public Double visit(Var variable) {
		String varName = variable.token.getValue();
		Double value = GLOBAL_MEMORY.get(varName);
		if (value == null)
			parser.getLexer().error("NameError: Variable "+varName+" has not been initialized");
		return value;
	}
	
	@Override
	public Double visit(VarDecl declaration) {	// don't visit children
		return null;
	}
	
	public void printContents() {
		System.out.println("Run-time global memory contents:");
		BiConsumer<String, Double> varPrinter = (name, val) -> 
			System.out.printf("\t%s: %f%n", name, val); 
		GLOBAL_MEMORY.forEach(varPrinter);
	}
}
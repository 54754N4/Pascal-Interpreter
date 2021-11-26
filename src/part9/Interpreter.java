package part9;

import java.util.HashMap;
import java.util.Map;

import part9.ast.AST;
import part9.ast.Assign;
import part9.ast.BinOp;
import part9.ast.Compound;
import part9.ast.NoOp;
import part9.ast.Num;
import part9.ast.UnaryOp;
import part9.ast.Var;
import part9.ast.Visitor;

public class Interpreter implements Visitor<Double> {
	public final Map<String, Double> GLOBAL_SCOPE;
	private Parser parser;
	
	public Interpreter(Parser parser) {
		this.parser = parser; 
		GLOBAL_SCOPE = new HashMap<>();
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
			case DIVIDE: return visit(binOp.left) / visit(binOp.right);
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
	public Double visit(Compound compound) {
		Double last = null;
		for (AST child : compound.children) 
			last = visit(child);
		return last;
	}

	@Override
	public Double visit(Assign assign) {
		String varName = assign.left.token.getValue();
		return GLOBAL_SCOPE.put(varName, visit(assign.right));
	}

	@Override
	public Double visit(Var variable) {
		String varName = variable.token.getValue();
		Double value = GLOBAL_SCOPE.get(varName);
		if (value == null)
			parser.getLexer().error("NameError: Variable has not been initialized");
		return value;
	}

	@Override
	public Double visit(NoOp nop) {
		return null;
	}
	
	public Double interpret() {
		return visit(parser.parse());
	}
}
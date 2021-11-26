package part11;

import java.util.HashMap;
import java.util.Map;

import part11.ast.AST;
import part11.ast.Assign;
import part11.ast.BinOp;
import part11.ast.Block;
import part11.ast.Compound;
import part11.ast.NoOp;
import part11.ast.Num;
import part11.ast.Program;
import part11.ast.UnaryOp;
import part11.ast.Var;
import part11.ast.VarDecl;
import part11.ast.Visitor;

public class Interpreter implements Visitor<Double> {
	public final Map<String, Double> GLOBAL_SCOPE;
	private Parser parser;
	
	public Interpreter(Parser parser) {
		this.parser = parser; 
		GLOBAL_SCOPE = new HashMap<>();
	}

	public Double interpret() {
		return visit(parser.parse());
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

	@Override
	public Double visit(Program program) {
		return visit(program.block);
	}

	@Override
	public Double visit(Block block) {
		for (VarDecl declaration : block.declarations)
			visit(declaration);
		return visit(block.compoundStatement);
	}

	@Override
	public Double visit(VarDecl declaration) {
		return null;
	}

	@Override
	public Double visit(part11.ast.Type type) {
		return null;
	}
}
package part8;

import part8.ast.BinOp;
import part8.ast.Num;
import part8.ast.UnaryOp;
import part8.ast.Visitor;

public class Interpreter implements Visitor<Double> {
	private Parser parser;
	
	public Interpreter(Parser parser) {
		this.parser = parser; 
	}

	@Override
	public Double visit(Num num) {
		return num.getValue();
	}

	@Override
	public Double visit(BinOp binOp) {
		switch (binOp.op.getType()) {
			case PLUS: return visit(binOp.left) + visit(binOp.right);
			case MINUS: return visit(binOp.left) - visit(binOp.right);
			case MULTIPLY: return visit(binOp.left) * visit(binOp.right);
			case DIVIDE: return visit(binOp.left) / visit(binOp.right);
			default:
				parser.getLexer()
					.error("Unhandled binary operator type : "+binOp.op);
				return null;	// unreachable
		}
	}

	@Override
	public Double visit(UnaryOp unaryOp) {
		return unaryOp.op.getType() == Type.PLUS ?
			visit(unaryOp.expr) :
			-visit(unaryOp.expr);
	}
	
	public double interpret() {
		return visit(parser.parse());
	}
}
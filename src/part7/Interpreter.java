package part7;

import part7.ast.BinOp;
import part7.ast.Num;
import part7.ast.Visitor;

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

	public double interpret() {
		return visit(parser.parse());
	}
}
package part18;

import part18.ast.AST;
import part18.ast.Assign;
import part18.ast.BinOp;
import part18.ast.Num;
import part18.ast.ProcedureCall;
import part18.ast.ProcedureDecl;
import part18.ast.Program;
import part18.ast.UnaryOp;
import part18.ast.Var;
import part18.ast.VarDecl;
import part18.ast.Visitor;
import part18.errors.ErrorCode;

public class Interpreter implements Visitor<Double> {
	public final CallStack callStack;
	private AST tree;
	
	public Interpreter(AST tree) {
		this.tree = tree;
		callStack = new CallStack();
	}

	public Double interpret() {
		return visit(tree);
	}
	
	@Override
	public Double visit(Program node) {
		String programName = node.name;
		Logger.logs("ENTER: PROGRAM %s", programName);
		
		ActivationRecord ar = new ActivationRecord(programName, ARType.PROGRAM, 1);
		callStack.push(ar);
		Logger.logs(callStack.toString());
		
		visit(node.block);
		
		Logger.logs("LEAVE: PROGRAM %s", programName);
		Logger.logs(callStack.toString());
		
		callStack.pop();
		return null;
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
		Double varValue = visit(assign.right);
		ActivationRecord ar = callStack.peek();
		ar.set(varName, varValue);
		return varValue;
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
		ActivationRecord ar = callStack.peek();
		Double value = ar.get(varName);
		return value;
	}
}
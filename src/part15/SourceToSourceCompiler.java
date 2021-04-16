package part15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import part15.ast.AST;
import part15.ast.Assign;
import part15.ast.BinOp;
import part15.ast.Block;
import part15.ast.Compound;
import part15.ast.Param;
import part15.ast.ProcedureDecl;
import part15.ast.Program;
import part15.ast.Var;
import part15.ast.VarDecl;
import part15.ast.Visitor;
import part15.errors.ErrorCode;
import part15.symbols.ProcedureSymbol;
import part15.symbols.ScopedSymbolTable;
import part15.symbols.Symbol;
import part15.symbols.VarSymbol;

public class SourceToSourceCompiler implements Visitor<String> {
	public ScopedSymbolTable currentScope;
	
	public static void main(String[] args) {
		String source = "program Main;\r\n"
				+ "   var x, y : real;\r\n"
				+ "   var z : integer;\r\n"
				+ "\r\n"
				+ "   procedure AlphaA(a : integer);\r\n"
				+ "      var y : integer;\r\n"
				+ "   begin { AlphaA }\r\n"
				+ "      x := a + x + y;\r\n"
				+ "   end;  { AlphaA }\r\n"
				+ "\r\n"
				+ "   procedure AlphaB(a : integer);\r\n"
				+ "      var b : integer;\r\n"
				+ "   begin { AlphaB }\r\n"
				+ "   end;  { AlphaB }\r\n"
				+ "\r\n"
				+ "begin { Main }\r\n"
				+ "end.  { Main }";
		Parser parser = new Parser(new Lexer(source));
		AST tree = parser.parse();
		SourceToSourceCompiler compiler = new SourceToSourceCompiler();
		System.out.println(compiler.visit(tree));
	}
	
	@Override
	public String visit(Program node) {
		String programName = node.name;
		StringBuilder resultString = new StringBuilder()
			.append("program "+programName+"0;\n");
		currentScope = new ScopedSymbolTable("global", 1, currentScope)
			.initBuiltins();	// only initialise built-ins once
		resultString.append(visit(node.block))
			.append(".")
			.append(" {END OF "+programName+"}");
		currentScope = currentScope.enclosingScope;		// reset stack pointer
		return resultString.toString();
	}
	
	@Override
	public String visit(Block block) {
		List<String> results = new ArrayList<>();
		for (AST declaration : block.declarations)
			results.add(visit(declaration));
		results.add("\nbegin");
		results.add("   " + visit(block.compoundStatement));
		results.add("end");
		return String.join("\n", results);
	}
	
	@Override
	public String visit(ProcedureDecl node) {
		String procName = node.name;
		ProcedureSymbol procSymbol = new ProcedureSymbol(procName);
		currentScope.insert(procSymbol);
		StringBuilder resultString = new StringBuilder()
				.append(String.format("procedure %s%s", 
						procName, 
						currentScope.scopeLevel));
		// Create scope for procedure
		ScopedSymbolTable procedureScope = currentScope.createNested(procName);
		currentScope = procedureScope;
		if (node.params.size() != 0) 
			resultString.append("(");
		List<String> formalParams = new ArrayList<>();
		// Insert parameters into the procedure scope
		for (Param param : node.params) {
			Symbol paramType = currentScope.lookup(param.type.getValue());
	 		String paramName = param.var.token.getValue();
			VarSymbol varSymbol = new VarSymbol(paramName, paramType);
			currentScope.insert(varSymbol);
			procSymbol.params.add(varSymbol);
			formalParams.add(String.format(
					"%s : %s", 
					paramName + currentScope.scopeLevel, 
					paramType.name));
		}
		resultString.append(String.join("; ", formalParams));
		if (node.params.size() != 0) 
			resultString.append(")");
		resultString.append(";\n")
			.append(visit(node.block))
			.append("; {END OF "+procName+"}");
		// Indent procedure code
		currentScope = currentScope.enclosingScope;
		return Arrays.asList(resultString.toString().split("\n"))
				.stream()
				.map(line -> "   " + line)
				.collect(Collectors.joining("\n"));
	}
	
	@Override
	public String visit(Compound compound) {
		List<String> results = new ArrayList<>();
		String result;
		for (AST child : compound.children) {
			result = visit(child);
			if (result == null)
				continue;
			results.add(result);
		}
		return String.join("\n", results);
	}
	
	@Override
	public String visit(VarDecl node) {
		String typeName = node.type.token.getValue();
		Symbol type = currentScope.lookup(typeName);
		String varName = node.var.token.getValue();
		VarSymbol varSymbol = new VarSymbol(varName, type);
		if (currentScope.lookup(varName, true) != null)
			error(ErrorCode.DUPLICATE_ID, node.var.token);
		currentScope.insert(varSymbol);
		return String.format("   var %s : %s;", 
				varName + currentScope.scopeLevel, 
				typeName);
	}
	
	@Override
	public String visit(BinOp node) {
		String t1 = visit(node.left),
			t2 = visit(node.right);
		return String.format("%s %s %s", t1, node.token.getValue(), t2);
	}
	
	@Override
	public String visit(Assign node) {
		String t1 = visit(node.left),
			t2 = visit(node.right);
		return String.format("%s := %s;", t1, t2);
	}
	
	@Override
	public String visit(Var node) {
		String varName = node.token.getValue();
		Symbol varSymbol = currentScope.lookup(varName);
		if (varSymbol == null) 
			error(ErrorCode.ID_NOT_FOUND, node.token);
		return String.format("<%s:%s>", 
				varName + currentScope.scopeLevel, 
				varSymbol.type.name);
	}
}

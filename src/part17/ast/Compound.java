package part17.ast;

import java.util.ArrayList;
import java.util.List;

public class Compound extends AST {
	public final List<AST> children;
	
	public Compound() {
		super(null);
		children = new ArrayList<>();
	}
}

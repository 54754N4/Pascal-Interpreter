package part17;

import java.util.Stack;

public class CallStack extends Stack<ActivationRecord> {
	private static final long serialVersionUID = 1868649593773604710L;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CALL STACK\n");
		forEach(ar -> sb.append(ar.toString()+"\n"));
		return sb.toString();
	}
}

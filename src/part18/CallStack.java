package part18;

import java.util.Stack;

public class CallStack extends Stack<ActivationRecord> {
	private static final long serialVersionUID = 1868649593773604710L;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CALL STACK\n");
		for (int i=size()-1; i>=0; i--)
			sb.append(get(i).toString()+"\n");
		return sb.toString();
	}
}

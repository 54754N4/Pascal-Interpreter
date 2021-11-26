package part15;

public class Token {
	private final Type type;
	private final String value;
	private final int line, col;
	
	public Token(Type type, Object value, int line, int col) {
		this.type = type;
		this.value = value.toString();
		this.line = line;
		this.col = col;
	}
	
	public Type getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.equals(type.name()) ?
				"Token["+value+"]" :
				String.format("Token[%s: %s](line:%d|col:%d)", type.toString(), value, line, col);
	}
}

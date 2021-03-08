package part2;

public class Token {
	private Type type;
	private String value;
	
	public Token(Type type) {
		this(type, type.toString());
	}
	
	public Token(Type type, Object value) {
		this.type = type;
		this.value = value.toString();
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
				String.format("Token[%s: %s]", type.toString(), value);
	}
}

package part17.errors;

public enum ErrorCode {
	UNEXPECTED_TOKEN("Unexpected token"),
	ID_NOT_FOUND("Identifier not found"),
	DUPLICATE_ID("Duplicate id found"),
	UNINITIALIZED_VAR("Variable has not been initialized"),
	WRONG_PARAMS_NUM("Wrong number of arguments");
	
	public final String message;
	
	private ErrorCode(String message) {
		this.message = message;
	}
}

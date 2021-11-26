package part18;

public enum ARType {
	PROGRAM("PROGRAM");
	
	private String repr;
	
	private ARType(String repr) {
		this.repr = repr;
	}
	
	@Override
	public String toString() {
		return repr;
	}
}

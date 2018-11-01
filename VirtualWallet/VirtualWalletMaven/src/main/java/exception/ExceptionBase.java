package exception;

public abstract class ExceptionBase extends Exception {
	private static final long serialVersionUID = 1L;
	private final String type;
	
	public ExceptionBase(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}

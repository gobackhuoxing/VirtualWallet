package exception;

public abstract class InvalidAmountException extends ExceptionBase{
	private static final long serialVersionUID = 1L;
	private final double amount;
	
	public InvalidAmountException(String type,double amount) {
		super(type);
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}
}

package exception;

public class InvalidTransferAmountException extends InvalidAmountException{
	private static final long serialVersionUID = 1L;

	public InvalidTransferAmountException(double amount) {
		super("Transaction",amount);
	}

	public String toString() {
		return getType()+" error: "+"InvalidTransferAmountException; The allowed Transfer amount is 0-$2000; amount: "+getAmount();
	}
}

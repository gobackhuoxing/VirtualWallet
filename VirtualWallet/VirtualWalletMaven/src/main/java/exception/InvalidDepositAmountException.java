package exception;


public class InvalidDepositAmountException extends InvalidAmountException{
	private static final long serialVersionUID = 1L;

	public InvalidDepositAmountException(double amount) {
		super("Deposit",amount);
	}

	public String toString() {
		return getType()+" error: "+"InvalidDepositAmountException; The allowed deposit amount is 0-$5000; amount: "+getAmount();
	}
}

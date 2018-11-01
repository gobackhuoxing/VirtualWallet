package exception;

public class InvalidWithdrawAmountException extends InvalidAmountException{
	private static final long serialVersionUID = 1L;

	public InvalidWithdrawAmountException(double amount) {
		super("Withdraw",amount);
	}

	public String toString() {
		return getType()+" error: "+"InvalidWithdrayAmountException; The allowed withdraw amount is 0-$800; amount: "+getAmount();
	}
}

package exception;

import account.AccountBase;

public class NoEnoughBalanceException extends ExceptionBase{
	private static final long serialVersionUID = 1L;
	private final double amount;
	private final AccountBase account;

	public NoEnoughBalanceException(String type, double amount, AccountBase account) {
		super(type);
		this.amount = amount;
		this.account = account;
	}

	public double getAmount() {
		return amount;
	}
	
	public AccountBase getAccount() {
		return account;
	}

	public String toString() {
		return getType()+" error: "+"NoEnoughBalanceException in account "+account.getAccountNum()+"; balance: "+account.getBalance()+" amount: "+amount;
	}
	
}

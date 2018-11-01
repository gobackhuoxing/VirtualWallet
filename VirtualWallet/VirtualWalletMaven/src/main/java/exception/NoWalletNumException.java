package exception;

import bank.BankDriver;

public class NoWalletNumException extends ExceptionBase{
	private static final long serialVersionUID = 1L;
	private final int walletNum;
	private final BankDriver bank;

	public NoWalletNumException(String type, int walletNum, BankDriver bank) {
		super(type);
		this.walletNum = walletNum;
		this.bank = bank;
	}

	public int getAccountNum() {
		return walletNum;
	}
	
	public BankDriver getBank() {
		return bank;
	}

	public String toString() {
		return getType()+" error: "+"NoWalletNumException; Wallet number: "+walletNum;
	}
}

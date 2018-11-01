package exception;

import wallet.WalletBase;

public class NoAccountNumException extends ExceptionBase{
	private static final long serialVersionUID = 1L;
	private final int accountNum;
	private final WalletBase wallet;

	public NoAccountNumException(String type, int accountNum, WalletBase wallet) {
		super(type);
		this.accountNum = accountNum;
		this.wallet = wallet;
	}

	public int getAccountNum() {
		return accountNum;
	}
	
	public WalletBase getWallet() {
		return wallet;
	}

	public String toString() {
		return getType()+" error: "+"NoAccountNumExcpetion in wallet " +wallet.getWalletNum()+"; Account number: "+accountNum;
	}
}

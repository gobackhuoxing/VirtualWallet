package wallet;

import bank.BankDriver;

public class Wallet extends WalletBase{
	public Wallet(String walletOwner,BankDriver bank) {
		super(walletOwner, bank);
		this.setWalletType("default");
	}
}

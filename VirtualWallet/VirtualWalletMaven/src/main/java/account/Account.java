package account;

import wallet.WalletBase;

public class Account extends AccountBase {

	public Account(WalletBase wallet) {
		super(wallet);
		this.setAccountType("default");
	}

}

package transaction;

import bank.BankDriver;

public class WithdrawTransaction extends TransactionBase {

	public WithdrawTransaction(BankDriver bank, int fromWhichWallet, int fromWhichAccount, double amount,
			double fromBalance) {
		super(bank, fromWhichWallet, fromWhichAccount, amount, fromBalance);
		setType("Withdraw");
	}

	public String toString() {
		return "Type: "+getType()+" Transaction number: "+getTransactionNum()+"  amount: "+getAmount()+ "  time: "+ getTimestamp()+"\n"
				+"From wallet: "+getFromWhichWallet()+"  account: "+ getFromWhichAccount()+
				"  balance: "+getFromBalance();
	}
}

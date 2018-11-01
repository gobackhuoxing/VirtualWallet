package transaction;

import bank.BankDriver;

public class DepositTransaction extends TransactionBase{

	public DepositTransaction(BankDriver bank, int fromWhichWallet, int fromWhichAccount, double amount,
			double fromBalance) {
		super(bank, fromWhichWallet, fromWhichAccount, amount, fromBalance);
		setType("Deposit");
	}

	public String toString() {
		return "Type: "+getType()+" Transaction number: "+getTransactionNum()+"  amount: "+getAmount()+ "  time: "+ getTimestamp()+"\n"
				+"To wallet: "+getFromWhichWallet()+"  account: "+ getFromWhichAccount()+
				"  balance: "+getFromBalance();
	}
}

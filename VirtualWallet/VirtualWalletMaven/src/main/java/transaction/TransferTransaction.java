package transaction;

import bank.BankDriver;

public class TransferTransaction extends TransactionBase{

	public TransferTransaction(BankDriver bank, int fromWhichWallet, int fromWhichAccount, int toWhichWallet, int toWhichAccount, double amount,
			double fromBalance, double toBalance) {
		super(bank, fromWhichWallet, fromWhichAccount, amount, fromBalance);
		this.setToWhichWallet(toWhichWallet);
		this.setToWhichAccount(toWhichAccount);
		this.setToBalance(toBalance);
		this.setType("Transfer");
	}
	
	public String toString() {
		return "Type: "+getType()+" Transaction number: "+getTransactionNum()+"  amount: "+getAmount()+ "  time: "+ getTimestamp()+"\n"
				+"From wallet: "+getFromWhichWallet()+"  account: "+ getFromWhichAccount()+
				"  balance: "+getFromBalance()+"\n"
				+"To wallet: "+getToWhichWallet()+"  account: "+ getToWhichAccount()+
				"  balance: "+getToBalance();
	}
	
}

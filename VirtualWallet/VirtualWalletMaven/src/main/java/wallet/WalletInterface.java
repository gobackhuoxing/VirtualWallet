package wallet;

import account.AccountBase;

public interface WalletInterface {
	
	/**
	 * Create an account for a wallet, assign with a random account number. The initial balance is 0.
	 * @param none
	 * @return an instance of AccountBase
	 * @throws none
	 */
	public AccountBase addAccount();
	
	/**
	 * Withdraw money from an account.If successful, add a transaction to transaction history.
	 * @param accountNum: account to withdraw
	 * @param amount: amount to withdraw
	 * @return true if withdraw successful, false if failed
	 * @throws InvalidWithdrawAmountException if the amount is less than 0 or bigger 800.
	 * @throws NoAccountNumException if the account doesn¡¯t exist
	 * @throws NoEnoughBalanceException if the account doesn¡¯t have enough balance.
	 */
	public Boolean withdraw(int accountNum, double amount);
	
	/**
	 * deposit money from to an account. If successful, add a transaction to transaction history.
	 * @param accountNum: account to deposit
	 * @param amount: amount to deposit
	 * @return true if deposit successful, false if failed
	 * @throws InvalidWithdrawAmountException if the amount is less than 0 or bigger 800.
	 * @throws NoAccountNumException if the account doesn¡¯t exist
	 */
	public Boolean deposit(int accountNum, double amount);
	
	/**
	 * Transfer money between account.If successful, add a transaction to transaction history.
	 * @param from: transfered account
	 * @param toWallet: recipient wallet 
	 * @param toAccount; recipient account
	 * @param amount: amount to transfer
	 * @return true if transfer successful, false if failed
	 * @throws InvalidWithdrawAmountException if the amount is less than 0 or bigger 800.
	 * @throws NoAccountNumException if the account doesn¡¯t exist
	 * @throws NoEnoughBalanceException if the transfered account doesn¡¯t have enough balance.
	 */
	public Boolean transfer(int from, int toWallet, int toAccount, double amount);
	
	/**
	 * Show the n most recent transaction history for all account in this wallet
	 * @param n: the number of history to show
	 * @return void
	 * @throws none
	 */
	public void showHistory(int n);
	
	/**
	 * Show all accounts in this wallet.
	 * @param none
	 * @return void
	 * @throws none
	 */
	public void showWallet();
	
}

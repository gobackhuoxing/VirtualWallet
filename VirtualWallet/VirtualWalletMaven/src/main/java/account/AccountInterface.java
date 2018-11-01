package account;

import exception.NoEnoughBalanceException;

public interface AccountInterface {
	
	/**
	 * Withdraw money from this account.
	 * @param amount: amount to withdraw
	 * @return true if withdraw successful, false if failed
	 * @throws NoEnoughBalanceException if the account doesn¡¯t have enough balance.
	 */
	public Boolean withdraw(double amount) throws NoEnoughBalanceException;
	
	/**
	 * deposit money to this account.
	 * @param amount: amount to deposit
	 * @return void
	 * @throws none
	 */
	public void deposit(double amount);
	
	/**
	 * Withdraw money from this account for transfer.
	 * @param amount: amount to transfer
	 * @return void
	 * @throws NoEnoughBalanceException if the transfered account doesn¡¯t have enough 
	 */
	public void transfer(double amount) throws NoEnoughBalanceException;
	
	/**
	 * deposit money to this account.
	 * @param amount: amount to deposit
	 * @return void
	 * @throws none
	 */
	public void receiveTransfer(double amount);
	
	/**
	 * Show account information
	 * @param none
	 * @return void
	 * @throws none
	 */
	public void showAccount();
}

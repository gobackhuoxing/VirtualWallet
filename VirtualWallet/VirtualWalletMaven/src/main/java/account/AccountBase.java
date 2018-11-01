package account;

import java.util.Random;

import exception.NoEnoughBalanceException;
import wallet.WalletBase;

public abstract class AccountBase implements AccountInterface {
	private final WalletBase wallet;
	private final int accountNum;
	private double balance;
	private String accountType;
	
	public AccountBase(WalletBase wallet) {
		this.wallet = wallet;
		Random random = wallet.getBank().getRandom();
		int randomNum = random.nextInt(100000);
		while(wallet.getAccountList().containsKey(randomNum)) {
			randomNum = random.nextInt(10000);
		}
		this.accountNum = randomNum;
		balance = 0;
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public int getAccountNum() {
		return accountNum;
	}
	
	/**
	 * Withdraw money from this account.
	 * @param amount: amount to withdraw
	 * @return true if withdraw successful, false if failed
	 * @throws NoEnoughBalanceException if the account doesn¡¯t have enough balance.
	 */
	public synchronized Boolean withdraw(double amount) throws NoEnoughBalanceException{
		if(getBalance()-amount<0) {
			throw new NoEnoughBalanceException("Withdraw",amount,this);
		}
		else {
			setBalance(getBalance()-amount);
			wallet.setBalance(wallet.getBalance()-amount);
			return true;
		}
	}

	/**
	 * deposit money to this account.
	 * @param amount: amount to deposit
	 * @return void
	 * @throws none
	 */
	public synchronized void deposit(double amount) {
		setBalance(getBalance()+amount);
		wallet.setBalance(wallet.getBalance()+amount);
	}

	/**
	 * Withdraw money from this account for transfer.
	 * @param amount: amount to transfer
	 * @return void
	 * @throws NoEnoughBalanceException if the transfered account doesn¡¯t have enough 
	 */
	public synchronized void transfer(double amount) throws NoEnoughBalanceException{
		if(getBalance()-amount<0) {
			throw new NoEnoughBalanceException("Transaction",amount,this);
		}
		else {
			setBalance(getBalance()-amount);
			wallet.setBalance(wallet.getBalance()-amount);
		}	
	}

	/**
	 * deposit money to this account.
	 * @param amount: amount to deposit
	 * @return void
	 * @throws none
	 */
	public synchronized void receiveTransfer(double amount) {
		deposit(amount);
	}

	/**
	 * Show account information
	 * @param none
	 * @return void
	 * @throws none
	 */
	public void showAccount() {
		System.out.println("Account Number: "+accountNum+"  Account Type: "+accountType+"  Balance: "+balance);
	}
}

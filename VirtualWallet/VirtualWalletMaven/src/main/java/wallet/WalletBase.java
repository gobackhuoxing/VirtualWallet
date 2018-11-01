package wallet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import account.Account;
import account.AccountBase;
import bank.BankDriver;
import exception.ExceptionBase;
import exception.InvalidDepositAmountException;
import exception.InvalidTransferAmountException;
import exception.InvalidWithdrawAmountException;
import exception.NoAccountNumException;
import exception.NoWalletNumException;
import transaction.DepositTransaction;
import transaction.TransactionBase;
import transaction.TransferTransaction;
import transaction.WithdrawTransaction;

public abstract class WalletBase implements WalletInterface {
	private final String walletOwner;
	private final int walletNum;
	private final BankDriver bank;
	private final Map<Integer, AccountBase> accountList;
	private final CopyOnWriteArrayList<TransactionBase> transactionHistory;
	private int transactionHistorySize;
	private String walletType;
	private double balance;
	
	
	public WalletBase(String walletOwner, BankDriver bank) {
		this.bank = bank;
		this.walletOwner = walletOwner;
		Random random = bank.getRandom();
		int randomNum = random.nextInt(10000);
		while(bank.getWalletList().containsKey(randomNum)) {
			randomNum = random.nextInt(10000);
		}
		this.walletNum = randomNum;
		balance = 0;
		accountList = new HashMap<Integer, AccountBase>();
		transactionHistorySize = 20;
		transactionHistory = new CopyOnWriteArrayList<TransactionBase>();
	}

	public String getWalletType() {
		return walletType;
	}

	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}

	public String getWalletOwner() {
		return walletOwner;
	}

	public int getWalletNum() {
		return walletNum;
	}

	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Map<Integer, AccountBase> getAccountList() {
		return accountList;
	}

	public BankDriver getBank() {
		return bank;
	}
	
	public int getTransactionHistorySize() {
		return transactionHistorySize;
	}

	public void setTransactionHistorySize(int transactionHistorySize) {
		this.transactionHistorySize = transactionHistorySize;
	}

	public List<TransactionBase> getTransactionHistory() {
		return transactionHistory;
	}

	/**
	 * Create an account for a wallet, assign with a random account number. The initial balance is 0.
	 * @param none
	 * @return an instance of AccountBase
	 * @throws none
	 */
	public AccountBase addAccount() {
		AccountBase account = new Account(this);
		accountList.put(account.getAccountNum(), account);
		return account;
	}

	/**
	 * Withdraw money from an account.If successful, add a transaction to transaction history.
	 * @param accountNum: account to withdraw
	 * @param amount: amount to withdraw
	 * @return true if withdraw successful, false if failed
	 * @throws InvalidWithdrawAmountException if the amount is less than 0 or bigger 800.
	 * @throws NoAccountNumException if the account doesn¡¯t exist
	 * @throws NoEnoughBalanceException if the account doesn¡¯t have enough balance.
	 */
	public synchronized Boolean withdraw(int accountNum, double amount) {
		try {
			if(amount<=0 || amount>800) throw new InvalidWithdrawAmountException(amount);
			else {
				if(!accountList.containsKey(accountNum)) throw new NoAccountNumException("Withdraw",accountNum,this);
				else accountList.get(accountNum).withdraw(amount);
			}
		}
		catch(ExceptionBase e) {
			e.printStackTrace();
			return false;
		}
		TransactionBase transaction = new WithdrawTransaction(bank,walletNum,accountNum,amount,accountList.get(accountNum).getBalance());
		addTransaction(transaction);
		//System.out.println("Withdraw "+amount+" from account"+accountNum+" Wallet Balance: "+getBalance()+"  Account Balance: "+accountList.get(accountNum).getBalance());
		return true;
	}

	/**
	 * deposit money from to an account. If successful, add a transaction to transaction history.
	 * @param accountNum: account to deposit
	 * @param amount: amount to deposit
	 * @return true if deposit successful, false if failed
	 * @throws InvalidWithdrawAmountException if the amount is less than 0 or bigger 800.
	 * @throws NoAccountNumException if the account doesn¡¯t exist
	 */
	public synchronized Boolean deposit(int accountNum, double amount) {
		try {
			if(amount<=0 || amount>5000) throw new InvalidDepositAmountException(amount);
			else {
				if(!accountList.containsKey(accountNum)) throw new NoAccountNumException("Deposit",accountNum,this);
				else accountList.get(accountNum).deposit(amount);
			}
		}
		catch(ExceptionBase e) {
			e.printStackTrace();
			return false;
		}
		TransactionBase transaction = new DepositTransaction(bank,walletNum,accountNum,amount,accountList.get(accountNum).getBalance());
		addTransaction(transaction);
		//System.out.println("Deposit "+amount+" to account "+accountNum+" Wallet Balance: "+getBalance()+"  Account Balance: "+accountList.get(accountNum).getBalance());
		return true;
	}

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
	public synchronized Boolean transfer(int from, int toWallet, int toAccount, double amount) {
		try {
			if(amount<0 || amount>2000) throw new InvalidTransferAmountException(amount); 
			else {
				if(!accountList.containsKey(from)) throw new NoAccountNumException("Transaction",from,this);
				else if(!bank.getWalletList().containsKey(toWallet)) throw new NoWalletNumException("Transaction",toWallet,this.bank);
				else if(!bank.getWalletList().get(toWallet).accountList.containsKey(toAccount)) throw new NoAccountNumException("Transaction",toAccount,this);
				else {
					accountList.get(from).transfer(amount);
					bank.getWalletList().get(toWallet).accountList.get(toAccount).receiveTransfer(amount);
				}
			}
		}
		catch(ExceptionBase e) {
			e.printStackTrace();
			return false;
		}
		TransactionBase transaction = new TransferTransaction(bank,walletNum,from,toWallet,toAccount,amount,
				accountList.get(from).getBalance(),bank.getWalletList().get(toWallet).getAccountList().get(toAccount).getBalance());
		addTransaction(transaction);
		//System.out.println(transaction.toString());
		return true;
	}

	/**
	 * Show the n most recent transaction history for all account in this wallet
	 * @param n: the number of history to show
	 * @return void
	 * @throws none
	 */
	public void showHistory(int n) {
		if(n>transactionHistorySize) System.out.println("\n"+"You can only check the last "+transactionHistorySize+" transactions in wallet "+ walletNum+ " : ");
		else System.out.println("\n"+"The last "+n+" transactions in wallet "+ walletNum+ " : ");
		n = Math.min(n, transactionHistory.size());
		for(int i = 0 ; i < n; i++ ) {
			System.out.println(transactionHistory.get(i).toString());
		}	
	}
	
	/**
	 * Show all accounts in this wallet.
	 * @param none
	 * @return void
	 * @throws none
	 */
	public void showWallet() {
		System.out.println("\n"+"Wallet Owner: "+walletOwner+"  Wallet Number: "+walletNum+"  Wallet Type: "+walletType+"  Balance: "+balance);
		System.out.println(accountList.size()+" Accounts");
		for(Map.Entry<Integer, AccountBase> e : accountList.entrySet()) {
			e.getValue().showAccount();
		}
	}
	
	/**
	 * Add transaction to transaction history in bank and wallet
	 * @param transaction: instance of a transaction
	 * @return void
	 * @throws none
	 */
	private void addTransaction(TransactionBase transaction) {
		bank.getTransactionNumSet().add(transaction.getTransactionNum());
		synchronized(bank.getTransactionHistory()) {
			while(bank.getTransactionHistory().size()>bank.getTransactionHistorySize()) {
				bank.getTransactionHistory().remove(bank.getTransactionHistory().size()-1);
			}
			bank.getTransactionHistory().add(0, transaction);
		}
		
		synchronized(transactionHistory) {
			while(transactionHistory.size()>transactionHistorySize) {
				transactionHistory.remove(transactionHistory.size()-1);
			}
			transactionHistory.add(0, transaction);
		}
		
		if(transaction.getType().equals("Transfer") && walletNum != ((TransferTransaction) transaction).getToWhichWallet()) {
			WalletBase towallet = bank.getWalletList().get(((TransferTransaction) transaction).getToWhichWallet());
			synchronized(towallet) {
				while(towallet.getTransactionHistory().size()>towallet.getTransactionHistorySize()) {
					towallet.getTransactionHistory().remove(towallet.getTransactionHistory().size()-1);
				}
				towallet.getTransactionHistory().add(0, transaction);
			}
		}
	}
}

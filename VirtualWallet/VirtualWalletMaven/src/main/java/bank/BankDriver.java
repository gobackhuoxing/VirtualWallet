package bank;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import transaction.TransactionBase;
import wallet.Wallet;
import wallet.WalletBase;

public class BankDriver {
	private final Map<Integer, WalletBase> walletList;
	private Random random;
	private final Set<Integer> transactionNumSet;
	private final CopyOnWriteArrayList<TransactionBase> transactionHistory;
	private int transactionHistorySize;

	public BankDriver() {
		this.walletList = new HashMap<Integer,WalletBase>();
		this.random = new Random(41);
		transactionNumSet = new HashSet<Integer>();
		transactionHistorySize = 50;
		transactionHistory = new CopyOnWriteArrayList<TransactionBase>();
	}

	/**
	 * Create a Wallet for a user, assign with a random wallet number. The initial balance is 0.
	 * @param owner: the name of owner of this wallet;
	 * @return an instance of WalletBase
	 * @throws none
	 */
	public WalletBase addWallet(String owner) {
		WalletBase wallet = new Wallet(owner,this);
		walletList.put(wallet.getWalletNum(),wallet);
		return wallet;
	}
	
	/**
	 * Show the n most recent transaction history for all wallets in this bank
	 * @param n: the number of history to show
	 * @return void
	 * @throws none
	 */
	public void showHistory(int n) {
		if(n>transactionHistorySize) System.out.println("\n"+"You can only check the last "+transactionHistorySize+" transactions:");
		else System.out.println("\n"+"The last "+n+" transactions in bank:");
		n = Math.min(n, transactionHistory.size());
		for(int i = 0 ; i < n; i++ ) {
			System.out.println(transactionHistory.get(i).toString());
		}	
	}
	
	public Map<Integer, WalletBase> getWalletList() {
		return walletList;
	}
	
	public Random getRandom() {
		return random;
	}

	public int getTransactionHistorySize() {
		return transactionHistorySize;
	}

	public void setTransactionHistorySize(int transactionHistorySize) {
		this.transactionHistorySize = transactionHistorySize;
	}

	public Set<Integer> getTransactionNumSet() {
		return transactionNumSet;
	}

	public List<TransactionBase> getTransactionHistory() {
		return transactionHistory;
	}

	public static void demo() {
		BankDriver bank = new BankDriver();
		WalletBase wallet1 = bank.addWallet("Jeremy");
		int a1 = wallet1.addAccount().getAccountNum();
		WalletBase wallet2 = bank.addWallet("Tom");
		int a2 = wallet2.addAccount().getAccountNum();
		WalletBase wallet3 = bank.addWallet("Jack");
		int a3 = wallet3.addAccount().getAccountNum();
		
		wallet1.deposit(a1, 100);
		wallet2.deposit(a2, 150);
		wallet3.deposit(a3, 250);
		wallet3.withdraw(a3, 350);
		wallet1.showWallet();
		wallet2.showWallet();
		wallet3.showWallet();
		for(int i = 0; i < 9; i++) {
			wallet1.transfer(a1, wallet2.getWalletNum(), a2, 1);
		}
		
		for(int i = 0; i < 9; i++) {
			wallet1.transfer(a1, wallet3.getWalletNum(), a3, 1);
		}
		wallet1.withdraw(a1, 10);
		wallet2.withdraw(a2, 11);
		wallet3.withdraw(a3, 12);
		bank.showHistory(43);
		wallet1.showHistory(20);
		wallet2.showHistory(20);
		wallet3.showHistory(20);
		wallet1.showWallet();
		wallet2.showWallet();
		wallet3.showWallet();

	}
	
	public static void main(String[] args) {
		//demo();
	}

}

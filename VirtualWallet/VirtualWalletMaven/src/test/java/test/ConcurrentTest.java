package test;

import static org.junit.Assert.*;

import org.junit.Test;

import bank.BankDriver;
import wallet.WalletBase;

public class ConcurrentTest {

	// Test for muti-threaded: concurrent deposit, withdraw, transfer.
	@Test
	public void concurrentTest() {
		BankDriver bank = new BankDriver();
		WalletBase wallet1 = bank.addWallet("Jeremy");
		WalletBase wallet2 = bank.addWallet("Jack");
		int a1 = wallet1.addAccount().getAccountNum();
		int a2 = wallet2.addAccount().getAccountNum();
		int a3 = wallet2.addAccount().getAccountNum();
		
		Thread deposit1 = new Thread(new DepositThread(wallet1,a1));
		Thread deposit2 = new Thread(new DepositThread(wallet2,a2));
		Thread deposit3 = new Thread(new DepositThread(wallet2,a3));
		
		Thread withdraw1 = new Thread(new WithDrawThread(wallet1,a1));
		Thread withdraw2 = new Thread(new WithDrawThread(wallet2,a2));
		Thread withdraw3 = new Thread(new WithDrawThread(wallet2,a3));
		
		//concurrent deposit
		deposit1.start();
		deposit2.start();
		deposit3.start();	
		try {
			deposit1.join();
			deposit2.join();
			deposit3.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("account1 balance should be 1000",1000,wallet1.getAccountList().get(a1).getBalance(),1);
		assertEquals("account2 balance should be 1000",1000,wallet2.getAccountList().get(a2).getBalance(),1);
		assertEquals("account3 balance should be 1000",1000,wallet2.getAccountList().get(a3).getBalance(),1);
		assertEquals("wallet1 balance should be 1000",1000,wallet1.getBalance(),1);
		assertEquals("wallet2 balance should be 2000",2000,wallet2.getBalance(),1);
		
		//concurrent withdraw
		withdraw1.start();
		withdraw2.start();
		withdraw3.start();
		try {
			withdraw1.join();
			withdraw2.join();
			withdraw3.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("account1 balance should be 800",800,wallet1.getAccountList().get(a1).getBalance(),1);
		assertEquals("account2 balance should be 800",800,wallet2.getAccountList().get(a2).getBalance(),1);
		assertEquals("account3 balance should be 800",800,wallet2.getAccountList().get(a3).getBalance(),1);
		assertEquals("wallet1 balance should be 800",800,wallet1.getBalance(),1);
		assertEquals("wallet2 balance should be 1600",1600,wallet2.getBalance(),1);
		
		//concurrent transfer
		Thread transfer1 = new Thread(new TransferThread(wallet2,a2,wallet1,a1));
		Thread transfer2 = new Thread(new TransferThread(wallet2,a2,wallet2,a3));
		
		transfer1.start();
		transfer2.start();
		try {
			transfer1.join();
			transfer2.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("account1 balance should be 1000",1000,wallet1.getAccountList().get(a1).getBalance(),1);
		assertEquals("account2 balance should be 400",400,wallet2.getAccountList().get(a2).getBalance(),1);
		assertEquals("account3 balance should be 1000",1000,wallet2.getAccountList().get(a3).getBalance(),1);
		assertEquals("wallet1 balance should be 1000",1000,wallet1.getBalance(),1);
		assertEquals("wallet2 balance should be 1400",1400,wallet2.getBalance(),1);
	}
	
	private class DepositThread implements Runnable{
		private WalletBase wallet;
		private int account;
		
		public DepositThread(WalletBase wallet, int account) {
			this.wallet = wallet;
			this.account = account;
		}

		public void run() {
			for(int i = 0; i < 10; i++) {
				wallet.deposit(account, 100);
			}
		}
	}
	
	private class WithDrawThread implements Runnable{
		private WalletBase wallet;
		private int account;
		
		public WithDrawThread(WalletBase wallet, int account) {
			this.wallet = wallet;
			this.account = account;
		}

		public void run() {
			for(int i = 0; i < 10; i++) {
				wallet.withdraw(account, 20);
			}
		}
	}
	
	private class TransferThread implements Runnable{
		private WalletBase fromWallet;
		private int fromAccount;
		private WalletBase toWallet;
		private int toAccount;

		public TransferThread(WalletBase fromWallet, int fromAccount, WalletBase toWallet, int toAccount) {
			this.fromWallet = fromWallet;
			this.fromAccount = fromAccount;
			this.toWallet = toWallet;
			this.toAccount = toAccount;
		}

		public void run() {
			for(int i = 0; i < 10; i++) {
				fromWallet.transfer(fromAccount, toWallet.getWalletNum(), toAccount,20);
			}
		}
	}
}

package test;

import static org.junit.Assert.*;

import org.junit.*;

import bank.BankDriver;
import wallet.WalletBase;

public class BankDriverTest {
	
	//Test the overall high level functionality for the whole library
	@Test
	public void bankDriverTest() {
		//create a new empty wallet
		BankDriver bank = new BankDriver();
		WalletBase wallet1 = bank.addWallet("Jeremy");
		assertEquals("initial balance should be 0",0, wallet1.getBalance(),1);
		assertEquals("initial account list should be empty",0, wallet1.getAccountList().size());
		
		//create two more wallets
		WalletBase wallet2 = bank.addWallet("Tom");
		WalletBase wallet3 = bank.addWallet("Jack");
		assertEquals("should have three wallet",3,bank.getWalletList().size());
		
		//add account to each wallet
		int a1 = wallet1.addAccount().getAccountNum();
		int a2 = wallet2.addAccount().getAccountNum();
		int a3 = wallet3.addAccount().getAccountNum();
		int a4 = wallet1.addAccount().getAccountNum();
		assertEquals("wallet1 should have 2 account",2,wallet1.getAccountList().size());
		assertEquals("initial account balance should be 0",0.0,wallet1.getAccountList().get(a1).getBalance(),1);
		
		//test deposit and withdraw
		wallet1.deposit(a1, 100);
		wallet1.deposit(a4, 120);
		wallet2.deposit(a2, 150);
		wallet3.deposit(a3, 250);
		assertEquals("a1 balance should be 100",100.0,wallet1.getAccountList().get(a1).getBalance(),1);
		assertEquals("wallet1 balance should be 220",220.0,wallet1.getBalance(),1);
		assertEquals("a2 balance should be 150",150.0,wallet2.getAccountList().get(a2).getBalance(),1);
		wallet3.withdraw(a3, 111);
		assertEquals("a3 balance should be 139",139.0,wallet3.getAccountList().get(a3).getBalance(),1);
		
		//when user tried to withdraw more than balance
		wallet3.withdraw(a3, 140);
		assertEquals("withdraw should not bigger than balance",139.0,wallet3.getAccountList().get(a3).getBalance(),1);
		
	}	
}

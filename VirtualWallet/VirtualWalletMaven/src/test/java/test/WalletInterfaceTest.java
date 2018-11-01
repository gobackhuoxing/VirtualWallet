package test;

import static org.junit.Assert.*;

import org.junit.Test;

import bank.BankDriver;
import wallet.WalletBase;

public class WalletInterfaceTest {

	//Test for WalletInterface: deposit, withdraw, transfer, multiple time of transfer and the transaction history.
	@Test
	public void walletInterfaceTest() {
		BankDriver bank = new BankDriver();
		WalletBase wallet1 = bank.addWallet("Jeremy");
		WalletBase wallet2 = bank.addWallet("Tom");
		
		int a1 = wallet1.addAccount().getAccountNum();
		int a2 = wallet2.addAccount().getAccountNum();
		int a3 = wallet2.addAccount().getAccountNum();
		
		//test deposit
		wallet1.deposit(a1, 10000);
		assertEquals("The allowed deposit amount is 0-$5000",wallet1.getBalance(),0.0,1);
		wallet1.deposit(a1, 100);
		assertEquals("The balance should be 100",100.0,wallet1.getBalance(),1);
		wallet2.deposit(a2, 150);
		assertEquals("The balance should be 150",150.0,wallet2.getBalance(),1);
		wallet2.deposit(a3, 250);
		assertEquals("The balance should be 400",400.0,wallet2.getBalance(),1);
		wallet2.deposit(a2, 50);
		assertEquals("The balance should be 4450",450.0,wallet2.getBalance(),1);
		
		//test withdraw
		wallet1.withdraw(a1, 1000);
		assertEquals("The allowed deposit amount is 0-$800",100.0,wallet1.getBalance(),1);
		wallet1.withdraw(a1, 110);
		assertEquals("can not withdraw more than the balancd",100.0,wallet1.getBalance(),1);
		wallet2.withdraw(a2, 10);
		assertEquals("wallet balance should be 440",440.0,wallet2.getBalance(),1);
		assertEquals("account balance should be 190",190.0,wallet2.getAccountList().get(a2).getBalance(),1);
		
		//test transfer
		wallet2.transfer(a3, wallet1.getWalletNum(), a1, 10);
		assertEquals("wallet2 balance should be 440",430.0,wallet2.getBalance(),1);
		assertEquals("The a3 balance should be 240",240.0,wallet2.getAccountList().get(a3).getBalance(),1);
		assertEquals("The a1 balance should be 110",110.0,wallet1.getAccountList().get(a1).getBalance(),1);
		
		
		//transfer multiple times
		for(int i = 0; i < 5; i++) {
			wallet2.transfer(a3, wallet1.getWalletNum(), a1, 1);
		}
		assertEquals("The a1 balance should be 115",115.0,wallet1.getAccountList().get(a1).getBalance(),1);
		assertEquals("The a3 balance should be 235",235.0,wallet2.getAccountList().get(a3).getBalance(),1);
		assertEquals("The wallet1 balance should be 115",115.0,wallet1.getBalance(),1);
		assertEquals("The wallet2 balance should be 425",425.0,wallet2.getBalance(),1);
		
		for(int i = 0; i < 5; i++) {
			wallet2.transfer(a3, wallet2.getWalletNum(), a2, 1);
		}
		assertEquals("The a3 balance should be 230",230.0,wallet2.getAccountList().get(a3).getBalance(),1);
		assertEquals("The a2 balance should be 195",195.0,wallet2.getAccountList().get(a2).getBalance(),1);
		assertEquals("The wallet2 balance should be 425",425.0,wallet2.getBalance(),1);
		
		wallet1.withdraw(a1, 10);
		wallet2.withdraw(a2, 11);
		wallet2.withdraw(a3, 12);
		wallet1.showWallet();
		wallet2.showWallet();
		assertEquals("The a1 balance should be 105",105.0,wallet1.getAccountList().get(a1).getBalance(),1);
		assertEquals("The a2 balance should be 184",184.0,wallet2.getAccountList().get(a2).getBalance(),1);
		assertEquals("The a3 balance should be 218",218.0,wallet2.getAccountList().get(a3).getBalance(),1);
		
		//test the transaction history
		assertEquals("bank should have 19 transaction history",19,bank.getTransactionHistory().size());
		assertEquals("wallet1 should have 8 transaction history",8,wallet1.getTransactionHistory().size());
		assertEquals("wallet2 should have 17 transaction history",17,wallet2.getTransactionHistory().size());
		
		assertEquals("the type of fisrt transaction in wallet1 should be Withdraw","Withdraw",wallet1.getTransactionHistory().get(0).getType());
		assertEquals("the amount of fisrt transaction in wallet1 should be 10",10.0,wallet1.getTransactionHistory().get(0).getAmount(),1);
		assertEquals("the type of 2th transaction in wallet1 should be Transfer","Transfer",wallet1.getTransactionHistory().get(1).getType());
		assertEquals("the amount of 2th transaction in wallet1 should be 1",1.0,wallet1.getTransactionHistory().get(1).getAmount(),1);
		assertEquals("the recipient wallet of 2th transaction in wallet1 should be 4007",4007,wallet1.getTransactionHistory().get(1).getToWhichWallet());
		assertEquals("the transfered wallet of 2th transaction in wallet1 should be 9627",9627,wallet1.getTransactionHistory().get(1).getFromWhichWallet());
		assertEquals("the recipient account of 2th transaction in wallet1 should be 9056",9056,wallet1.getTransactionHistory().get(1).getToWhichAccount());
		assertEquals("the transfered account of 2th transaction in wallet1 should be 15835",15835,wallet1.getTransactionHistory().get(1).getFromWhichAccount());
		
		assertEquals("the balance of wallet1 after 5th transaction should be 113",113.0,wallet1.getTransactionHistory().get(4).getToBalance(),1);
		assertEquals("the balance of wallet2 after 5th transaction should be 425",425.0,wallet2.getTransactionHistory().get(4).getFromBalance()+wallet2.getTransactionHistory().get(4).getToBalance(),1);
	}	
}

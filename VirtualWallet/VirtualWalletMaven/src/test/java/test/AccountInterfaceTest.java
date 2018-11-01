package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import bank.BankDriver;
import exception.NoEnoughBalanceException;
import wallet.WalletBase;

public class AccountInterfaceTest {

	//Test for AccountInterface: deposit, withdraw, transfer and exception throwing.
	@Test
	public void accountInterfaceTest() {
		BankDriver bank = new BankDriver();
		WalletBase wallet1 = bank.addWallet("Jeremy");
		int a1 = wallet1.addAccount().getAccountNum();
		//should throw NoEnoughBalanceException
		Boolean throwed = false;
		try {
			wallet1.getAccountList().get(a1).withdraw(1);
			fail("Should throw NoEnoughBalanceException");
		} 
		catch (NoEnoughBalanceException e) {
			assertEquals("withdraw should not bigger than balance",0.0,wallet1.getAccountList().get(a1).getBalance(),1);
			throwed = true;
		}
		assertTrue("Should throw NoEnoughBalanceException",throwed);
		
		throwed = false;
		try {
			wallet1.getAccountList().get(a1).transfer(1);
			fail("Should throw NoEnoughBalanceException");
		} 
		catch (NoEnoughBalanceException e) {
			assertEquals("transfer amount should not bigger than balance",0.0,wallet1.getAccountList().get(a1).getBalance(),1);
			throwed = true;
		}
		assertTrue("Should throw NoEnoughBalanceException",throwed);    
		
		wallet1.getAccountList().get(a1).deposit(100);
		assertEquals("Balance should be 100",100.0,wallet1.getAccountList().get(a1).getBalance(),1);
		//should not throw any exception
		try {
			wallet1.getAccountList().get(a1).withdraw(100);
		} catch (Exception e) {
			fail("Exception throwed");
		}
		assertEquals("Balance should be 0",0.0,wallet1.getAccountList().get(a1).getBalance(),1);
	}
}

package transaction;

import java.sql.Timestamp;
import java.util.Random;

import bank.BankDriver;

public abstract class TransactionBase {
	private final int transactionNum;
	private final BankDriver bank;
	private final Timestamp timestamp;
	private final int fromWhichWallet;
	private final int fromWhichAccount;
	private final double amount;
	private final double fromBalance;
	private String type;
	private int toWhichWallet;
	private int toWhichAccount;
	private double toBalance;
	
	public TransactionBase(BankDriver bank, int fromWhichWallet,
			int fromWhichAccount, double amount, double fromBalance) {
		timestamp = new Timestamp(System.currentTimeMillis());;
		this.bank = bank;
		this.fromWhichWallet = fromWhichWallet;
		this.fromWhichAccount = fromWhichAccount;
		this.amount = amount;
		this.fromBalance = fromBalance;
		Random random = bank.getRandom();
		int randomNum = random.nextInt(1000000);
		while(bank.getTransactionNumSet().contains(randomNum)) {
			randomNum = random.nextInt(1000000);
		}
		transactionNum = randomNum;
	}
	public int getTransactionNum() {
		return transactionNum;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public BankDriver getBank() {
		return bank;
	}
	public int getFromWhichWallet() {
		return fromWhichWallet;
	}
	public int getFromWhichAccount() {
		return fromWhichAccount;
	}
	public double getAmount() {
		return amount;
	}
	public double getFromBalance() {
		return fromBalance;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getToWhichWallet() {
		return toWhichWallet;
	}
	public int getToWhichAccount() {
		return toWhichAccount;
	}
	public double getToBalance() {
		return toBalance;
	}
	public void setToWhichWallet(int toWhichWallet) {
		this.toWhichWallet = toWhichWallet;
	}
	public void setToWhichAccount(int toWhichAccount) {
		this.toWhichAccount = toWhichAccount;
	}
	public void setToBalance(double toBalance) {
		this.toBalance = toBalance;
	}
}

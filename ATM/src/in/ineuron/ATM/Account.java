package in.ineuron.ATM;

import java.util.ArrayList;

public class Account {
	/*
	 * Type of account - savings, current
	 */
	private String accountType;

	/*
	 * Account Number of the account
	 */
	private String accountNo;
	/*
	 * Account Holder who owns the account
	 */
	private AccountHolder accountHolder;
	/*
	 * List of transactions performed on this account
	 */
	private ArrayList<BankTransaction> bankTransactions;
	
	/**
	 * Create account of the specified type for the account holder and associated bank
	 * 
	 * @param accountType Type of account(savings or current)
	 * @param accountHolder account holder associated with the account
	 * @param theBank the bank associated with the account
	 */
	public Account(String accountType, AccountHolder accountHolder, Bank theBank) {
		this.accountType = accountType;
		this.accountHolder = accountHolder;
		this.accountNo = theBank.getNewAccountNo();
		this.bankTransactions = new ArrayList<BankTransaction>();
	}
	
	/**
	 * Get the account number
	 * 
	 * @return the account number
	 */
	public String getAccountNo() {
		return this.accountNo;
	}
	/**
	 * Get summary line for the account
	 * @return summary line
	 */
	public String getSummaryLine() {
		//get the account's balance
		double balance = this.getBalance();
		//format the summary line based on the balance is negative
		if(balance>=0) {
			return String.format("%s : $%.02f : %s", this.accountNo,balance,this.accountType);
		}
		else {
			return String.format("%s : $(%.02f) : %s", this.accountNo,balance,this.accountType);
		}

	}
	
	/**
	 * Get the balance after adding the transaction amount
	 * @return the account balance
	 */
	public double getBalance() {
		double balance = 0;
		for(BankTransaction bt : this.bankTransactions) {
			balance += bt.getTransactionAmount();			
		}
		return balance;
	}
	
	/**
	 * Prints the transaction history of the particular account
	 */
	public void printTransactionHistory() {
		System.out.printf("\nTransaction History for account %s\n",this.accountNo);
		if(this.bankTransactions.size()==0)
			System.out.println("No transactions performed yet");
		for(int t=this.bankTransactions.size()-1; t>=0; t--){
			System.out.println(this.bankTransactions.get(t).getSummaryLine());
		}
		System.out.println();
	}
	
	public void addTransaction(double amount,String transactionComment) {
		BankTransaction newTransaction = new BankTransaction(amount, transactionComment, this);
		this.bankTransactions.add(newTransaction);
	}
}

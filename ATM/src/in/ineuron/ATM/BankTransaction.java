package in.ineuron.ATM;

import java.util.Date;

public class BankTransaction {
	/*
	 * Amount of transaction made
	 */
	private double transactionAmount;
	/*
	 * Date of transaction
	 */
	private Date transactionDate;
	/*
	 * Comments for the transaction
	 */
	private String transactionComment;
	/*
	 * Account in which transaction was performed
	 */
	private Account transactionAccount;
	
	/**
	 * Generate a new Bank Transaction
	 * 
	 * @param transactionAmount
	 * @param transactionAccount
	 */
	public BankTransaction(double transactionAmount, Account transactionAccount) {
		super();
		this.transactionAmount = transactionAmount;
		this.transactionDate = new Date();
		this.transactionAccount = transactionAccount;
		this.transactionComment = "";
	}
	/**
	 * Generate new Bank Transaction with comments
	 * 
	 * @param transactionAmount
	 * @param transactionComment
	 * @param transactionAccount
	 */
	public BankTransaction(double transactionAmount, String transactionComment,
			Account transactionAccount) {
		this(transactionAmount,transactionAccount);
		this.transactionComment = transactionComment;
	}
	/**
	 * Get the amount of the transaction
	 * 
	 * @return get the amount of transaction
	 */
	public double getTransactionAmount() {
		return this.transactionAmount;
	}
	
	/**
	 * Summary of the transaction 
	 * @return summary string
	 */
	public String getSummaryLine() {
		if(this.transactionAmount>=0) {
			return String.format("%s : $%.02f : %s", this.transactionDate.toString(),
					this.transactionAmount,this.transactionComment);
		}
		else {
			return String.format("%s : $(%.02f) : %s", this.transactionDate.toString(),
					-this.transactionAmount,this.transactionComment);
		}
	}
}

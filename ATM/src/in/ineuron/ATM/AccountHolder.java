package in.ineuron.ATM;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @author Suvitha
 *
 */
public class AccountHolder {
	/*
	 *First name of the account holder
	 */
	private String firstName;
	/*
	 * Last name of the account holder
	 */
	private String lastName;
	/*
	 * User Id of the account holder
	 */
	private String userID;
	/*
	 * Pin number of the account holder
	 */
	private byte userPin[];
	/*
	 * List of accounts of the account holder
	 */
	private ArrayList<Account> accounts;
	
	/**
	 * Create a new Account Holder
	 * 
	 * @param firstName account holder's firstname
	 * @param lastName account holder's lastname
	 * @param pin account holder's pin
	 * @param thebank the bank the accountholder is associated with
	 */
	public AccountHolder(String firstName, String lastName, String pin, Bank thebank) {
		this.firstName = firstName;
		this.lastName = lastName;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.userPin = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.print("error caught no such algorithm");
			e.printStackTrace();
			System.exit(1);
		}
		this.userID = thebank.getNewUserID();
		this.accounts = new ArrayList<Account>();
		System.out.printf("New user %s %s with ID %s created.\n", firstName, lastName, this.userID);
	}
	
	/**
	 * Add an account to the Account holder
	 * 
	 * @param anAccount account to be added to account holder
	 */
	public void addAccount(Account anAccount) {
		this.accounts.add(anAccount);
	}
	
	/**
	 * Returns account holder's unique user id
	 * 
	 * @return the unique user id
	 */
	public String getUserID() {
		return this.userID;
	}
	/**
	 * Check if pin entered matched original pin
	 * @param aPin
	 * @return true if matched
	 */
	public boolean validatePin(String aPin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()),this.userPin);
		} catch (NoSuchAlgorithmException e) {
			System.err.print("error caught no such algorithm");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}
	public String getFirstName() {
		return this.firstName;
	}
	/**
	 * Print summary of the accounts of the account holder
	 */
	public void printAccountSummary() {
		System.out.printf("\n\n%s's Account Summary \n",this.firstName);
		for(int i=0; i<this.accounts.size();i++) {
			System.out.printf(" %d) %s\n", i+1, this.accounts.get(i).getSummaryLine());
		}
		System.out.println();
	}
	/**
	 * Get the number of account of the account holder
	 * @return number of accounts
	 */
	public int numAccounts() {
		return this.accounts.size();
	}
	/**
	 * Print the transaction history of the particular account
	 * @param accountIndex index of the account
	 */
	public void printAccountTransactionHistory(int accountIndex) {
		this.accounts.get(accountIndex).printTransactionHistory();
	}
	/**
	 * Get the balance in the account 
	 * @param accountIndex index of account
	 * @return balance of the account
	 */
	public double getAccountBalance(int accountIndex) {
		return this.accounts.get(accountIndex).getBalance();
	}
	/**
	 * Get the user id of the accountholder's mentioned account
	 * @param accountIndex index of the account of the holder
	 * @return account number
	 */
	public String getAccountUUID(int accountIndex) {
		return this.accounts.get(accountIndex).getAccountNo();
	}
	/**
	 * Add transaction to the particular account number
	 * @param accountIndex index of the account
	 * @param amount transaction amount
	 * @param transactionComment comment for the transaction
	 */
	public void addAccountTransaction(int accountIndex, double amount, String transactionComment) {
		this.accounts.get(accountIndex).addTransaction(amount,transactionComment);
	}
}

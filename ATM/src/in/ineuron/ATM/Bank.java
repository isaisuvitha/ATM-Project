package in.ineuron.ATM;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Suvitha
 *
 */
public class Bank {
	
	/**
	 * The name of the Bank
	 */
	private String bankName;
	
	/**
	 * Account Holders associated to the bank
	 */
	private ArrayList<AccountHolder> accountHolders;
	/**
	 * Accounts associated to the bank
	 */
	private ArrayList<Account> accounts;
	
	/**
	 * Create a Bank object with empty list of account holders and account
	 * 
	 * @param bankName name of the bank
	 */
	public Bank(String bankName) {
		this.bankName = bankName;
		this.accountHolders = new ArrayList<AccountHolder>();
		this.accounts = new ArrayList<Account>();
	}
	/**
	 * Generate unique user id for the account holder
	 * 
	 * @return user id of the account holder
	 */
	public String getNewUserID() {
		String userID;
		Random rno = new Random();
		int len = 6;
		boolean nonUnique;
		do {
			userID="";
			//continue looping to get unique user id
			for(int i=0;i<len;i++) {
				userID+= ((Integer)rno.nextInt(10)).toString() ;
			}
			nonUnique = false;
			//check to make sure user id is unique
			for(AccountHolder ah:this.accountHolders) {
				if(userID.compareTo(ah.getUserID())==0) {
					nonUnique = true;
					break;
				}
			}
		}while(nonUnique);
		return userID;
	}
	/**
	 * @return
	 */
	public String getNewAccountNo() {
		String accountNo;
		Random rno = new Random();
		int len = 10;
		boolean nonUnique;
		do {
			accountNo = "";
			for(int i=0; i<len; i++) {
				accountNo+=((Integer)rno.nextInt(10)).toString() ;
			}
			nonUnique = false;
			for(Account a : this.accounts) {
				if(accountNo.compareTo(a.getAccountNo())==0) {
					nonUnique = true;
					break;
				}
			}
		}while(nonUnique);
		return accountNo;
	}
	public void addAccount(Account anAccount) {
		this.accounts.add(anAccount);
	}
	/**
	 * Create a new account holder for the bank
	 * @param firstName first name of the account holder
	 * @param lastName last name of the account holder
	 * @param pin pin number of the account holder
	 * @return
	 */
	public AccountHolder addAccountHolder(String firstName, String lastName, String pin) {
		//create a new account holder and add it to our list
		AccountHolder newAccountHolder = new AccountHolder(firstName, lastName, pin, this);
		this.accountHolders.add(newAccountHolder);
		
		//create a savings account for the account holder
		Account newAccount = new Account("Savings",newAccountHolder, this);
		//adding the savings account to the account holder
		newAccountHolder.addAccount(newAccount);
		//adding the savings account to the accountlist of the bank
		this.accounts.add(newAccount);
		return newAccountHolder;
	} 
	
	/**
	 * Associate the account holder with the user id and pin if valid
	 * @param userID user id of the account holder
	 * @param pin pin number associated with the user id of the account holder
	 * @return AccountHolder object if successful
	 */
	public AccountHolder accountHolderLogin(String userID, String pin) {
		//search through the list of account holders
		for(AccountHolder ah : this.accountHolders) {
			//check if user id is correct
			if(ah.getUserID().compareTo(userID)==0 && ah.validatePin(pin)) {
				return ah;
			}
		}
		return null;
	}
	
	public String getBankName() {
		return this.bankName;
	}
}

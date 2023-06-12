package in.ineuron.ATM;

import java.util.Scanner;

public class ATM {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		Bank theBank = new Bank("Bank of Pedu");
		AccountHolder accountHolder = theBank.addAccountHolder("Tim", "Marvel", "1009");
		Account newAccount = new Account("Current",accountHolder,theBank);
		accountHolder.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		AccountHolder currentAccountHolder;
		while(true) {
			//stay in the login prompt until successful login 
			currentAccountHolder = ATM.mainMenu(theBank,sc);
			//stay in main menu until user quits
			ATM.printMenu(currentAccountHolder,sc);
		}	
	}
	public static AccountHolder mainMenu(Bank theBank,Scanner sc) {
		String userID;
		String pin;
		AccountHolder authorisedUser;
		//prompt the account holder to enter the user id and pin number until correct match
		do {
			System.out.printf("\nWelcome to %s \n\n",theBank.getBankName());
			System.out.print("User Id : ");
			userID = sc.nextLine();
			System.out.print("Pin : ");
			pin = sc.nextLine();
			//get the authorised user matching the user id pin combination
			authorisedUser = theBank.accountHolderLogin(userID, pin);
			if(authorisedUser == null) {
				System.out.println("Invalid user id or pin. Try again");
			}
		}while(authorisedUser==null);//continue looping until successful login
		return authorisedUser;
	}
	public static void printMenu(AccountHolder accountHolder, Scanner sc) {
		accountHolder.printAccountSummary();
		int choice;
		do {
			System.out.printf("Welcome %s, What would you like to do?\n",accountHolder.getFirstName());
			System.out.println("1. Show Transaction History");
			System.out.println("2. Withdraw");
			System.out.println("3. Deposit");
			System.out.println("4. Transfer");
			System.out.println("5. Quit");
			System.out.println();
			System.out.println("Enter your choice : ");
			choice = sc.nextInt();
			
			if(choice<1 || choice>5) {
				System.out.println("Enter a valid choice between 1 & 5");
			}
		}while(choice<1 || choice>5);
		switch(choice) {
		case 1:
			ATM.showTransactionHistory(accountHolder, sc);
			break;
		case 2:
			ATM.withdrawFunds(accountHolder, sc);
			break;
		case 3:
			ATM.depositFunds(accountHolder, sc);
			break;
		case 4:
			ATM.transferFunds(accountHolder, sc);
			break;
		case 5:
			//sc.nextLine();
			System.exit(0);
			//break;
		}
		if(choice!=5) {
			ATM.printMenu(accountHolder, sc);
		}
	}
	
	/**
	 * Show the transaction history of the particular account of the account holder
	 * @param accountHolder logged in account holder
	 * @param scuser input scanner object
	 */
	public static void showTransactionHistory(AccountHolder accountHolder, Scanner sc) {
		int theAccount;
		do {
			System.out.printf("Enter number (1-%d) of the account\n"+ 
						"whose transaction you want to see", accountHolder.numAccounts());
			theAccount = sc.nextInt()-1;
			if(theAccount<0 || theAccount>=accountHolder.numAccounts()) {
				System.out.println("Account Number Invalid. Try again.");
			}
		}while(theAccount<0||theAccount>=accountHolder.numAccounts());
		accountHolder.printAccountTransactionHistory(theAccount);
	}
	
	public static void transferFunds(AccountHolder accountHolder, Scanner sc) {
		int fromAccount;
		int toAccount;
		double amount;
		double accountBalance;
		
		//getting the account number to tranfer the funds from
		do {
			System.out.printf("Enter number (1-%d) of the account\n"+ 
						"to transfer funds from : ", accountHolder.numAccounts());
			fromAccount = sc.nextInt()-1;
			if(fromAccount<0 || fromAccount>=accountHolder.numAccounts()) {
				System.out.println("Account Number Invalid. Try again.");
			}
		}while(fromAccount<0||fromAccount>=accountHolder.numAccounts());
		accountBalance = accountHolder.getAccountBalance(fromAccount);
		
		//to get the account number to transfer the funds to
		do {
			System.out.printf("Enter number (1-%d) of the account\n"+ 
						"to transfer funds to : ",accountHolder.numAccounts());
			toAccount = sc.nextInt()-1;
			if(toAccount<0 || toAccount>=accountHolder.numAccounts()) {
				System.out.println("Account Number Invalid. Try again.");
			}
		}while(toAccount<0||toAccount>=accountHolder.numAccounts());
		
		// getting the amount to be transferred
		do {
			System.out.printf("Enter the amount to be transferred (max $%.02f):$", accountBalance);
			amount = sc.nextDouble();
			if(amount<0) {
				System.out.println("Amount to be transferred cannot be less than zero");
			}
			else if(amount>accountBalance){
				System.out.printf("Amount to be transferred cannot be more than\n"+
			"balance of $%.02f \n",accountBalance);
			}
		}while(amount<0 || amount>accountBalance);
		
		// transfer after conditions are met
		accountHolder.addAccountTransaction(fromAccount,-1*amount, String.format
				("Transfer to account %s", accountHolder.getAccountUUID(toAccount)));
		accountHolder.addAccountTransaction(toAccount,amount, String.format
				("Transfer from account %s", accountHolder.getAccountUUID(fromAccount)));
	}
	
	public static void withdrawFunds(AccountHolder accountHolder, Scanner sc) {
		int fromAccount;
		double amount;
		double accountBalance;
		String transactionComment;
		//getting the account number to withdraw the funds from
		do {
			System.out.printf("Enter number (1-%d) of the account\n"+ 
						"to withdraw funds from : ", accountHolder.numAccounts());
			fromAccount = sc.nextInt()-1;
			if(fromAccount<0 || fromAccount>=accountHolder.numAccounts()) {
				System.out.println("Account Number Invalid. Try again.");
			}
		}while(fromAccount<0||fromAccount>=accountHolder.numAccounts());
		accountBalance = accountHolder.getAccountBalance(fromAccount);
		// getting the amount to withdraw
		do {
			System.out.printf("Enter the amount to be withdrawn (max $%.02f):$", accountBalance);
			amount = sc.nextDouble();
			if(amount<0) {
				System.out.println("Amount to be withdrawn cannot be less than zero");
			}
			else if(amount>accountBalance){
				System.out.printf("Amount to be withdrawn cannot be more than\n"+
			"balance of $%.02f \n",accountBalance);
			}
		}while(amount<0 || amount>accountBalance);
		sc.nextLine();
		System.out.print("Enter transaction comment :");
		transactionComment = sc.nextLine();
		accountHolder.addAccountTransaction(fromAccount, -1*amount, transactionComment);
	}
	
	public static void depositFunds(AccountHolder accountHolder, Scanner sc) {
		int toAccount;
		double amount;
		double accountBalance;
		String transactionComment;
		//getting the account number to deposit funds to
		do {
			System.out.printf("Enter number (1-%d) of the account\n"+ 
						"to deposit funds to : ", accountHolder.numAccounts());
			toAccount = sc.nextInt()-1;
			if(toAccount<0 || toAccount>=accountHolder.numAccounts()) {
				System.out.println("Account Number Invalid. Try again.");
			}
		}while(toAccount<0||toAccount>=accountHolder.numAccounts());
		accountBalance = accountHolder.getAccountBalance(toAccount);
		// getting the amount to deposit
		do {
			System.out.printf("Enter the amount to be deposited (max $%.02f):$", accountBalance);
			amount = sc.nextDouble();
			if(amount<0) {
				System.out.println("Amount to be deposited cannot be less than zero");
			}
		}while(amount<0);
		sc.nextLine();
		System.out.print("Enter transaction comment :");
		transactionComment = sc.nextLine();
		accountHolder.addAccountTransaction(toAccount, amount, transactionComment);
		
	}
}

package dev.huai.services;

import dev.huai.data.UserDataImpl;
import dev.huai.models.Account;
import dev.huai.models.User;
import java.util.Scanner;

public class AccountServices {

    UserDataImpl userData = new UserDataImpl();
    Scanner sc = new Scanner(System.in);

    public AccountServices(){
        super();
    }

    public void createAccount(User user){
        userData.addAccount(user);
    }

    // Account menu displays after user selects the account
    public void accountMenu(Account account, User user){

        // Condition checks if the user is the "main user" or an authorized user
        // definition: authorized user has no access adding authorized user
        if(account.getUser_id()==user.getUserId()) {
            System.out.println("\n---Account # " + account.getAccount_id() + "---");
            System.out.println("Please choose an action:");
            System.out.println("1 - Check balance");
            System.out.println("2 - Deposit");
            System.out.println("3 - Withdraw funds");
            System.out.println("4 - Check transactions");
            System.out.println("5 - Transfer funds");
            System.out.println("6 - Add authorized user");
            System.out.println("7 - Exit account");

            //precalculate balance from transaction table and update the value into the account variable
            getBalance(account);
            int selection = sc.nextInt();
            sc.nextLine();

            switch (selection) {
                case 1:
                    System.out.println("Your account balance is $" + account.getBalance());
                    break;
                case 2:
                    addFunds(account);
                    break;
                case 3:
                    withdrawFunds(account, user);
                    break;
                case 4:
                    checkTransactions(account);
                    break;
                case 5:
                    transferFunds(account, user);
                    break;
                case 6:
                    addAuthorizedUserMenu(account);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Wrong input!");
            }
            accountMenu(account, user);
        }else{
            System.out.println("\n---Authorized Account # " + account.getAccount_id() + "---");
            System.out.println("Please choose an action:");
            System.out.println("1 - Check balance");
            System.out.println("2 - Deposit");
            System.out.println("3 - Withdraw funds");
            System.out.println("4 - Check transactions");
            System.out.println("5 - Transfer funds");
            System.out.println("6 - Exit account");

            //precalculate balance from transaction table and update the value into the account variable
            getBalance(account);
            int selection = sc.nextInt();
            sc.nextLine();

            switch (selection) {
                case 1:
                    System.out.println("Your account balance is $" + account.getBalance());
                    break;
                case 2:
                    addFunds(account);
                    break;
                case 3:
                    withdrawFunds(account, user);
                    break;
                case 4:
                    checkTransactions(account);
                    break;
                case 5:
                    transferFunds(account, user);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Wrong input!");
            }
            accountMenu(account, user);
        }
    }

    public void getBalance(Account account){
        userData.checkBalance(account);
    }

    public void addFunds(Account account){
        System.out.println("Please enter deposit amount $");
        // catch wrong format input user id
        try{
            int deposit = Integer.parseInt(sc.nextLine());
            if(deposit<0) {
                System.out.println("Please enter a positive amount!");
                addFunds(account);
            }
            userData.depositFunds(deposit, account);
        } catch (NumberFormatException e) {
            System.out.println("Wrong deposit format");
            addFunds(account);
        }
    }

    public void withdrawFunds(Account account, User user){
        System.out.println("Please enter withdraw amount $");
        // catch wrong format input user id
        try{
            int withdraw = Integer.parseInt(sc.nextLine());
            if(withdraw<0) {
                System.out.println("Please enter a positive amount!");
                withdrawFunds(account, user);
            }
            // check if overdrawing
            else if(withdraw > account.getBalance().intValue()){
                System.out.println("NO Overdrawing!");
                System.out.println("Your account balance is: $" + account.getBalance());
                accountMenu(account, user);
            }
            else {
                userData.withdrawFunds(withdraw, account);
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong withdraw format");
            withdrawFunds(account, user);
        }
    }

    public void checkTransactions(Account account){
        userData.printTransactions(account);
    }

    public void transferFunds(Account account, User user){
        System.out.println("Please enter recipient account number");
        int recipient = Integer.parseInt(sc.nextLine());

        System.out.println("Please enter transfer amount $");
        // catch wrong format input user id
        try{
            int transfer = Integer.parseInt(sc.nextLine());
            if(transfer<0) {
                System.out.println("Please enter a positive amount!");
                transferFunds(account, user);
            }
            // check if overdrawing
            else if(transfer > account.getBalance().intValue()){
                System.out.println("Funds not available!");
                System.out.println("Your account balance is: $" + account.getBalance());
                accountMenu(account, user);
            }
            else {
                userData.transferFunds(transfer, account, recipient);
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong transfer funds format");
            transferFunds(account, user);
        }
    }

    public void addAuthorizedUserMenu(Account account){
        try{
            if(account.getAuthorized_user_id()==0){
                System.out.println("Please enter authorized user ID number");
                int authorized_user_id = Integer.parseInt(sc.nextLine());
                userData.addAuthorizedUser(account, authorized_user_id);
            }
            else {
                System.out.println("Your current authorized user id:" + account.getAuthorized_user_id());
                System.out.println("Please choose an action:");
                System.out.println("1 - Update authorized user by ID");
                System.out.println("2 - Remove authorized user");
                System.out.println("3 - Return account menu");

                int selection = Integer.parseInt(sc.nextLine());
                if (selection == 1) {
                    System.out.println("Please enter authorized user ID number");
                    int authorized_user_id = Integer.parseInt(sc.nextLine());
                    userData.addAuthorizedUser(account, authorized_user_id);
                }
                else if(selection == 2){
                    userData.removeAuthorizedUser(account);
                }
                else if (selection == 3) {
                    return;
                } else {
                    System.out.println("Wrong input");
                    addAuthorizedUserMenu(account);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong input");
            addAuthorizedUserMenu(account);
        }
    }
}

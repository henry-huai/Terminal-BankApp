package dev.huai.services;

import dev.huai.data.UserData;
import dev.huai.models.Account;
import dev.huai.models.User;

import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;


public class MenuServices {

    private User user = new User();
    private UserData userData = new UserData();
    private PasswordService passwordService = new PasswordService();
    Scanner sc = new Scanner(System.in);

    public void startMenu(){
        System.out.println("---Welcome to Revature Banking---");
        System.out.println("Please choose an action:");
        System.out.println("1 - Login in");
        System.out.println("2 - Sign up");
        System.out.println("3 - Exit app");

        int selection = sc.nextInt();
        sc.nextLine();

        switch(selection) {
            case 1:
                userLogin();
                break;
            case 2:
                userSignUp();
                break;
            case 3:
                //use exit() instead of return(), otherwise it will cause infinite loop
                System.exit(0);
            default:
                System.out.println("Unrecognized action!\n");
        }
        startMenu();
    }

    public void userMenu(User user){
        System.out.println("---Account Menu---");
        System.out.println("Please choose an action:");
        System.out.println("1 - Create a new account");
        ArrayList<Account> allAccounts = userData.printAccounts(user);
        for(Account a : allAccounts){
            System.out.println(allAccounts.indexOf(a)+2+ " - "+"Account #"+ a.getAccount_id());
        }
        System.out.println(allAccounts.size()+2+" - Sign off");

        int selection = sc.nextInt();
        sc.nextLine();

        if(selection == 1){
            createAccount(user);
            userMenu(user);
        }
        else if(selection == allAccounts.size()+2){
            startMenu();
        }
        else{
            accountMenu(allAccounts.get(selection-2), user);
        }
    }

    public void accountMenu(Account account, User user){

        // check if the user is the main user or an authorized user
        // define: authorized user has no access adding authorized user
        if(account.getUser_id()==user.getUserId()) {
            System.out.println("---Account # " + account.getAccount_id() + "---");
            System.out.println("Please choose an action:");
            System.out.println("1 - Check balance");
            System.out.println("2 - Deposit");
            System.out.println("3 - Withdraw funds");
            System.out.println("4 - Check transactions");
            System.out.println("5 - Transfer funds");
            System.out.println("6 - Add an authorized user");
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
                    addAuthorizedUser(account);
                    break;
                case 7:
                    userMenu(user);
                    break;
                default:
                    System.out.println("Wrong input!");
            }
        }else{
            System.out.println("---Authorized Account # " + account.getAccount_id() + "---");
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
                    userMenu(user);
                    break;
                default:
                    System.out.println("Wrong input!");
            }
        }
        accountMenu(account, user);
    }

    public void userLogin(){

        System.out.println("---Login in---");
        System.out.println("Please enter your user ID:");
        // catch wrong format input user id
        try{
            Integer userID = Integer.parseInt(sc.nextLine());
            user.setUserId(userID);
        } catch (NumberFormatException e) {
            System.out.println("Wrong user ID format");
            userLogin();
        }

        try{
            Console console = System.console();
            // System.console() works in console, returns null in IDE
            System.out.println("Please enter your password:");
            if(console==null){
                //System.out.println("No console available");
                String password = sc.nextLine();
                user.setPassword(passwordService.encryptPassword(password));
            }
            // if no console available like testing the code in IDE
            else{
                char[] passcode = console.readPassword();
                String password = new String(passcode);
                user.setPassword(passwordService.encryptPassword(password));
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        //Arrays.fill(passcode,' ');
        if(checkUser(user)){
            userMenu(user);
        } else{
            System.out.println("User not found!");
            startMenu();
        }
    }

    public void userSignUp(){
        System.out.println("---Sign Up---");
        System.out.println("Please enter your first name:");
        String firstName = sc.nextLine();
        user.setFirstName(firstName);
        System.out.println("Please enter your last name:");
        String lastName = sc.nextLine();
        user.setLastName(lastName);
        System.out.println("Please enter your email:");
        String email = sc.nextLine();
        user.setEmail(email);
        System.out.println("Please enter your password:");
        String password = sc.nextLine();
        user.setPassword(passwordService.encryptPassword(password));
        addUser(user);
        System.out.println("Thank you for signing up!\n\n");
        startMenu();
    }

    public boolean checkUser(User user){
        return userData.checkUser(user);
    }

    public void addUser(User newUser){
        userData.addUser(newUser);
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

    public void createAccount(User user){
        userData.addAccount(user);
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

    public void addAuthorizedUser(Account account){
        System.out.println("Please enter authorized user ID number");
        int authorized_user_id = Integer.parseInt(sc.nextLine());
        userData.addAuthorizedUser(account, authorized_user_id);
    }

}

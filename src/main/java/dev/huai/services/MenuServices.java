package dev.huai.services;

import dev.huai.data.UserData;
import dev.huai.models.Account;
import dev.huai.models.User;

import java.util.ArrayList;
import java.util.Scanner;


public class MenuServices {

    private User user = new User();
    private UserData userData = new UserData();
    Scanner sc = new Scanner(System.in);

    public void startMenu(){
        System.out.println("---Welcome to Revature Banking---");
        System.out.println("Please choose an action:");
        System.out.println("1 - Login in");
        System.out.println("2 - Sign up");
        System.out.println("3 - Exit");

        //Scanner sc = new Scanner(System.in);
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
                // I have to use exit() instead of return(), it will cause infinite loop
                System.exit(0);
            default:
                System.out.println("Unrecognized action!\n");
        }
        startMenu();
    }

    public void userMenu(User user){

        System.out.println("---Account Menu---");
        System.out.println("Please choose an action?");
        System.out.println("0 - Create a new account");
        ArrayList<Account> allAccounts = userData.printAccounts(user);
        for(Account a : allAccounts){
            System.out.println(allAccounts.indexOf(a)+1+ " - "+"Account #"+ a.getAccount_id());
        }
        System.out.println(allAccounts.size()+1+" - Exit");

        int selection = sc.nextInt();
        sc.nextLine();

        if(selection == 0){
            createAccount(user);
            userMenu(user);
        }
        else if(selection == allAccounts.size()+1){
            startMenu();
        }
        else{
            //System.out.println("my selection account is "+allAccounts.get(selection));
            accountMenu(allAccounts.get(selection-1), user);
        }
    }

    public void accountMenu(Account account, User user){
        System.out.println("---Account # "+account.getAccount_id()+"---");
        System.out.println("Please choose an action?");
        System.out.println("1 - Check balance");
        System.out.println("2 - Deposit");
        System.out.println("3 - Withdraw funds");
        System.out.println("4 - Check transactions");
        System.out.println("5 - Exit");

        int selection = sc.nextInt();
        sc.nextLine();

        switch(selection) {
            case 1:
                checkBalance(account);
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
                userMenu(user);
                break;
                //return;
            default:
                System.out.println("Wrong input!");
        }
        accountMenu(account, user);
    }

    public void userLogin(){
        System.out.println("---Login in---");
        System.out.println("Please enter your user ID?");
        // catch wrong format input user id
        try{
            Integer userID = Integer.parseInt(sc.nextLine());
            //int userID = sc.nextInt();
            user.setUserId(userID);
        } catch (NumberFormatException e) {
            System.out.println("Wrong user ID format");
            userLogin();
        }
        System.out.println("Please enter your password?");
        String password = sc.nextLine();
        user.setPassword(password);
        if(checkUser(user)){
            userMenu(user);
        } else{
            System.out.println("User not found!");
            startMenu();
        }

    }

    public void userSignUp(){
        System.out.println("---Sign Up---");
        System.out.println("Please enter your first name?");
        String firstName = sc.nextLine();
        user.setFirstName(firstName);
        System.out.println("Please enter your last name?");
        String lastName = sc.nextLine();
        user.setLastName(lastName);
        System.out.println("Please enter your email?");
        String email = sc.nextLine();
        user.setEmail(email);
        System.out.println("Please enter your password?");
        String password = sc.nextLine();
        user.setPassword(password);
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

    public void checkBalance(Account account){
        System.out.println("Your current balance is: $" + account.getBalance());
    }

    public void addFunds(Account account){
        System.out.println("Please enter deposit amount $");
        // catch wrong format input user id
        try{
            //Integer deposit = sc.nextInt();
            Integer deposit = Integer.parseInt(sc.nextLine());
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
            //Integer deposit = sc.nextInt();
            Integer withdraw = Integer.parseInt(sc.nextLine());
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

    public void getAccounts(User user){
        userData.printAccounts(user);
    }

    public void createAccount(User user){
        //System.out.println("");
        userData.addAccount(user);
    }

}

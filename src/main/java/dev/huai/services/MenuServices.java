package dev.huai.services;

import dev.huai.data.UserData;
import dev.huai.models.User;

import java.math.BigDecimal;
import java.sql.SQLOutput;
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
                //jump to login in
                userLogin();
                break;
            case 2:
                //jump to sign up
                userSignUp();
                break;
            case 3:
                return;
            default:
                System.out.println("Unrecognized action!\n");
        }
        startMenu();
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
            accountMenu(user);
        } else{
            System.out.println("User not found!");
            startMenu();
        }

    }

    public boolean checkUser(User user){
        return userData.checkUser(user);
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

    public void addUser(User newUser){
        userData.addUser(newUser);
    }

    public void accountMenu(User user){
        System.out.println("---Account Menu---");
        System.out.println("Please choose an action?");
        System.out.println("1 - Check balance");
        System.out.println("2 - Deposit");
        System.out.println("3 - Withdraw funds");
        System.out.println("4 - Exit");

        int selection = sc.nextInt();
        sc.nextLine();

        switch(selection) {
            case 1:
                checkBalance(user);
                break;
            case 2:
                addFunds(user);
                break;
            case 3:
                withdrawFunds(user);
                break;
            case 4:
                return;
            default:
                System.out.println("Wrong input!");
        }
        accountMenu(user);
    }

    public void checkBalance(User user){
        System.out.println("Your current balance is: $" + user.getBalance());
    }

    public void addFunds(User user){
        System.out.println("Please enter deposit amount $");
        // catch wrong format input user id
        try{
            //Integer deposit = sc.nextInt();
            Integer deposit = Integer.parseInt(sc.nextLine());
            if(deposit<0) {
                System.out.println("Please enter a positive amount!");
                addFunds(user);
            }
            userData.depositFunds(deposit, user);
        } catch (NumberFormatException e) {
            System.out.println("Wrong deposit format");
            addFunds(user);
        }
    }

    public void withdrawFunds(User user){
        System.out.println("Please enter withdraw amount $");
        // catch wrong format input user id
        try{
            //Integer deposit = sc.nextInt();
            Integer withdraw = Integer.parseInt(sc.nextLine());
            if(withdraw<0) {
                System.out.println("Please enter a positive amount!");
                withdrawFunds(user);
            }
            // check if overdrawing
            else if(withdraw > user.getBalance().intValue()){
                System.out.println("NO Overdrawing!");
                System.out.println("Your account balance is: $" + user.getBalance());
                withdrawFunds(user);
            }
            else {
                userData.withdrawFunds(withdraw, user);
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong withdraw format");
            withdrawFunds(user);
        }
    }

}

package dev.huai.services;

import dev.huai.data.UserDataImpl;
import dev.huai.models.Account;
import dev.huai.models.User;

import java.io.Console;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserServices {

    private User user = new User();
    private ArrayList<Account> allAccounts;
    UserDataImpl userData = new UserDataImpl();
    AccountServices accountServices = new AccountServices();
    PasswordService passwordService = new PasswordService();
    Scanner sc = new Scanner(System.in);

    public UserServices(){
        super();
    }

    // User menu displays after user pass verification
    public void userMenu(User user){
        System.out.println("\n---Account Menu---");
        System.out.println("Please choose an action:");
        System.out.println("1 - Create a new account");
        allAccounts = userData.getAccounts(user);
        for(Account a : allAccounts){
            System.out.println(allAccounts.indexOf(a)+2+ " - "+"Account #"+ a.getAccount_id());
        }
        System.out.println(allAccounts.size()+2+" - Sign off");

        try {
            int selection = sc.nextInt();
            sc.nextLine();

            if (selection == 1) {
                accountServices.createAccount(user);
                userMenu(user);
            } else if (selection == allAccounts.size() + 2) {
                return;
            } else if(selection <  allAccounts.size() + 2){
                accountServices.accountMenu(allAccounts.get(selection - 2), user);
                userMenu(user);
            }
            else{
                System.out.println("Wrong action");
                userMenu(user);
            }
        }catch(InputMismatchException e) {
            System.out.println("Wrong action");
            userMenu(user);
        }
        //userMenu(user);
    }


    public void userLogin(){

        System.out.println("\n---Login in---");
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
            return;
        }
    }

    public void userSignUp(){
        System.out.println("\n---Sign Up---");
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
        return;
    }

    public boolean checkUser(User user){
        return userData.checkUser(user);
    }

    public void addUser(User newUser){
        userData.addUser(newUser);
    }


}

package dev.huai.services;

import dev.huai.data.UserData;
import dev.huai.models.User;

import java.util.Scanner;

public class MenuServices {

    private User user = new User();
    private UserData userData = new UserData();
    Scanner sc = new Scanner(System.in);

    public void startMenu(){
        System.out.println("###Welcome to Revature Banking###");
        System.out.println("Please choose an action:");
        System.out.println("1 - Login in");
        System.out.println("2 - Sign up");
        System.out.println("3 - Exit");

        Scanner sc = new Scanner(System.in);
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
                System.out.println("Wrong input!");
        }
    }

    public void userLogin(){
        System.out.println("###Login in###");
        System.out.println("Please enter your user ID?");
        // catch wrong format input user id
        try{
            Integer userID = Integer.parseInt(sc.nextLine());
            user.setUserId(userID);
        } catch (NumberFormatException e) {
            System.out.println("Wrong user ID format");
            userLogin();
        }
        System.out.println("Please enter your password?");
        String password = sc.nextLine();
        user.setPassword(password);
        if(checkUser(user)){
            accountMenu();
        } else{
            System.out.println("User not found!");
            startMenu();
        }

    }

    public boolean checkUser(User user){
        return userData.checkUser(user);
    }

    public void userSignUp(){

        System.out.println("###Sign Up###");
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

    public void accountMenu(){
        System.out.println("###Account Menu");
        System.out.println("Please choose an action?");

    }

}

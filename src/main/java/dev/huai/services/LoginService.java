package dev.huai.services;

import dev.huai.models.User;

import java.io.Console;
import java.sql.SQLOutput;
import java.util.Scanner;

public class LoginService {

    Scanner sc = new Scanner(System.in);
    private User newUser = new User();
    private SignUpService signupService = new SignUpService();

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

    public void userSignUp(){

        System.out.println("###Sign Up###");
        System.out.println("Please enter your first name?");
        String firstName = sc.nextLine();
        newUser.setFirstName(firstName);
        System.out.println("Please enter your last name?");
        String lastName = sc.nextLine();
        newUser.setLastName(lastName);
        System.out.println("Please enter your email?");
        String email = sc.nextLine();
        newUser.setEmail(email);
        System.out.println("Please enter your password?");
        String password = sc.nextLine();
        newUser.setPassword(password);
        signupService.addUser(newUser);
        System.out.println("Thank you for signing up!\n\n");
        startMenu();
    }


}

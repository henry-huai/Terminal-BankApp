package dev.huai.services;

import java.util.Scanner;

public class MenuServices {

    UserServices userServices = new UserServices();
    Scanner sc = new Scanner(System.in);

    // Main menu displays
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
                userServices.userLogin();
                break;
            case 2:
                userServices.userSignUp();
                break;
            case 3:
                System.exit(0);
            default:
                System.out.println("Unrecognized action!\n");
        }
        startMenu();
    }










}

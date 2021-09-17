package dev.huai;

import dev.huai.services.LoginService;

public class App {
    public static void main(String args[]){
        LoginService menu = new LoginService();
        menu.startMenu();
    }
}

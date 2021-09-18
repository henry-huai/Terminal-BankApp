package dev.huai;

import dev.huai.services.MenuServices;

public class App {
    public static void main(String args[]){
        MenuServices menu = new MenuServices();
        menu.startMenu();
    }
}

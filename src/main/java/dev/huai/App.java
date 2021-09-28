/*
* Author: Renhan Huai
* Date:   9/25/2021
* Description: Project_0 during Raveture training
* */

package dev.huai;

import dev.huai.services.MenuServices;

public class App {
    public static void main(String[] args){
        /*
         * This is the main method
         * which creates MenuServices() object
         * to start the program
         */
        MenuServices menu = new MenuServices();
        menu.startMenu();
    }
}


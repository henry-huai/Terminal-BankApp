package dev.huai.services;

import dev.huai.data.UserData;
import dev.huai.models.User;

public class SignUpService {

    private UserData userData = new UserData();

    public void addUser(User newUser){
        userData.addUser(newUser);
    }
}

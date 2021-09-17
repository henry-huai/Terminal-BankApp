package dev.huai.data;

import dev.huai.models.User;
import dev.huai.services.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserData {
    private ConnectionService connectionService = new ConnectionService();

    public void addUser(User newUser){
        addUserBySQL(newUser);
    }

    private void addUserBySQL(User newUser){
        String sql = "insert into users(first_name, last_name, email, password) values(?, ?, ?, ?)";

        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, newUser.getFirstName());
            stmt.setString(2, newUser.getLastName());
            stmt.setString(3, newUser.getEmail());
            stmt.setString(4, newUser.getPassword());

            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

package dev.huai.data;

import dev.huai.models.User;
import dev.huai.services.ConnectionService;

import java.sql.*;

public class UserData {
    private ConnectionService connectionService = new ConnectionService();

    public void addUser(User newUser){
        addUserBySQL(newUser);
    }

    public boolean checkUser(User user){
        return checkUserBySQL(user);
    }

    public void depositFunds(Integer deposit, User user){
        depositBySQL(deposit, user);
    }

    public void withdrawFunds(Integer deposit, User user){
        withdrawBySQL(deposit, user);
    }


    private void addUserBySQL(User newUser){
        String sql = "insert into users(first_name, last_name, email, pass_word) values(?, ?, ?, ?) returning user_id";

        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, newUser.getFirstName());
            stmt.setString(2, newUser.getLastName());
            stmt.setString(3, newUser.getEmail());
            stmt.setString(4, newUser.getPassword());
            //store the return value user_id from database
            ResultSet rs = stmt.executeQuery();
            rs.next();
            System.out.println("Your login user ID is: " + rs.getInt("user_id"));;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean checkUserBySQL(User user){
        String sql = "select * from users where user_id =? and pass_word = ?";
        try{
            Connection c = connectionService.establishConnection();
            //Statement stmt = c.createStatement();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, user.getUserId());
            stmt.setString(2, user.getPassword());

            //store the return value user_id from database
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setBalance(rs.getBigDecimal("amount_change"));
                System.out.println(user.getFirstName() + " " + user.getLastName() + ", Welcome to your account! ");
                return true;
            }
            else
                //System.out.println("wrong user");
                return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    return false;
    }

    private void depositBySQL(Integer deposit, User user){
        String sql = "update users set amount_change = amount_change + ? where user_id = ? returning amount_change" ;
        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, deposit);
            stmt.setInt(2, user.getUserId());
            //store the return value user_id from database
            ResultSet rs = stmt.executeQuery();
            rs.next();
            user.setBalance(rs.getBigDecimal("amount_change"));
            System.out.println("Your account balance is: $" + rs.getBigDecimal("amount_change"));;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void withdrawBySQL(Integer withdraw, User user){
        String sql = "update users set amount_change = amount_change - ? where user_id = ? returning amount_change" ;
        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, withdraw);
            stmt.setInt(2, user.getUserId());
            //store the return value user_id from database
            ResultSet rs = stmt.executeQuery();
            rs.next();
            user.setBalance(rs.getBigDecimal("amount_change"));
            System.out.println("Your account balance is: $" + rs.getBigDecimal("amount_change"));;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

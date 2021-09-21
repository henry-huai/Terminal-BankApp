package dev.huai.data;

import dev.huai.models.Transaction;
import dev.huai.models.User;
import dev.huai.services.ConnectionService;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

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

    public void printTransactions(User user){
        printTransactionsBySQL(user);
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
                user.setBalance(rs.getBigDecimal("balance"));
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
        String sql1 = "update users set balance = balance + ? where user_id = ? returning balance";
        String sql2 = "insert into account (user_id, amount_change) values(?, ?)" ;
        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt1 = c.prepareStatement(sql1);
            stmt1.setInt(1, deposit);
            stmt1.setInt(2, user.getUserId());
            PreparedStatement stmt2 = c.prepareStatement(sql2);
            stmt2.setInt(1, user.getUserId());
            stmt2.setInt(2, deposit);
            //store the return value user_id from database
            stmt2.executeUpdate();
            ResultSet rs = stmt1.executeQuery();

            rs.next();
            user.setBalance(rs.getBigDecimal("balance"));
            System.out.println("Your account balance is: $" + rs.getBigDecimal("balance"));;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void withdrawBySQL(Integer withdraw, User user){
        String sql1 = "update users set balance = balance - ? where user_id = ? returning balance" ;
        String sql2 = "insert into account (user_id, amount_change) values(?, ?)" ;
        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt1 = c.prepareStatement(sql1);
            stmt1.setInt(1, withdraw);
            stmt1.setInt(2, user.getUserId());
            PreparedStatement stmt2 = c.prepareStatement(sql2);
            stmt2.setInt(1, user.getUserId());
            stmt2.setInt(2, -withdraw);
            //store the return value user_id from database
            stmt2.executeUpdate();
            ResultSet rs = stmt1.executeQuery();
            rs.next();
            user.setBalance(rs.getBigDecimal("balance"));
            System.out.println("Your account balance is: $" + rs.getBigDecimal("balance"));;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void printTransactionsBySQL(User user){
        String sql = "select * from account where user_id = ?";
        //ArrayList<Transaction> allTransactions = new ArrayList<Transaction>();
        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1,user.getUserId());
            ResultSet rs = stmt.executeQuery();

            System.out.println("Here are your transactions:");
            while(rs.next()){
                Date date = rs.getDate("transaction_date");
                BigDecimal amount_change = rs.getBigDecimal("amount_change");
                Transaction t = new Transaction();
                t.setTransaction_date(date);
                t.setAmount_change(amount_change);
                System.out.println(date +" " + amount_change);
                //allTransactions.add(t);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

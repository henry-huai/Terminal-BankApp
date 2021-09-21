package dev.huai.data;

import dev.huai.models.Account;
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

    public void depositFunds(Integer deposit, Account account){
        depositBySQL(deposit, account);
    }

    public void withdrawFunds(Integer deposit, Account account){
        withdrawBySQL(deposit, account);
    }

    public void printTransactions(Account account){
        printTransactionsBySQL(account);
    }

    public ArrayList<Account> printAccounts(User user){
        return printAccountsBySQL(user);
    }

    public void addAccount(User user){
        addAccountBySQL(user);
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
                //user.setBalance(rs.getBigDecimal("balance"));
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

    private void depositBySQL(Integer deposit, Account account){
        String sql1 = "update accounts set balance = balance + ? where account_id = ? returning balance";
        String sql2 = "insert into transactions (account_id, amount_change) values(?, ?)" ;
        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt1 = c.prepareStatement(sql1);
            stmt1.setInt(1, deposit);
            stmt1.setInt(2, account.getAccount_id());
            PreparedStatement stmt2 = c.prepareStatement(sql2);
            stmt2.setInt(1, account.getAccount_id());
            stmt2.setInt(2, deposit);
            //store the return value user_id from database
            stmt2.executeUpdate();
            ResultSet rs = stmt1.executeQuery();

            rs.next();
            account.setBalance(rs.getBigDecimal("balance"));
            System.out.println("Your account balance is: $" + rs.getBigDecimal("balance"));;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void withdrawBySQL(Integer withdraw, Account account){
        String sql1 = "update accounts set balance = balance - ? where account_id = ? returning balance" ;
        String sql2 = "insert into transactions (account_id, amount_change) values(?, ?)" ;
        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt1 = c.prepareStatement(sql1);
            stmt1.setInt(1, withdraw);
            stmt1.setInt(2, account.getAccount_id());
            PreparedStatement stmt2 = c.prepareStatement(sql2);
            stmt2.setInt(1, account.getAccount_id());
            stmt2.setInt(2, -withdraw);
            //store the return value user_id from database
            stmt2.executeUpdate();
            ResultSet rs = stmt1.executeQuery();
            rs.next();
            account.setBalance(rs.getBigDecimal("balance"));
            System.out.println("Your account balance is: $" + rs.getBigDecimal("balance"));;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void printTransactionsBySQL(Account account){
        String sql = "select * from transactions where account_id = ?";
        //ArrayList<Transaction> allTransactions = new ArrayList<Transaction>();
        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1,account.getAccount_id());
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

    private ArrayList<Account> printAccountsBySQL(User user){
        String sql = "select * from accounts where user_id = ?";
        ArrayList<Account> allAccounts = new ArrayList<Account>();
        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1,user.getUserId());
            ResultSet rs = stmt.executeQuery();

            //System.out.println("Here are your transactions:");
            while(rs.next()){
                Account a = new Account();
                a.setAccount_id(rs.getInt("account_id"));
                a.setUser_id(user.getUserId());
                a.setBalance(rs.getBigDecimal("balance"));
                allAccounts.add(a);
                //System.out.println("Account #"+ a.getAccount_id());
                //allTransactions.add(t);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allAccounts;
    }

    private void addAccountBySQL(User user){
        String sql = "insert into accounts (user_id) values(?) returning account_id";
        //ArrayList<Account> allAccount = new ArrayList<Account>();
        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, user.getUserId());

            //store the return value user_id from database
            ResultSet rs = stmt.executeQuery();
            rs.next();
            System.out.println("Your new account number is: " + rs.getInt("account_id"));;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

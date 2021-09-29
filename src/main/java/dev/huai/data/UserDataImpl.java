package dev.huai.data;

import dev.huai.models.Account;
import dev.huai.models.Transaction;
import dev.huai.models.User;
import dev.huai.services.ConnectionService;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class UserDataImpl implements UserDataDao {
    private ConnectionService connectionService = new ConnectionService();
    //private final Logger logger = Logger.getLogger(String.valueOf(UserDataImpl.class));

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

    public void transferFunds(Integer deposit, Account account, Integer recipient){
        transferBySQL(deposit, account, recipient);
    }

    public void printTransactions(Account account){
        printTransactionsBySQL(account);
    }

    public ArrayList<Account> getAccounts(User user){
        return getAccountsBySQL(user);
    }

    public void addAccount(User user){
        addAccountBySQL(user);
    }

    public void checkBalance(Account account){
        checkBalanceBySQL(account);
    }

    public void addAuthorizedUser(Account account, Integer authorized_user_id){
        addAuthorizedUserBySQL(account, authorized_user_id);
    }

    public void removeAuthorizedUser(Account account){
        removeAuthorizedUserBySQL(account);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkUserBySQL(User user){

        String sql = "select * from users where user_id =? and pass_word = ?";

        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, user.getUserId());
            stmt.setString(2, user.getPassword());

            //store the return value user_id from database
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                System.out.println(user.getFirstName() + " " + user.getLastName() + ", Welcome to your account! ");
                return true;
            }
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return false;
    }

    private void checkBalanceBySQL(Account account){

        String sql = "select coalesce(sum(amount_change), 0.00) as balance from transactions where account_id = ?;";

        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, account.getAccount_id());

            //store the return value user_id from database
            ResultSet rs = stmt.executeQuery();
            rs.next();
            account.setBalance(rs.getBigDecimal("balance"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void depositBySQL(Integer deposit, Account account){

        String sql = "insert into transactions (account_id, amount_change) values(?, ?)" ;

        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, account.getAccount_id());
            stmt.setInt(2, deposit);
            account.setBalance(account.getBalance().add(BigDecimal.valueOf(deposit)));

            stmt.executeUpdate();
            //logger.info("Deposit Transaction completed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void withdrawBySQL(Integer withdraw, Account account){

        String sql = "insert into transactions (account_id, amount_change) values(?, ?)" ;

        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, account.getAccount_id());
            stmt.setInt(2, -withdraw);

            account.setBalance(account.getBalance().subtract(BigDecimal.valueOf(withdraw)));
            stmt.executeUpdate();
            //logger.info("Withdraw Transaction completed");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void transferBySQL(Integer transfer, Account account, Integer recipient){

        String sql = "BEGIN;select account_id from accounts where account_id = ?; insert into transactions (account_id, amount_change) values(?, ?);insert into transactions (account_id, amount_change) values(?, ?); COMMIT;" ;

        try{

            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, recipient);
            stmt.setInt(2, recipient);
            stmt.setInt(3, transfer);
            stmt.setInt(4,account.getAccount_id());
            stmt.setInt(5,-transfer);
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("The recipient account doesn't exit");
        }
    }


    private void printTransactionsBySQL(Account account){

        String sql = "select * from transactions where account_id = ?";

        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1,account.getAccount_id());
            ResultSet rs = stmt.executeQuery();

            System.out.println("Here are your transactions:");

            // create transaction object and print out all transactions
            while(rs.next()){
                Date date = rs.getDate("transaction_date");
                BigDecimal amount_change = rs.getBigDecimal("amount_change");
                Transaction t = new Transaction();
                t.setTransaction_date(date);
                t.setAmount_change(amount_change);
                System.out.println(date +" " + amount_change);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Account> getAccountsBySQL(User user){

        String sql = "select * from accounts account_id where user_id = ? or authorized_user_id = ? order by account_id ASC";
        ArrayList<Account> allAccounts = new ArrayList<Account>();

        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1,user.getUserId());
            stmt.setInt(2,user.getUserId());
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Account a = new Account();
                a.setAccount_id(rs.getInt("account_id"));
                a.setUser_id(rs.getInt("user_id"));
                a.setAuthorized_user_id(rs.getInt("authorized_user_id"));
                allAccounts.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAccounts;
    }

    private void addAccountBySQL(User user){

        String sql = "insert into accounts (user_id,authorized_user_id) values(?, null) returning account_id";

        try{
            Connection c = connectionService.establishConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, user.getUserId());

            //store the return value user_id from database
            ResultSet rs = stmt.executeQuery();
            rs.next();
            System.out.println("Your new account number is: " + rs.getInt("account_id"));;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addAuthorizedUserBySQL(Account account, Integer authorized_user_id){

        String sql = "BEGIN; select user_id from users where user_id = ?; update accounts set authorized_user_id = ? where account_id = ?; COMMIT;";

        try{
            Connection c = connectionService.establishConnection();

            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, authorized_user_id);
            stmt.setInt(2, authorized_user_id);
            stmt.setInt(3, account.getAccount_id());
            stmt.execute();

            // if transactions go through successfully, update the account authorized_user
            account.setAuthorized_user_id(authorized_user_id);
        } catch (SQLException e) {
            System.out.println("The user id doesn't exit");
        }
    }

    private void removeAuthorizedUserBySQL(Account account) {
        String sql = "update accounts set authorized_user_id = null where account_id = ?";

        try {
            Connection c = connectionService.establishConnection();

            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, account.getAccount_id());
            stmt.execute();

            // if transactions go through successfully, update the account authorized_user
            account.setAuthorized_user_id(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package huai.data;

import huai.models.Account;
import huai.models.User;

import java.util.ArrayList;

public interface UserDataDao {
    public void addUser(User newUser);

    public boolean checkUser(User user);

    public void depositFunds(Integer deposit, Account account);

    public void withdrawFunds(Integer deposit, Account account);

    public void transferFunds(Integer deposit, Account account, Integer recipient);

    public void printTransactions(Account account);

    public ArrayList<Account> getAccounts(User user);

    public void addAccount(User user);

    public void checkBalance(Account account);

    public void addAuthorizedUser(Account account, Integer authorized_user_id);
}

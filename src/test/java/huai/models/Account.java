package huai.models;

import java.math.BigDecimal;

public class Account {
    private int account_id;
    private int user_id;
    private BigDecimal balance = BigDecimal.ZERO;
    private int authorized_user_id;



    public int getAuthorized_user_id() {
        return authorized_user_id;
    }

    public void setAuthorized_user_id(int authorized_user_id) {
        this.authorized_user_id = authorized_user_id;
    }

    public Account(){
        super();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

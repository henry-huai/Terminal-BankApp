package dev.huai.models;

import java.math.BigDecimal;

public class Account {
    private String transaction_date;
    private BigDecimal total;


    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}

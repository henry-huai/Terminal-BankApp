package huai.models;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private Date transaction_date;
    private BigDecimal amount_change;

    public Transaction(){
        super();
    }

    public Date getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(Date transaction_date) {
        this.transaction_date = transaction_date;
    }

    public BigDecimal getAmount_change() {
        return amount_change;
    }

    public void setAmount_change(BigDecimal amount_change) {
        this.amount_change = amount_change;
    }
}

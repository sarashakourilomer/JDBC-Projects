import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private int transactionId;
    private int accountId;
    private String transactionType;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

    public Transaction(
            int transactionId,
            int accountId,
            String transactionType,
            BigDecimal amount,
            LocalDateTime transactionDate) {

        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    @Override
    public String toString() {
        return transactionId +
                " | " +
                transactionType +
                " | " +
                amount +
                " | " +
                transactionDate;
    }
}
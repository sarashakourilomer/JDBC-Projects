import java.math.BigDecimal;

public class Account {

    private int accountId;
    private BigDecimal balance;
    private String accountType;
    private int customerId;

    public Account(int accountId, BigDecimal balance, String accountType, int customerId) {
        this.accountId = accountId;
        this.balance = balance;
        this.accountType = accountType;
        this.customerId = customerId;
    }

    public int getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public int getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return accountId + " | " + accountType + " | " + balance + " | Customer: " + customerId;
    }
}

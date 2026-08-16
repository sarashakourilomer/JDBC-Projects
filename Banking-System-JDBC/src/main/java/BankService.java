import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class BankService {

    private final TransactionDAO transactionDAO;

    public BankService() {
        transactionDAO = new TransactionDAO();
    }

    public boolean deposit(int accountId, BigDecimal amount) {

        String sql =
                "UPDATE accounts " +
                        "SET balance = balance + ? " +
                        "WHERE account_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                try (PreparedStatement statement =
                             connection.prepareStatement(sql)) {

                    statement.setBigDecimal(1, amount);
                    statement.setInt(2, accountId);

                    int updated = statement.executeUpdate();

                    if (updated == 0) {
                        connection.rollback();
                        return false;
                    }
                }

                transactionDAO.addTransaction(
                        connection,
                        accountId,
                        "Deposit",
                        amount
                );

                connection.commit();

                return true;

            } catch (Exception e) {

                connection.rollback();
                e.printStackTrace();

                return false;
            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean withdraw(int accountId, BigDecimal amount) {

        String sql =
                "UPDATE accounts " +
                        "SET balance = balance - ? " +
                        "WHERE account_id = ? AND balance >= ?";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                try (PreparedStatement statement =
                             connection.prepareStatement(sql)) {

                    statement.setBigDecimal(1, amount);
                    statement.setInt(2, accountId);
                    statement.setBigDecimal(3, amount);

                    int updated = statement.executeUpdate();

                    if (updated == 0) {
                        connection.rollback();
                        return false;
                    }
                }

                transactionDAO.addTransaction(
                        connection,
                        accountId,
                        "Withdrawal",
                        amount
                );

                connection.commit();

                return true;

            } catch (Exception e) {

                connection.rollback();
                e.printStackTrace();

                return false;
            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean transfer(
            int senderId,
            int receiverId,
            BigDecimal amount) {

        String withdrawSql =
                "UPDATE accounts " +
                        "SET balance = balance - ? " +
                        "WHERE account_id = ? AND balance >= ?";

        String depositSql =
                "UPDATE accounts " +
                        "SET balance = balance + ? " +
                        "WHERE account_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                try (PreparedStatement withdrawStatement =
                             connection.prepareStatement(
                                     withdrawSql)) {

                    withdrawStatement.setBigDecimal(1, amount);
                    withdrawStatement.setInt(2, senderId);
                    withdrawStatement.setBigDecimal(3, amount);

                    int withdrawn =
                            withdrawStatement.executeUpdate();

                    if (withdrawn == 0) {
                        connection.rollback();
                        return false;
                    }
                }

                try (PreparedStatement depositStatement =
                             connection.prepareStatement(
                                     depositSql)) {

                    depositStatement.setBigDecimal(1, amount);
                    depositStatement.setInt(2, receiverId);

                    int deposited =
                            depositStatement.executeUpdate();

                    if (deposited == 0) {
                        connection.rollback();
                        return false;
                    }
                }

                transactionDAO.addTransaction(
                        connection,
                        senderId,
                        "Transfer",
                        amount
                );

                transactionDAO.addTransaction(
                        connection,
                        receiverId,
                        "Transfer",
                        amount
                );

                connection.commit();

                return true;

            } catch (Exception e) {

                connection.rollback();
                e.printStackTrace();

                return false;
            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}
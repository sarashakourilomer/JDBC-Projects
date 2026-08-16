import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransactionDAO {

    public void addTransaction(
            Connection connection,
            int accountId,
            String transactionType,
            BigDecimal amount) throws java.sql.SQLException {

        String sql =
                "INSERT INTO transactions " +
                        "(account_id, transaction_type, amount) " +
                        "VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, accountId);
            preparedStatement.setString(2, transactionType);
            preparedStatement.setBigDecimal(3, amount);

            preparedStatement.executeUpdate();
        }
    }

    public void showTransactionsByAccount(int accountId) {

        String sql =
                "SELECT * FROM transactions " +
                        "WHERE account_id = ? " +
                        "ORDER BY transaction_date";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, accountId);

            try (ResultSet result =
                         preparedStatement.executeQuery()) {

                boolean found = false;

                while (result.next()) {

                    found = true;

                    int id = result.getInt("transaction_id");
                    String type = result.getString("transaction_type");
                    BigDecimal amount = result.getBigDecimal("amount");
                    String date = result.getTimestamp("transaction_date").toString();

                    System.out.println(
                            id + " | " +
                                    type + " | " +
                                    amount + " | " +
                                    date
                    );
                }

                if (!found) {
                    System.out.println("No transactions found.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
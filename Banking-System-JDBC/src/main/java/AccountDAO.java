import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AccountDAO {

    public Account getAccountById(int accountId) {

        String sql = "SELECT * FROM accounts WHERE account_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, accountId);

            try (ResultSet result = preparedStatement.executeQuery()) {

                if (result.next()) {

                    int id = result.getInt("account_id");
                    BigDecimal balance = result.getBigDecimal("balance");
                    String accountType = result.getString("account_type");
                    int customerId = result.getInt("customer_id");

                    return new Account(
                            id,
                            balance,
                            accountType,
                            customerId
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Account addAccount(
            BigDecimal balance,
            String accountType,
            int customerId) {

        String sql =
                "INSERT INTO accounts " +
                        "(balance, account_type, customer_id) " +
                        "VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setBigDecimal(1, balance);
            preparedStatement.setString(2, accountType);
            preparedStatement.setInt(3, customerId);

            preparedStatement.executeUpdate();

            try (ResultSet result =
                         preparedStatement.getGeneratedKeys()) {

                if (result.next()) {

                    int accountId = result.getInt(1);

                    return new Account(
                            accountId,
                            balance,
                            accountType,
                            customerId
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
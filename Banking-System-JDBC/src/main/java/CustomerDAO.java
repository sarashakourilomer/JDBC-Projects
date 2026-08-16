import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDAO {

    public Customer getCustomerById(int customerId) {

        String sql =
                "SELECT * FROM customers " +
                        "WHERE customer_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, customerId);

            try (ResultSet result =
                         preparedStatement.executeQuery()) {

                if (result.next()) {

                    int id =
                            result.getInt("customer_id");

                    String firstName =
                            result.getString("first_name");

                    String lastName =
                            result.getString("last_name");

                    return new Customer(
                            id,
                            firstName,
                            lastName
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addCustomer(
            String firstName,
            String lastName) {

        String sql =
                "INSERT INTO customers " +
                        "(first_name, last_name) " +
                        "VALUES (?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            preparedStatement.executeUpdate();

            System.out.println("Customer added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(
            int customerId,
            String firstName,
            String lastName) {

        String sql =
                "UPDATE customers " +
                        "SET first_name = ?, last_name = ? " +
                        "WHERE customer_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, customerId);

            int updated =
                    preparedStatement.executeUpdate();

            if (updated > 0) {
                System.out.println("Customer updated!");
            } else {
                System.out.println("Customer not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int customerId) {

        String sql =
                "DELETE FROM customers " +
                        "WHERE customer_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, customerId);

            int deleted =
                    preparedStatement.executeUpdate();

            if (deleted > 0) {
                System.out.println("Customer deleted!");
            } else {
                System.out.println("Customer not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
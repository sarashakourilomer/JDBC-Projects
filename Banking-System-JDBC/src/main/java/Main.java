import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("src/main/resources/database.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            System.out.println("Connected to PostgreSQL!");

            String sql = "DELETE FROM customers WHERE customer_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setInt(1, 7);

                int rowsDeleted = preparedStatement.executeUpdate();

                System.out.println("Rows deleted: " + rowsDeleted);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
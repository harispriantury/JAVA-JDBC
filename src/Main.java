import java.sql.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //template "jdbc:provider://localhost:<port>/<name-database>
        String url = "jdbc:postgresql://localhost:5432/wmb_db";
        String password = System.getenv("DB_PASSWORD");String username = System.getenv("DB_USERNAME");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            try (Statement statement = connection.createStatement()) {
                String query = "SELECT " +
                        "id, customer_name, mobile_phone_no, is_member " +
                        "FROM m_customer";

                ResultSet resultSet = statement.executeQuery(query);

                //print
                while (resultSet.next()) {
                    System.out.println("-".repeat(50));
                    System.out.println("ID : " + resultSet.getInt("id"));
                    System.out.println("Customer Name : " + resultSet.getString(2));
                    System.out.println("Mobile Phone Number : " + resultSet.getString("mobile_phone_no"));
                    System.out.println("Is Member : " + resultSet.getBoolean("is_member"));
                    System.out.println("-".repeat(50));
                }
                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        saveCustomer(url, username, password);

    }

    private static void saveCustomer(String url, String username, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String query = "INSERT INTO m_customer " +
                    "(id, customer_name, mobile_phone_no, is_member) " +
                    "values " +
                    "('9', 'budi', '0894839832', false ) ";

            int result = statement.executeUpdate(query);
            if (result<=0){
                throw new RuntimeException("gagal");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
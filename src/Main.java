import com.enigma.belajar_jdbc.config.DBConnector;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        getCustomerByID(1);
//        getAllCustomer();
//        createCustomer(9, "budi", "0384739209", true);
//        updateCustomer(9, "budi new", "0000", false);
//        deleteCustomer(9);
//        createWithBatch();

//        loginUser();
    }

    private static void loginUser() {
        try (Connection connection = DBConnector.connect()) {
            String query = "SELECT username FROM " +
                    "m_user_credential WHERE " +
                    "username= ? AND password= ?";
            PreparedStatement statement = connection.prepareStatement(query);
            Scanner scanner = new Scanner(System.in);
            System.out.println("masukan username : ");
            String username = scanner.nextLine();
            System.out.println("masukan password : ");
            String password = scanner.nextLine();
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("successfully login : " + resultSet.getString(1));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getCustomerByID(int id) {
        try (Connection connection = DBConnector.connect()) {
            String query = "SELECT * FROM m_customer WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("ID : " + resultSet.getInt("id"));
                System.out.println("Customer Name : " + resultSet.getString("customer_name"));
                System.out.println("Phone Number : " + resultSet.getString(3));
                System.out.println("Is Member : " + resultSet.getBoolean(4));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("error while create connection : " + e.getMessage());
        }
    }

    private static void getAllCustomer() {
        try (Connection connection = DBConnector.connect()) {
            String query = "SELECT * FROM m_customer ";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("-".repeat(50));
                System.out.println("ID : " + resultSet.getInt("id"));
                System.out.println("Customer Name : " + resultSet.getString("customer_name"));
                System.out.println("Phone Number : " + resultSet.getString(3));
                System.out.println("Is Member : " + resultSet.getBoolean(4));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("error while create connection : " + e.getMessage());
        }
    }

    private static void createCustomer(int id, String name, String phone_number, boolean is_member) {
        try (Connection connection = DBConnector.connect()) {
            String query = "INSERT INTO m_customer " +
                    "(id,customer_name, mobile_phone_no, is_member) " +
                    "values " +
                    "( ?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, phone_number);
            statement.setBoolean(4, is_member);
            System.out.println(query);
            statement.executeUpdate();
//            if (i == null) {
//                throw new RuntimeException("gagal create customer");
//            }
            System.out.println("sukses create customer");
            statement.close();
        } catch (SQLException e) {
            System.out.println("error while create connection : " + e.getMessage());
        }
    }

    private static void updateCustomer(int id, String name, String phone_number, boolean is_member) {
        try (Connection connection = DBConnector.connect()) {
            String query = "UPDATE m_customer " +
                    "set customer_name= ?," +
                    "mobile_phone_no= ?," +
                    "is_member = ?  WHERE id= ? ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, phone_number);
            statement.setBoolean(3, is_member);
            statement.setInt(4, id);
            statement.executeUpdate();
            System.out.println("sukses update customer");
            statement.close();
        } catch (SQLException e) {
            System.out.println("error while create connection : " + e.getMessage());
        }
    }

    private static void deleteCustomer(int id) {
        try (Connection connection = DBConnector.connect()) {
            try (Statement statement = connection.createStatement()) {
                String query = "DELETE from m_customer " +
                        " WHERE id=" + id;

                System.out.println(query);
                int i = statement.executeUpdate(query);
                System.out.println(i);
                if (i != 1) {
                    throw new RuntimeException("gagal delete customer");
                }
                System.out.println("sukses delete");
                statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("error while create connection : " + e.getMessage());
        }
    }

    private static void createWithBatch() {
        try (Connection connection = DBConnector.connect()) {
            Statement statement = connection.createStatement();
            int id = 11;
            for (int i = 0; i < 10; i++) {
                String name = "saleh " + id;
                String mobilPhone = "0984938" + id;
                boolean is_member = true;
                String query = "INSERT INTO m_customer " +
                        "values " +
                        "(" + id + ",'" + name + "','" + mobilPhone + "'," + is_member + ")";
                statement.addBatch(query);
                id++;
            }
            statement.executeBatch();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

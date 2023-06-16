package simple.P4.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpDbConnect {
    public static void signUpDbConnect(String username, String hashedPassword, byte[] salt) {
        Connection connection = null;
        PreparedStatement psInsert = null;

        try {
            // Get database connection
            dataBaseConnection connectNow = new dataBaseConnection();
            connection = connectNow.getConnection();

            // Prepare the INSERT statement
            String insertQuery = "INSERT INTO useraccounts (username, password, salt) VALUES (?, ?, ?)";
            psInsert = connection.prepareStatement(insertQuery);
            psInsert.setString(1, username);
            psInsert.setString(2, hashedPassword);
            psInsert.setBytes(3, salt);

            // Execute the INSERT statement
            psInsert.executeUpdate();

            System.out.println("Registration successful!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (psInsert != null) {
                    psInsert.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
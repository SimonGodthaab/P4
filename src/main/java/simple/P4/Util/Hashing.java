package simple.P4.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Hashing {
    private static final int SALT_LENGTH = 16;

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        try {
            // Create a MessageDigest instance with SHA-256 algorithm
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            // Apply salt to the password
            messageDigest.update(salt);

            // Convert the password string to bytes and hash it
            byte[] hashedPassword = messageDigest.digest(password.getBytes());

            // Concatenate the salt and hashed password
            byte[] saltedHash = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, saltedHash, 0, salt.length);
            System.arraycopy(hashedPassword, 0, saltedHash, salt.length, hashedPassword.length);

            // Convert the salted hash to a hexadecimal string representation
            StringBuilder hexBuilder = new StringBuilder();
            for (byte b : saltedHash) {
                hexBuilder.append(String.format("%02x", b));
            }
            return hexBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String password = "myPassword123";

        // Generate a salt
        byte[] salt = generateSalt();

        // Hash the password with the salt
        String hashedPassword = hashPassword(password, salt);

        System.out.println("Hashed password: " + hashedPassword);
        System.out.println("Salt: " + bytesToHex(salt));

        // Store the hashed password and salt in the database
        storePasswordAndSalt("username", hashedPassword, salt);
    }

    private static void storePasswordAndSalt(String username, String hashedPassword, byte[] salt) {
        try {
            // Establish the database connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");

            // Prepare the SQL statement to insert the username, hashed password, and salt into the database
            String sql = "INSERT INTO useraccounts (username, password, salt) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.setBytes(3, salt);

            // Execute the statement
            statement.executeUpdate();

            // Close the database connection and resources
            statement.close();
            conn.close();
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexBuilder.append(String.format("%02x", b));
        }
        return hexBuilder.toString();
    }
}
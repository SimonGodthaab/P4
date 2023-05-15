package simple.P4.Util;

import javafx.scene.control.Alert;

import java.sql.*;

public class SignUpDbConnect {
    public static void signUpDbConnect(String username, String encryptedPassword) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUser = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "root", "root");
            psCheckUser = connection.prepareStatement("SELECT * FROM useraccounts WHERE username = ?");
            psCheckUser.setString(1, username);
            resultSet = psCheckUser.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username invalid!");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO useraccounts (username, password) VALUES (?,?);");
                psInsert.setString(1, username);
                psInsert.setString(2, encryptedPassword);
                psInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
            if (psCheckUser != null) {
                try {
                    psCheckUser.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }


        }
    }
}

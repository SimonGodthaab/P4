package simple.P4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import simple.P4.Util.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecureLoginSiteController {

    @FXML
    private Label messageLabel;
    @FXML
    private TextField usernameBox;
    @FXML
    private PasswordField passwordBox;
    @FXML
    private TextField otpBox;
    private Stage stage;
    private Scene scene;
    private Parent searchSite;
    private String otpToken;
    private String username;
    private Parent signup;
    private int i = 0;

    private boolean emailValidate() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher matcher = pattern.matcher(usernameBox.getText());
        if (matcher.find() && matcher.group().equals(usernameBox.getText())) {
            return true;
        } else
            return false;

    }

    public void loginButtonOnAction(ActionEvent e) throws IOException {
        if (emailValidate() == true) {
            if (usernameBox.getText().isBlank() == false && passwordBox.getText().isBlank() == false) {
                if (validateLogin()) {
                    otpToken = OtpGen.otpCode();

                    MailHandler.send(otpToken, username);
                }
            }
        } else {
            messageLabel.setText("Log In Failed!");
        }
    }

    private String decrypt(String encryptedData, String key) throws Exception {
        return Encryption.decryption(encryptedData, key);
    }

    private String hashPassword(String password, byte[] salt) {
        return Hashing.hashPassword(password, salt);
    }

    private byte[] generateSalt() {
        return Hashing.generateSalt();
    }

    public boolean validateLogin() {
        PreparedStatement stmt = null;
        dataBaseConnection connectNow = new dataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT password, salt FROM useraccounts WHERE Binary username = ?";
        username = usernameBox.getText();

        try {
            stmt = connectDB.prepareStatement(verifyLogin);
            stmt.setString(1, usernameBox.getText());
            ResultSet databaseResult = stmt.executeQuery();

            if (databaseResult.next()) {
                String storedPassword = databaseResult.getString("password");
                byte[] salt = databaseResult.getBytes("salt");
                String enteredPassword = passwordBox.getText();

                // Hash the entered password with the retrieved salt
                String hashedEnteredPassword = hashPassword(enteredPassword, salt);
                // Compare the stored and hashed passwords
                if (storedPassword.equals(hashedEnteredPassword)) {
                    messageLabel.setText("OTP token sent to registered Email :-)");
                    return true;
                } else {
                    messageLabel.setText("Invalid Account information");
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connectDB != null) {
                    connectDB.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void authorizeButtonOnAction(ActionEvent e) throws IOException {
        if (otpToken == null) {
            messageLabel.setText("Please log in");
        } else if (otpToken.compareTo(otpBox.getText()) == 0) {
            System.out.println("OTP Token: " + otpToken);
            searchSite = FXMLLoader.load(getClass().getResource("searchBar.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(searchSite);
            stage.setScene(scene);
            stage.show();
        } else {
            i++;
            messageLabel.setText("Invalid Token number of tries:" + i);
            System.out.println(i);

            if (i == 3) {
                otpToken = null;
                i = 0;
                messageLabel.setText("Number of retries exceeded. Please login again");
            }
        }
    }

    public void signUpSiteAction(ActionEvent e) throws IOException {
        signup = FXMLLoader.load(getClass().getResource("signup.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(signup);
        stage.setScene(scene);
        stage.show();
    }
}

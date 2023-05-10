package simple.test12345;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import simple.test12345.Util.MailHandler;
import simple.test12345.Util.OtpGen;

import java.io.IOException;
import java.sql.*;
public class SecureLoginSiteController  {
    @FXML
    private Button cancelButton;
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
    public void loginButtonOnAction(ActionEvent e) throws IOException {

        if (usernameBox.getText().isBlank() == false && passwordBox.getText().isBlank() == false) {
            if (validateLogin()) {
                otpToken = OtpGen.otpCode();

                MailHandler.send(otpToken, username);
            }

        } else {
            messageLabel.setText("Fill out the login information!");
        }
    }
    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public boolean validateLogin() {
        PreparedStatement stmt = null;
        dataBaseConnection connectNow = new dataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "Select count(1) From useraccounts Where Binary username = ? AND Binary password = ? ";
        username = usernameBox.getText();

        try {
            stmt = connectDB.prepareStatement(verifyLogin);
            stmt.setString(1, usernameBox.getText());
            stmt.setString(2, passwordBox.getText());
            ResultSet databaseResult = stmt.executeQuery();

            while (databaseResult.next())
                if (databaseResult.getInt(1) == 1) {
                    messageLabel.setText("OTP token sent :-)");
                    return true;
                } else {
                    messageLabel.setText("*Beep!* Wrong Username or Password!");
                    return false;
                }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;

    }

    public void authorizeButtonOnAction(ActionEvent e) throws IOException {

        if (otpToken == null){
            messageLabel.setText("Please log in");
        }
        else if (otpToken.compareTo(otpBox.getText()) == 0) {
                System.out.println("OTP Token: "+otpToken);
                searchSite = FXMLLoader.load(getClass().getResource("searchBar.fxml"));
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(searchSite);
                stage.setScene(scene);
                stage.show();
        }
        else
            messageLabel.setText("Invalid Token");

    }
}



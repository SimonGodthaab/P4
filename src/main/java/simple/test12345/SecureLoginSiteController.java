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
    private Parent signup;
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
    public void signUpSiteAction (ActionEvent e) throws IOException{
        signup = FXMLLoader.load(getClass().getResource("signup.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(signup);
        stage.setScene(scene);
        stage.show();
    }
    public static void signUpUser(ActionEvent event, String username, String password) {
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
                psInsert.setString(2, password);
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



package simple.P4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import simple.P4.Util.Encryption;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Signupcontroller {
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private Label registerLabel;
    private Stage stage;
    private Scene scene;
    private Parent login;

    private boolean emailValidate(){
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher matcher = pattern.matcher(tf_username.getText());
        if (matcher.find() && matcher.group().equals(tf_username.getText())){
            return true;
        }
        else
            return false;

    }
    private String encrypt(String data, String key) throws Exception {
        return Encryption.encryption(data, key);
    }


    public void loginAction (ActionEvent e) throws IOException {
        login = FXMLLoader.load(getClass().getResource("secureLoginSite.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(login);
        stage.setScene(scene);
        stage.show();
    }
    public void registerAction(ActionEvent event) {
        if (emailValidate() == true) {
            if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()) {
                try {
                    String encryptedPassword = encrypt(tf_password.getText(), "841rd57qrstvrs76");
                    SecureLoginSiteController.signUpUser(event, tf_username.getText(), encryptedPassword, "841rd57qrstvrs76");

                } catch (Exception e) {
                    System.out.println("Encryption failed: " + e.getMessage());
                }
                registerLabel.setText("Registration sucessful!");
            } else {
                System.out.println("Please fill in all information!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Make sure you have filled in all contents!");
                alert.show();
            }
        } else {
            System.out.println("Registration failed!");
        }
    }

}






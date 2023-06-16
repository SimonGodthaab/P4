package simple.P4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import simple.P4.Util.Encryption;
import simple.P4.Util.Hashing;
import simple.P4.Util.SignUpDbConnect;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signupcontroller {
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    public Label registerLabel;
    private Stage stage;
    private Scene scene;
    private Parent login;

    private boolean emailValidate() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher matcher = pattern.matcher(tf_username.getText());
        if (matcher.find() && matcher.group().equals(tf_username.getText())) {
            return true;
        } else
            return false;

    }

    private String encrypt(String data, String key) throws Exception {
        return Encryption.encryption(data, key);
    }

    private String hashPassword(String password, byte[] salt) {
        return Hashing.hashPassword(password, salt);
    }

    public void loginAction(ActionEvent e) throws IOException {
        login = FXMLLoader.load(getClass().getResource("secureLoginSite.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(login);
        stage.setScene(scene);
        stage.show();
    }

    public void registerAction(ActionEvent event) {
        if (emailValidate()) {
            if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()) {
                try {
                    byte[] salt = Hashing.generateSalt();
                    String hashedPassword = hashPassword(tf_password.getText(), salt);
                    signUpUser(event, tf_username.getText(), hashedPassword, salt);
                } catch (Exception e) {
                    System.out.println("Encryption failed: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Registration failed!");
            registerLabel.setText("Registration failed! Remember to use email as username");
        }
    }

    public void signUpUser(ActionEvent event, String username, String hashedPassword, byte[] salt) {
        SignUpDbConnect.signUpDbConnect(username, hashedPassword, salt);
        registerLabel.setText("Registration successful!");
    }
}
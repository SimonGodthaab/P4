package simple.test12345;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;



public class Signupcontroller {
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    private Stage stage;
    private Scene scene;
    private Parent helloView;

    private boolean emailValidate(){
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(tf_username.getText());
        if (m.find() && m.group().equals(tf_username.getText())){
            return true;
        }
        else
            return false;

    }
    private String encrypt(String data, String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public void loginAction (ActionEvent e) throws IOException {
        helloView = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(helloView);
        stage.setScene(scene);
        stage.show();
    }
    public void registerAction(ActionEvent event) {
        if (emailValidate()) {
            if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()) {
                try {
                    String encryptedPassword = encrypt(tf_password.getText(), "841rd57qrstvrs76");
                    SecureLoginSiteController.signUpUser(event, tf_username.getText(), encryptedPassword, "841rd57qrstvrs76");

                } catch (Exception e) {
                    System.out.println("Encryption failed: " + e.getMessage());
                }
            } else {
                System.out.println("Please fill in all information!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Make sure you have filled in all contents!");
                alert.show();
            }
        } else {
            System.out.println("no work :P noob");
        }
    }

}






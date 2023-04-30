package simple.test12345;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }


  /*  String carBrand, carModel;
    Double Price;

    public HelloApplication(String carBrand, String carModel, Double price) {
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.Price = price;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public Double getPrice() {
        return Price;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setPrice(Double price) {
        Price = price;
    }*/
}
package simple.P4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import simple.P4.Util.dataBaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Searchcontroller implements Initializable {

    @FXML
    private TextField searchField;
    private Stage stage;
    private Scene scene;
    private Parent searchSite;
    @FXML
    private TableView<SearchApplication> tabel;
    @FXML
    private TableColumn<SearchApplication, String> carBrand;
    @FXML
    private TableColumn<SearchApplication, String> carModel;
    @FXML
    private TableColumn<SearchApplication, Double> carPrice;
    // Makes an Observable list
    ObservableList<SearchApplication> SearchApplicationObservableList = FXCollections.observableArrayList();

    // makes an connection with database and uses a SQL statement to take idCars, cb, cm and Price which uses foreign keys.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dataBaseConnection connectNow = new dataBaseConnection();
        Connection connectDB = connectNow.getConnection();
        String searchQuery = "select c.idCars, cb.Name, cm.Name, c.Price\n" +
                "from cars c, brands cb, models cm\n" +
                "where c.carBrand_Id = cb.Id \n" +
                "and c.carModel_Id = cm.Id";
        try {
            Statement dbStatement = connectDB.createStatement();
            ResultSet queryOutput = dbStatement.executeQuery(searchQuery);

            while (queryOutput.next()) {
                String carBrand = queryOutput.getString("cb.Name");
                String carModel = queryOutput.getString("cm.Name");
                Double carPrice = queryOutput.getDouble("c.Price");
                SearchApplicationObservableList.add(new SearchApplication(carBrand, carModel, carPrice));
            }
            carBrand.setCellValueFactory(new PropertyValueFactory<>("carBrand"));
            carModel.setCellValueFactory(new PropertyValueFactory<>("carModel"));
            carPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

            tabel.setItems(SearchApplicationObservableList);

            FilteredList<SearchApplication> filteredData = new FilteredList<>(SearchApplicationObservableList, b -> true);

            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(SearchApplication -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();

                    if (SearchApplication.getCarBrand().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (SearchApplication.getCarModel().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    }
                    return false;

                });
            });

            SortedList<SearchApplication> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tabel.comparatorProperty());

            tabel.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(SearchApplication.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();

        }

    }

    public void logoutButtonOnAction(ActionEvent e) throws IOException {
        searchSite = FXMLLoader.load(getClass().getResource("secureLoginSite.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(searchSite);
        stage.setScene(scene);
        stage.show();
    }
}

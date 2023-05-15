package simple.test12345;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class dataBaseConnection {
    //Makes connection to your database
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "car";
        String databaseUser = "root";
        String databasePassword = "Cheema90";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e){
            e.printStackTrace();
        }
        return databaseLink;
    }
}
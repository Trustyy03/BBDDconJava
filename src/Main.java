import com.sun.jdi.connect.Connector;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args)  {
        String url="jdbc:mysql://localhost:33060/demo";
        String username = "chris";
        String password = "secret";

        try{
            Driver driver = DriverManager.getDriver(url);

            Properties properties = new Properties();
            properties.setProperty("user", username);
            properties.setProperty("password", password);

            Connection con = driver.connect(url, properties);
            System.out.println("Conexión completada a través de Driver");
            con.close();
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error al conectar: " + ex.getMessage());
        }
    }
}
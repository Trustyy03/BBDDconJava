import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class Test {

    static java.sql.Connection con = DBConnection.getConnection("jdbc:mysql://79.117.64.230:33060/demo", "chris", "secret");

    public Test(){

    }

    public void createTable() throws SQLException {
        Statement st = con.createStatement();
        st.executeUpdate("CREATE TABLE T1 (c1 varchar(50))");
        st.close();
    }

    public static void main(String[] args) throws SQLException {
        Statement st = con.createStatement();
        st.executeUpdate("CREATE TABLE T1 (c1 varchar(50))");
        st.close();
    }

}

public class DBConnection {
    //Esto tiene que ser privado
    private static DBConnection instance;

    //Esto tiene que ser estático
    private static java.sql.Connection connection;
    //Constructor privado
    private DBConnection(String url, String user, String password){
        setConnection(url, user, password);
    }

    // Esto tiene que ser público y estático
    public static java.sql.Connection getConnection(String url, String user, String password){
        if (instance == null){
            instance = new DBConnection(url, user, password);
        }else {
            try {
                if (connection == null || connection.isClosed()) {
                    setConnection(url, user, password);
                }
            } catch (java.sql.SQLException e) {
                System.out.println("Connecting error: isClosed() " + e.getMessage());
            }
        }
        return connection;
    }
    private static void setConnection(String url, String user, String password){
        try {
            connection = java.sql.DriverManager.getConnection(url, user, password);
            System.out.println("Conexión realizada");
        }catch(java.sql.SQLException e){
            System.out.println("Connecting error: getConnection() " + e.getMessage());
        }
    }
}

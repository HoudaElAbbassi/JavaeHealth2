package Connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * this class enable to connect our java application with the database using JDBC.
 *
 * @author Ahmed,Houda,Amine,Parabal,Daniel
 */
public class DBConnection {

    /**
     * By Using this methode, we Register the JDBC driver name and database URL, and then we create connection with
     * our Database credentials
     *
     * @return con which used to establish connection with the database.
     *  @throws SQLException if no connection could established
     */
    public static Connection getConnection() throws SQLException {

        //  Database credentials
        String url = "jdbc:mysql://localhost:3306/ehealth";
        String user = "root";
        String pass = "";

        // JDBC driver name and database URL
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, pass);
            return con;

        } catch (SQLException e) {
            System.out.println("KEINE Verbindung");
            e.printStackTrace();


        }


        return con;
    }

}






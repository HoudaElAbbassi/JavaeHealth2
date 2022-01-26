package Connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * this class enable to connect our java application with the database using JDBC.
 *
 * @author
 */
public class DBConnection {

    /**
     * By Using this methode, we Register the JDBC driver name and database URL, and then we create connection with
     * our Database credentials
     *
     * @return con which used to establish connection with the database.
     */
    public static Connection getConnection() throws SQLException {

        //  Database credentials
        String url = "jdbc:mysql://localhost:3306/ehealth";
        String user = "root";
        String pass = "azerty@222";

        // JDBC driver name and database URL
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, pass);
            //System.out.println("Verbindung erfolgreich hergestellt");

            //Statement stm = con.createStatement();
            //ResultSet rs = stm.executeQuery("SELECT * FROM patient;");
            return con;

        } catch (SQLException e) {
            System.out.println("KEINE Verbindung");
            e.printStackTrace();


        }


        return con;
    }

    public static void main(String[] args) throws SQLException {
        DBConnection db=new DBConnection();
        db.getConnection();
    }
}






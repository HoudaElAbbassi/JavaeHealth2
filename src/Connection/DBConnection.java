package Connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {


    public static Connection getConnection() throws SQLException {


        String url = "jdbc:mysql://localhost:3306/ehealth3";
        String user = "root";
        String pass = "";


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






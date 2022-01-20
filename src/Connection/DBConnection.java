package Connection;


import java.sql.*;

public class DBConnection {


    public static Connection getConnection() {


        String url = "jdbc:mysql://localhost:3306/ehealth";
        String user = "root";
        String pass = "MySQL640182--";


        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Verbindung erfolgreich hergestellt");

            //Statement stm = con.createStatement();
            //ResultSet rs = stm.executeQuery("SELECT * FROM patient;");
            return con;

        } catch (SQLException e) {
            System.out.println("KEINE Verbindung");
            e.printStackTrace();


        }


        return con;
    }


    public static void main(String[] args) {
        DBConnection db=new DBConnection();
        db.getConnection();
    }
}






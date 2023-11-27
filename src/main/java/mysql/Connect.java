package mysql;

import geheim.Passwort;
import main.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    static String url = "jdbc:mysql://192.168.178.200:3306/" + Main.DATABASE;
    static String user = "discord";
    static String pass = Passwort.getDataPasswort();

    public static Connection getConnection(){
        try {
            Connection con = DriverManager.getConnection(url, user, pass);

            return con;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Connection getConnectionWithName(String db_name){
        String url = "jdbc:mysql://192.168.178.200:3306/" + db_name;

        try {
            Connection con = DriverManager.getConnection(url, user, pass);

            return con;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

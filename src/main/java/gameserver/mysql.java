package gameserver;

import mysql.Connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class mysql {
    public static ArrayList<Produkt> getProduktList(){
        ArrayList<Produkt> produktArrayList = new ArrayList<>();

        try {
            Connection con = Connect.getConnectionWithName("gameserver");
            String sql = "SELECT * FROM produkt";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                produktArrayList.add(new Produkt(rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getInt("points"), rs.getString("description"), Long.parseLong(rs.getString("tag_id")), rs.getDouble("price")));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produktArrayList;
    }
}

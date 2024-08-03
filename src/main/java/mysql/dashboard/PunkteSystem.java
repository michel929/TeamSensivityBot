package mysql.dashboard;

import mysql.Connect;
import org.joda.time.LocalDateTime;

import java.sql.*;

public class PunkteSystem {

    public static void uploadMinutes(LocalDateTime first, LocalDateTime second, String id, int diff){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO online (discord_id, firstDate, secondDate, minuten) VALUES ('"+ id + "', '"+ first +"' , '"+ second +"', '"+ diff +"')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void upload(String id, int diff, int type, String grund){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO points (discord_id, type, points, grund) VALUES ('"+ id + "', '"+ type +"' , '"+ diff +"', '"+ grund +"')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getMostPoints(){
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM users ORDER BY points DESC LIMIT 1";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                return rs.getString("discord_id");
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void uploadPoints(String id, int points){
        points = points + getPoints(id);

        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("UPDATE users SET points = '" + points + "' WHERE discord_id = '" + id + "'");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getPoints(String id){
        int i = 0;
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM users";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(id.equals(rs.getString("discord_id"))){
                    i = rs.getInt("points");
                }

            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i;
    }

}

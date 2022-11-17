package mysql.dashboard;

import mysql.Connect;
import org.joda.time.DateTime;

import java.sql.*;
import java.util.Date;

public class PunkteSystem {

    public static void uploadMinutes(int min, Date date, String id){
        uploadPoints(id, min);
        if(existMinutes(date, id)){

            min = min + getMin(date, id, "min");
            int gesamt = getMin(date, id, "gesamt") + min;

            try {
                Connection con = Connect.getConnection();

                PreparedStatement posted = con.prepareStatement("UPDATE online SET min = '" + min + "', gesamt = '" + gesamt + "' WHERE discord_id = '" + id + "'");

                posted.executeUpdate();
                con.close();

            } catch (
                    SQLException e) {
                e.printStackTrace();
            }
        }else {
            int gesamt = getGesamt(id);

            try {
                Connection con = Connect.getConnection();

                PreparedStatement posted = con.prepareStatement("INSERT INTO online (discord_id, date, day, min, gesamt) VALUES ('"+ id + "', '"+ date +"', '"+ date.getDay() +"', '"+ min +"', '"+ gesamt +"')");

                posted.executeUpdate();
                con.close();

            } catch (
                    SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getMin(Date date, String id, String row){
        int min = 0;
        String d = date.getDay() + "-" + date.getMonth() + "-" + date.getYear();
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM online ORDER BY STR_TO_DATE(date,'%d-%m-%Y') ASC";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(id.equals(rs.getString("discord_id"))){
                    if(d.equals(rs.getString(row))){
                        min = rs.getInt(row);
                    }
                }
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return min;
    }

    public static int getGesamt(String id){
        int min = 0;
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM online";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(id.equals(rs.getString("discord_id"))){
                    min = rs.getInt("gesamt");
                }

            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return min;
    }

    public static boolean existMinutes(Date date, String id){
        boolean exist = false;

        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM online";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(id.equals(rs.getString("discord_id"))){
                    if(date.equals(rs.getDate("date"))){
                        exist = true;
                    }
                }

            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;
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

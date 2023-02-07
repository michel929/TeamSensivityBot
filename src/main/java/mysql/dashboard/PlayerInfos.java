package mysql.dashboard;

import mysql.Connect;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

public class PlayerInfos {
    public static void createAccount(String id, String username, String pb, String banner){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO users (discord_id, discord_username, discord_pb, discord_banner, website_url) VALUES ('"+ id + "', '"+ username +"' , '"+ pb +"', '"+ banner +"', '" + id + "')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUserToList(String id){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO user_rename (discord_id) VALUES ('"+ id + "')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeUserToList(String id){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("DELETE FROM user_rename WHERE discord_id = '" + id +"'");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeAccount(String id){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("DELETE FROM users WHERE discord_id = '" + id +"'");

            posted.executeUpdate();

            posted = con.prepareStatement("DELETE FROM user_role WHERE discord_id = '" + id +"'");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isExist(String id, String row, String table){
        boolean exist = false;

        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM " + table;
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(id.equals(rs.getString(row))){
                    exist = true;
                }

            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;
    }
    public static LocalDate getDaily(String id){
        LocalDate exist = null;

        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM users";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(id.equals(rs.getString("discord_id"))){
                    exist = rs.getDate("daily").toLocalDate();
                }

            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;
    }


    public static String getInfo(String id, String vergleich_row,String row, String table){
        String y = null;

        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM " + table;
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(id.equals(rs.getString(vergleich_row))){
                    y = rs.getString(row);
                }

            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return y;
    }

    public static void deleteInfo(String id, String row, String table){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("DELETE FROM '" + table + "' WHERE '" + row + "' = '" + id +"'");

            posted.executeUpdate();

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayerInfos(String discord_id, String row, String newInfos){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("UPDATE users SET " + row + " = '" + newInfos + "' WHERE discord_id = '" + discord_id + "'");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updatePlayerInfos(String discord_id, String row, Date newInfos){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("UPDATE users SET " + row + " = '" + newInfos + "' WHERE discord_id = '" + discord_id + "'");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertRole(String id, String role){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO user_role (discord_id, discord_role) VALUES ('"+ id + "', '"+ role +"')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

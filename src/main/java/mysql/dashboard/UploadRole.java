package mysql.dashboard;

import mysql.Connect;

import java.sql.*;

public class UploadRole {
    public static void insertRole(String id, String color, String name, int position){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO discord_role (role_id, color, role_name, role_position) VALUES ('"+ id + "', '"+ color +"', '"+ name +"', '"+ position +"')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateRole(String row, String value, String roleid){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("UPDATE discord_role SET " + row + " = '" + value + "' WHERE role_id = '" + roleid + "'");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateRole(String row, int value, String roleid){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("UPDATE discord_role SET " + row + " = '" + value + "' WHERE role_id = '" + roleid + "'");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropTable(String table) {
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("TRUNCATE TABLE " + table);

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

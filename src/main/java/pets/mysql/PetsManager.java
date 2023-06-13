package pets.mysql;

import mysql.Connect;
import pets.tiere.Animal;
import pets.tiere.Tier;

import java.sql.*;

public class PetsManager {
    public static void createPet(int id, String discord_id, String name){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO pets (id, discord_id, name) VALUES ('"+ id + "', '"+ discord_id +"', + '" + name + "')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isExist(String discord_id){
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM pets";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(rs.getString("discord_id").equals(discord_id)){
                    return true;
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static int getPet(String discord_id){
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM pets";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(rs.getString("discord_id").equals(discord_id)){
                    return rs.getInt("id");
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static Tier getAnimal(String discord_id){
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM pets";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(rs.getString("discord_id").equals(discord_id)){
                    return new Tier(rs.getString("name"), rs.getInt("id"), rs.getString("bday"), rs.getString("hunger"), rs.getString("durst"), rs.getInt("happy"), rs.getInt("level"));
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void update(String id, String row, String discord_id){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("UPDATE pets SET " + row + " = '" + id + "' WHERE discord_id = '" + discord_id + "'");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}

package mysql;

import main.Main;
import main.Start;
import net.dv8tion.jda.api.entities.Member;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Minecraft {
    public static List<Member> getPlayer(){
        List<Member> members = new ArrayList<>();

        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM hardcore";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(rs.getInt("dead") == 1){
                    members.add(Main.INSTANCE.getGuild().getMemberById(rs.getString("discord_id")));
                }
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }
    public static LocalDate getDate(String discord_id){
        LocalDate localDate = LocalDate.now();

        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM hardcore";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(rs.getString("discord_id").equals(discord_id)){
                    localDate = LocalDate.parse(rs.getString("datum"));
                }
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return localDate;
    }

    public static void updatePlayer(String uuid, String row, int neu){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("UPDATE hardcore SET " + row + " = '" + neu + "' WHERE discord_id = '" + uuid + "'");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayer(String uuid, String row, String neu){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("UPDATE hardcore SET " + row + " = '" + neu + "' WHERE discord_id = '" + uuid + "'");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createHardcore(String uuid, String discord_id){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO hardcore (discord_id, uuid) VALUES ('"+ discord_id + "', '"+ uuid +"')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

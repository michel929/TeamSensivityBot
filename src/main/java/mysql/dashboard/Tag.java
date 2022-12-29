package mysql.dashboard;

import mysql.Connect;
import net.dv8tion.jda.api.entities.channel.forums.ForumTag;

import java.sql.*;

public class Tag {
    public static void insertTag(ForumTag tag){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO news_tags (name, discord_id) VALUES ('"+ tag.getName() + "', '"+ tag.getId() +"')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateTag(ForumTag tag){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("UPDATE news_tags SET name = '" + tag.getName() + "' WHERE discord_id = '" + tag.getId() + "'");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeTag(ForumTag tag){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("DELETE FROM news_tags WHERE discord_id = '" + tag.getId() +"'");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isExist(ForumTag tag){
        boolean exist = false;

        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM news_tags";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(tag.getId().equals(rs.getString("discord_id"))){
                    exist = true;
                }

            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;
    }
}

package mysql.dashboard;

import messenger.object.Message;
import mysql.Connect;

import java.sql.*;
import java.util.ArrayList;

public class Messenger {
    public static ArrayList<Message> getMessages(){

        ArrayList<Message> messages = new ArrayList<>();

        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM messages";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                Message nachricht = null;

                if(rs.getString("link") != ""){
                    nachricht = new Message(rs.getString("discord_id"), rs.getInt("id"), rs.getString("titel"), rs.getString("message"), rs.getString("color"), rs.getString("icon"), rs.getString("link"), rs.getString("link_text"));
                }else {
                    nachricht = new Message(rs.getString("discord_id"), rs.getInt("id"), rs.getString("titel"), rs.getString("message"), rs.getString("color"), rs.getString("icon"));
                }

                messages.add(nachricht);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    public static void removeMessage(int id){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("DELETE FROM messages WHERE id = '" + id +"'");

            posted.executeUpdate();

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

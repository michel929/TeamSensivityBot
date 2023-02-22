package logging;

import mysql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogSystem {
    public static void logGeneral(String discord_id, String text, String discord_tag){

        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO log_system (discord_id, log_text, discord_tag) VALUES ('"+ discord_id + "', '"+ text +"', + '" + discord_tag + "')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

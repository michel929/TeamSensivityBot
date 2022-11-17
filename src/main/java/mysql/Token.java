package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class Token {
    public static String createToken(){
        String token = String.valueOf(UUID.randomUUID());
        return token;
    }

    public static String uploadTokenLink(String discord_id){
        String link = "https://sensivity.team/dashboard/discord-login.php?token=";

        String token = createToken();
        link = link + token;

        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("DELETE FROM token WHERE discord_id = '" + discord_id +"'");
            posted.executeUpdate();

            posted = con.prepareStatement("INSERT INTO token (discord_id, token) VALUES ('"+ discord_id + "', '"+ token +"')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return link;
    }

    public static String uploadToken(String discord_id){
        String token = createToken();

        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("DELETE FROM token WHERE discord_id = '" + discord_id +"'");
            posted.executeUpdate();

            posted = con.prepareStatement("INSERT INTO token (discord_id, token) VALUES ('"+ discord_id + "', '"+ token +"')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return token;
    }
}

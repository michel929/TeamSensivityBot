package mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetAllTokens {
    public static List<String> getUsers(){
        List<String> members = new ArrayList<>();

        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM users";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(rs.getString("discord_token") != null){
                    members.add(rs.getString("discord_id"));
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }
}

package mysql;

import java.sql.*;

public class KarmaSystem {
    public static void addMove(String moved, String user, int seconds){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO karmasystem (moved, seconds, user) VALUES ('"+ moved + "', '"+ seconds +"', '"+ user +"')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getSeconds(String user, String row){
        int end = 0;

        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM karmasystem";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(user.equals(rs.getString("user"))){
                  end = rs.getInt("seconds");
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return end;
    }

    public static void createEvent(String user, String event, String object, int karma){

    }
}

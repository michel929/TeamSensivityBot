package mysql;

import java.sql.*;

public class BotInfos {
    public static String getBotInfos(String row){
        String url = "";

        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM bot";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                url = rs.getString(row);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static void createRole(String id, String color, String name, int position){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO rollen (role_id, color, role_name, role_position) VALUES ('"+ id + "', '"+ color +"', + '" + name + "', '" + position + "')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateInfo(String row, String value){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("UPDATE bot SET " + row + " = '" + value + "'");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

}

package mysql;

import main.Start;
import net.dv8tion.jda.api.entities.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SWF {
    public static void createSWF(String uuid, String player1, String player2, String player3, String player4, String mesageid){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO swf (id, player_1, reserviert_1, reserviert_2, reserviert_3, messageid) VALUES ('"+ uuid + "', '"+ player1 +"', '"+ player2 +"', '"+ player3 +"', '"+ player4 +"', '"+ mesageid +"')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createSWF(String uuid, String player1, String player2, String player3, String mesageid){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO swf (id, player_1, reserviert_1, reserviert_2, messageid) VALUES ('"+ uuid + "', '"+ player1 +"', '"+ player2 +"', '"+ player3 +"', '"+ mesageid +"')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createSWF(String uuid, String player1, String player2, String mesageid){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO swf (id, player_1, reserviert_1, messageid) VALUES ('"+ uuid + "', '"+ player1 +"', '"+ player2 +"', '"+ mesageid +"')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createSWF(String uuid, String player1, String mesageid){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("INSERT INTO swf (id, player_1, messageid) VALUES ('"+ uuid + "', '"+ player1 +"', '" + mesageid + "')");

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getPlayer(String uuid) {
        List<String> reserviert = new ArrayList<>();
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM swf";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(uuid.equals(rs.getString("id"))){
                    reserviert.add(rs.getString("player_1"));
                    reserviert.add(rs.getString("player_2"));
                    reserviert.add(rs.getString("player_3"));
                    reserviert.add(rs.getString("player_4"));
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reserviert;
    }

    public static List<Member> getReserviert(String uuid) {
        List<Member> reserviert = new ArrayList<>();
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM swf";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(uuid.equals(rs.getString("id"))){
                    if(rs.getString("reserviert_1") != null) {
                        reserviert.add(Start.INSTANCE.getApi().getGuildById(Start.GUILD_ID).getMemberById(rs.getString("reserviert_1")));
                    }

                    if(rs.getString("reserviert_2") != null) {
                        reserviert.add(Start.INSTANCE.getApi().getGuildById(Start.GUILD_ID).getMemberById(rs.getString("reserviert_2")));
                    }

                    if(rs.getString("reserviert_3") != null) {
                        reserviert.add(Start.INSTANCE.getApi().getGuildById(Start.GUILD_ID).getMemberById(rs.getString("reserviert_3")));
                    }
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reserviert;
    }

    public static void insertPlayer(String uuid, String playerid, String platz){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("UPDATE swf SET " + platz + " = '" + playerid + "' WHERE id = '" + uuid + "'");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMessageID(String uuid, String meesageid){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("UPDATE swf SET messageid = '" + meesageid + "' WHERE id = '" + uuid + "'");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isReserved(String uuid, String playerid, String platz){
        boolean reserviert = false;
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM swf";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(uuid.equals(rs.getString("id"))){
                  if(playerid.equals(rs.getString(platz))){
                      reserviert = false;
                  }else if(rs.getString(platz) == null){
                      reserviert = false;
                  }else {
                      reserviert = true;
                  }
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reserviert;
    }

    public static boolean isFree(String uuid, String playerid) {

        boolean free = false;

        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM swf";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(uuid.equals(rs.getString("id"))){
                    if(rs.getString("player_1") == null && !isReserved(uuid, playerid, "reserviert_1")){
                        free = true;
                        insertPlayer(uuid, playerid, "player_1");
                    }else if(rs.getString("player_2") == null && !isReserved(uuid, playerid, "reserviert_2")){
                        free = true;
                        insertPlayer(uuid, playerid, "player_2");
                    }else if(rs.getString("player_3") == null && !isReserved(uuid, playerid, "reserviert_3")){
                        free = true;
                        insertPlayer(uuid, playerid, "player_3");
                    }else if(rs.getString("player_4") == null && !isReserved(uuid, playerid, "reserviert_4")){
                        free = true;
                        insertPlayer(uuid, playerid, "player_4");
                    }else {
                        free = false;
                    }
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return free;
    }

    public static String getPlayerRow(String uuid, String playerid){
        String ruck = "";
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM swf";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(uuid.equals(rs.getString("id"))){
                    if(playerid.equals(rs.getString("player_1"))){
                        ruck = "player_1";
                    }else if(playerid.equals(rs.getString("player_2"))){
                        ruck = "player_2";
                    }else if(playerid.equals(rs.getString("player_3"))){
                        ruck = "player_3";
                    }else {
                        ruck = "player_4";
                    }
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ruck;
    }

    public static String getMessageID(String uuid){
        String ruck = "";
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM swf";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(uuid.equals(rs.getString("id"))){
                    ruck = rs.getString("messageid");
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ruck;
    }

    public static void leaveSWF(String uuid, String playerid){
        String player = getPlayerRow(uuid, playerid);

        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("UPDATE swf SET "+ player +" = NULL WHERE id = '" + uuid + "'");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeSWF(String id){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("DELETE FROM swf WHERE id = '" + id +"'");

            posted.executeUpdate();

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

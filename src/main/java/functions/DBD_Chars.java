package functions;

import mysql.dashboard.PlayerInfos;

import java.net.MalformedURLException;
import java.net.URL;

public class DBD_Chars {
    public static String getDBDChar(String discord_id){
        String charID = PlayerInfos.getInfo(discord_id, "discord_id","survivor_main", "users");
        return PlayerInfos.getInfo(charID, "id", "pb", "dbd_chars");
    }

    public static String getDBDRank(String discord_id) {
        String SteamID = PlayerInfos.getInfo(discord_id, "discord_id", "steam_id", "users");
        String url = "https://sensivity.team/bot/getRang.php?steam_id=" + SteamID;
        int rang = 0;
        try {
            rang = GetInfos.stream(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        switch (rang){
            case 1:
                return "<:rang1:1018826074676789301>";
            case 2:
                return "<:rang2:1018826178863321148>";
            case 3:
                return "<:rang3:1018826270303326239>";
            case 4:
                return "<:rang4:1018826427661029406>";
            case 5:
                return "<:rang5:1018826429363933214>";
            case 6:
                return "<:rang6:1018826431301681193>";
            case 7:
                return "<:rang7:1018826432920698910>";
            case 8:
                return "<:rang8:1018826434527113236>";
            case 9:
                return "<:rang9:1018826436162879558>";
            case 10:
                return "<:rang10:1018826437840601138>";
            case 11:
                return "<:rang11:1018826439606415480>";
            case 12:
                return "<:rang12:1018826441409970256>";
            case 13:
                return "<:rang13:1018826443242885120>";
            case 14:
                return "<:rang14:1018826445059014746>";
            case 15:
                return "<:rang15:1018826446527016962>";
            case 16:
                return "<:rang16:1018826448666112000>";
            case 17:
                return "<:rang17:1018826450272534548>";
            case 18:
                return "<:rang18:1018826452059291658>";
            case 19:
                return "<:rang19:1018826454093533184>";
            case 20:
                return "<:rang20:1018826456098422824>";
            default:
                break;
        }

        return "<:rang20:1018826456098422824>";
    }
}

package request;

import functions.GetInfos;
import mysql.GetAllTokens;
import mysql.dashboard.PlayerInfos;
import riot.RiotAPI;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.TimerTask;

public class EveryDay extends TimerTask {
    @Override
    public void run() {

        for (String m: GetAllTokens.getUsers()) {
            String url = "https://dashboard.sensivity.team/connect/discord/refresh.php?id=" + m;
            try {
                GetInfos.streamBOT(new URL(url));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        HashMap<String, String> league = PlayerInfos.getLeaguePuuids();
        RiotAPI.getMatches(league);
    }
}
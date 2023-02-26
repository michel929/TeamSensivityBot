package request;

import functions.GetInfos;
import mysql.GetAllTokens;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimerTask;

public class EveryDay extends TimerTask {
    @Override
    public void run() {

        for (String m: GetAllTokens.getUsers()) {
            String url = "https://dashboard.sensivity.team/connect/discord/update-points.php?discord_id=" + m;
            try {
                GetInfos.streamBOT(new URL(url));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}

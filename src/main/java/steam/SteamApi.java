package steam;

import functions.GetInfos;

import java.net.MalformedURLException;
import java.net.URL;

public class SteamApi {
    public static boolean isInGroup(String SteamID){
        String url = "https://sensivity.team/bot/getGroup.php?steam_id=" + SteamID;
            int wert = 0;
        try {
            wert = GetInfos.stream(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if(wert == 0){
            return false;
        }else
            return true;
    }
}

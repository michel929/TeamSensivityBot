package riot;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class RiotAPI {
    public static String getSummonerID(String summonerName){
        Summoner summoner = Orianna.summonerNamed(summonerName).get();
        return summoner.getId();
    }

    public static String getPuuid(String summonerName){
        Summoner summoner = Orianna.summonerNamed(summonerName).get();
        return summoner.getPuuid();
    }
}

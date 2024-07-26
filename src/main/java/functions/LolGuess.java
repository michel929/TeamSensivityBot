package functions;

import mysql.BotInfos;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import request.Api;

import java.util.ArrayList;
import java.util.Random;

public class LolGuess {

    public static ArrayList<Integer> champs = new ArrayList<>();

    public static void updateLOLGuess(){
        updateChamps();

        int rnd = new Random().nextInt(champs.size());
        int id = champs.get(rnd);

        BotInfos.updateInfo("lol_guess_id", String.valueOf(id));
    }

    public static void updateChamps(){
        champs.clear();

        JSONArray list = Api.getRequest("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/de_de/v1/champion-summary.json");

        for(Object o : list){
            JSONObject obj = (JSONObject)o;

            champs.add(Integer.parseInt(obj.get("id").toString()));
        }
    }
}

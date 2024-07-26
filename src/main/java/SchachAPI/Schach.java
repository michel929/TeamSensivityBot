package SchachAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import request.Api;

import java.util.ArrayList;

public class Schach {
    public static ArrayList<String> getUser(){
        JSONObject object = Api.getRequestO("https://api.chess.com/pub/club/team-sensivity/members");

        ArrayList<String> usernames = new ArrayList<>();

        JSONArray weekly = (JSONArray) object.get("weekly");
        JSONArray monthly = (JSONArray) object.get("monthly");
        JSONArray all_times = (JSONArray) object.get("all_time");

        for (int i = 0; i < weekly.size(); i++) {
            JSONObject week = (JSONObject) weekly.get(i);

            usernames.add((String) week.get("username"));
        }

        for (int i = 0; i < monthly.size(); i++) {
            JSONObject month = (JSONObject) monthly.get(i);

            usernames.add((String) month.get("username"));
        }

        for (int i = 0; i < all_times.size(); i++) {
            JSONObject time = (JSONObject) all_times.get(i);

            usernames.add((String) time.get("username"));
        }

        //usernames.add("11nils11");

        return usernames;
    }

    public static long getUserPoints(String username){
        long points = 0;

        JSONObject object = Api.getRequestO("https://api.chess.com/pub/player/" + username + "/stats");

        try {
            JSONObject chess_rapid = (JSONObject) object.get("chess_rapid");
            JSONObject last = (JSONObject) chess_rapid.get("last");
            points = (long) last.get("rating");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return points;
    }
}

package riot;

import geheim.Riot;
import mysql.Connect;
import mysql.dashboard.PlayerInfos;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import request.Api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class RiotAPI {

    public static void getMatches(HashMap<String, String> league) {

        Thread thread = new Thread() {
            public void run() {
                league.forEach((k, v) -> {
                    String puuid = v;
                    String discord_id = k;
                    System.out.println("Daten für " + discord_id + " werden gerade abgerufen. (" + puuid + ")");
                    JSONArray matchList = Api.getRequest("https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=100&api_key=" + Riot.RiotKey);

                    for (int i = 0; i < matchList.size(); i++) {
                        if (PlayerInfos.isExist(matchList.get(i).toString(), "match_id", "league_games")) {
                            if (!PlayerInfos.isMatchFromUser(discord_id, matchList.get(i).toString())) {
                                loadToDatabase(matchList.get(i).toString(), puuid, discord_id);
                                try {
                                    sleep(300);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        } else {
                            loadToDatabase(matchList.get(i).toString(), puuid, discord_id);
                            try {
                                sleep(300);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        };
        thread.setDaemon(true);
        thread.start();
    }


    public static void loadToDatabase(String match, String puuid, String discord_id){
        int win = 0;
        int match_type = 0;
        String position = "";
        int[] item_1 = new int[10];
        int[] item_2 = new int[10];
        int[] item_3 = new int[10];
        int[] item_4 = new int[10];
        int[] item_5 = new int[10];
        int[] item_6 = new int[10];
        int[] item_7 = new int[10];
        int[] gold = new int[10];
        int[] damage_buildings = new int[10];
        int[] damage_champions = new int[10];
        int[] damage_objective = new int[10];
        int get_damage = 0;
        int wards = 0;
        int cs = 0;
        int cast_1 = 0;
        int cast_2 = 0;
        int vision_score = 0;
        int kills_team1 = 0;
        int kills_team2 = 0;
        int first_blood = 0;
        int heralds_team1 = 0;
        int heralds_team2 = 0;
        int dragons_team1 = 0;
        int dragons_team2 = 0;
        int baron_team1 = 0;
        int baron_team2 = 0;
        int inihibitoren_team1 = 0;
        int inihibitoren_team2 = 0;
        String[] member = new String[10];
        int[] champ = new int[10];
        int[] kills = new int[10];
        int double_kills = 0;
        int tribble_kills = 0;
        int quadra_kills = 0;
        int penta_kills = 0;
        int tower_team1 = 0;
        int tower_team2 = 0;
        int[] death = new int[10];
        int[] level = new int[10];
        int[] assist = new int[10];
        int teamid = 0;

        //Nicht in Datenbank
        JSONObject game = Api.getRequestO("https://europe.api.riotgames.com/lol/match/v5/matches/" + match + "?api_key=" + Riot.RiotKey);

        JSONObject info = (JSONObject) game.get("info");

        match_type = Integer.parseInt(info.get("queueId").toString());

        JSONArray participants = (JSONArray) info.get("participants");

        ArrayList<JSONObject> player = new ArrayList<>();

        for (int f = 0; f < participants.size(); f++){
            player.add((JSONObject) participants.get(f));
        }

        int x = 1;
        for (JSONObject o : player) {
            if (o.get("puuid").equals(puuid)) {
                teamid = Integer.parseInt(o.get("teamId").toString());
                if ("true".equals(o.get("win").toString())) {
                    win = 1;
                } else {
                    win = 0;
                }
                if ("true".equals(o.get("firstBloodKill").toString())) {
                    first_blood = 1;
                }

                assist[0] = Integer.parseInt(o.get("assists").toString());
                level[0] = Integer.parseInt(o.get("champLevel").toString());
                champ[0] = Integer.parseInt(o.get("championId").toString());
                damage_buildings[0] = Integer.parseInt(o.get("damageDealtToBuildings").toString());
                damage_objective[0] = Integer.parseInt(o.get("damageDealtToObjectives").toString());
                damage_champions[0] = Integer.parseInt(o.get("totalDamageDealtToChampions").toString());
                death[0] = Integer.parseInt(o.get("deaths").toString());
                double_kills = Integer.parseInt(o.get("doubleKills").toString());
                gold[0] = Integer.parseInt(o.get("goldEarned").toString());
                position = o.get("individualPosition").toString();
                item_1[0] = Integer.parseInt(o.get("item0").toString());
                item_2[0] = Integer.parseInt(o.get("item1").toString());
                item_3[0] = Integer.parseInt(o.get("item2").toString());
                item_4[0] = Integer.parseInt(o.get("item3").toString());
                item_5[0] = Integer.parseInt(o.get("item4").toString());
                item_6[0] = Integer.parseInt(o.get("item5").toString());
                item_7[0] = Integer.parseInt(o.get("item6").toString());
                kills[0] = Integer.parseInt(o.get("kills").toString());
                quadra_kills = Integer.parseInt(o.get("quadraKills").toString());
                penta_kills = Integer.parseInt(o.get("pentaKills").toString());
                get_damage = Integer.parseInt(o.get("totalDamageTaken").toString());
                cs = Integer.parseInt(o.get("totalMinionsKilled").toString());
                wards = Integer.parseInt(o.get("wardsPlaced").toString());
                vision_score = Integer.parseInt(o.get("visionScore").toString());
                cast_1 = Integer.parseInt(o.get("summoner1Casts").toString());
                cast_2 = Integer.parseInt(o.get("summoner2Casts").toString());
                member[0] = o.get("summonerName").toString();
            } else {
                if ("true".equals(o.get("firstBloodKill").toString())) {
                    first_blood = x;
                    first_blood++;
                }

                assist[x] = Integer.parseInt(o.get("assists").toString());
                level[x] = Integer.parseInt(o.get("champLevel").toString());
                champ[x] = Integer.parseInt(o.get("championId").toString());
                death[x] = Integer.parseInt(o.get("deaths").toString());
                kills[x] = Integer.parseInt(o.get("kills").toString());
                member[x] = o.get("summonerName").toString();
                item_1[x] = Integer.parseInt(o.get("item0").toString());
                item_2[x] = Integer.parseInt(o.get("item1").toString());
                item_3[x] = Integer.parseInt(o.get("item2").toString());
                item_4[x] = Integer.parseInt(o.get("item3").toString());
                item_5[x] = Integer.parseInt(o.get("item4").toString());
                item_6[x] = Integer.parseInt(o.get("item5").toString());
                item_7[x] = Integer.parseInt(o.get("item6").toString());
                gold[x] = Integer.parseInt(o.get("goldEarned").toString());
                damage_buildings[x] = Integer.parseInt(o.get("damageDealtToBuildings").toString());
                damage_objective[x] = Integer.parseInt(o.get("damageDealtToObjectives").toString());
                damage_champions[x] = Integer.parseInt(o.get("totalDamageDealtToChampions").toString());

                x++;
            }
        }

        JSONArray teams = (JSONArray) info.get("teams");

        ArrayList<JSONObject> team = new ArrayList<>();
        team.add((JSONObject) teams.get(0));
        team.add((JSONObject) teams.get(1));

        for (JSONObject t : team) {
            JSONObject objectives = (JSONObject) t.get("objectives");
            JSONObject baron = (JSONObject) objectives.get("baron");
            JSONObject herold = (JSONObject) objectives.get("riftHerald");
            JSONObject champion = (JSONObject) objectives.get("champion");
            JSONObject dragon = (JSONObject) objectives.get("dragon");
            JSONObject inhibitor = (JSONObject) objectives.get("inhibitor");
            JSONObject tower = (JSONObject) objectives.get("tower");

            if (teamid == Integer.parseInt(t.get("teamId").toString())) {
                heralds_team1 = Integer.parseInt(herold.get("kills").toString());
                baron_team1 = Integer.parseInt(baron.get("kills").toString());
                dragons_team1 = Integer.parseInt(dragon.get("kills").toString());
                kills_team1 = Integer.parseInt(champion.get("kills").toString());
                inihibitoren_team1 = Integer.parseInt(inhibitor.get("kills").toString());
                tower_team1 = Integer.parseInt(tower.get("kills").toString());
            } else {
                heralds_team2 = Integer.parseInt(herold.get("kills").toString());
                baron_team2 = Integer.parseInt(baron.get("kills").toString());
                dragons_team2 = Integer.parseInt(dragon.get("kills").toString());
                kills_team2 = Integer.parseInt(champion.get("kills").toString());
                inihibitoren_team2 = Integer.parseInt(inhibitor.get("kills").toString());
                tower_team2 = Integer.parseInt(tower.get("kills").toString());
            }
        }
        String sql = "INSERT INTO league_games (`discord_id`, `match_id`, `match_type`, `win`, `position`, `get_damage`, `wards`, `cs`, `cast_1`, `cast_2`, `vision_score`, `kills_team1`, " +
                "`kills_team2`, `first_blood`, `heralds_team1`, `heralds_team2`, `dragons_team1`, `dragons_team2`, `tower_team1`, " +
                "`tower_team2`, `baron_team1`, `baron_team2`, `inihibitoren_team1`, `inihibitoren_team2`, `member_1`, `member_2`, `member_3`, " +
                "`member_4`, `member_5`, `member_6`, `member_7`, `member_8`, `member_9`, `member_10`, `champ_1`, `champ_2`, `champ_3`, `champ_4`, " +
                "`champ_5`, `champ_6`, `champ_7`, `champ_8`, `champ_9`, `champ_10`, `kills_1`, `double_kills`, `tribble_kills`, `quadra_kills`, " +
                "`penta_kills`, `kills_2`, `kills_3`, `kills_4`, `kills_5`, `kills_6`, `kills_7`, `kills_8`, `kills_9`, `kills_10`, `death_1`, `death_2`, " +
                "`death_3`, `death_4`, `death_5`, `death_6`, `death_7`, `death_8`, `death_9`, `death_10`, `assists_1`, `assist_2`, `assist_3`, `assist_4`, `assist_5`, " +
                "`assist_6`, `assist_7`, `assist_8`, `assist_9`, `assist_10`, `level_1`, `level_2`, `level_3`, `level_4`, `level_5`, `level_6`, `level_7`, `level_8`, " +
                "`level_9`, `level_10`, `item1_champ1`, `item2_champ1`, `item3_champ1`, `item4_champ1`, `item5_champ1`, `item6_champ1`, `item7_champ1`, `item1_champ2`, " +
                "`item2_champ2`, `item3_champ2`, `item4_champ2`, `item5_champ2`, `item6_champ2`, `item7_champ2`, `item1_champ3`, `item2_champ3`, " +
                "`item3_champ3`, `item4_champ3`, `item5_champ3`, `item6_champ3`, `item7_champ3`, `item1_champ4`, `item2_champ4`, `item3_champ4`, `item4_champ4`, " +
                "`item5_champ4`, `item6_champ4`, `item7_champ4`, `item1_champ5`, `item2_champ5`, `item3_champ5`, `item4_champ5`, `item5_champ5`, `item6_champ5`, " +
                "`item7_champ5`, `item1_champ6`, `item2_champ6`, `item3_champ6`, `item4_champ6`, `item5_champ6`, `item6_champ6`, `item7_champ6`, `item1_champ7`, " +
                "`item2_champ7`, `item3_champ7`, `item4_champ7`, `item5_champ7`, `item6_champ7`, `item7_champ7`, `item1_champ8`, `item2_champ8`, `item3_champ8`, " +
                "`item4_champ8`, `item5_champ8`, `item6_champ8`, `item7_champ8`, `item1_champ9`, `item2_champ9`, `item3_champ9`, `item4_champ9`, `item5_champ9`, " +
                "`item6_champ9`, `item7_champ9`, `item1_champ10`, `item2_champ10`, `item3_champ10`, `item4_champ10`, `item5_champ10`, `item6_champ10`, `item7_champ10`, " +
                "`gold_champ1`, `gold_champ2`, `gold_champ3`, `gold_champ4`, `gold_champ5`, `gold_champ6`, `gold_champ7`, `gold_champ8`, `gold_champ9`, `gold_champ10`, " +
                "`damage_objective_1`, `damage_objective_2`, `damage_objective_3`, `damage_objective_4`, `damage_objective_5`, `damage_objective_6`, `damage_objective_7`," +
                " `damage_objective_8`, `damage_objective_9`, `damage_objective_10`, `damage_champions_1`, `damage_champions_2`, `damage_champions_3`, `damage_champions_4`, " +
                "`damage_champions_5`, `damage_champions_6`, `damage_champions_7`,`damage_champions_8`, `damage_champions_9`, `damage_champions_10`, `damage_buildings_1`, `damage_buildings_2`" +
                ", `damage_buildings_3`, `damage_buildings_4`, `damage_buildings_5`, `damage_buildings_6`, `damage_buildings_7`, `damage_buildings_8`, `damage_buildings_9`, `damage_buildings_10`) VALUES ('" + discord_id + "','" + match + "','" + match_type + "','" + win + "','" + position + "','" + get_damage + "','" + wards + "','" + cs + "','" + cast_1 + "','" + cast_2 + "','" + vision_score + "','" + kills_team1 + "','" + kills_team2 + "','" + first_blood + "','" + heralds_team1 + "'," +
                "'" + heralds_team2 + "','" + dragons_team1 + "','" + dragons_team2 + "','" + tower_team1 + "','" + tower_team2 + "','" + baron_team1 + "','" + baron_team2 + "','" + inihibitoren_team1 + "','" + inihibitoren_team2 + "','" + member[0] + "','" + member[1] + "','" + member[2] + "'," +
                "'" + member[3] + "','" + member[4] + "','" + member[5] + "','" + member[6] + "','" + member[7] + "','" + member[8] + "','" + member[9] + "','" + champ[0] + "','" + champ[1] + "','" + champ[2] + "','" + champ[3] + "','" + champ[4] + "'," +
                "'" + champ[5] + "','" + champ[6] + "','" + champ[7] + "','" + champ[8] + "','" + champ[9] + "','" + kills[0] + "','" + double_kills + "','" + tribble_kills + "','" + quadra_kills + "','" + penta_kills + "','" + kills[1] + "','" + kills[2] + "'," +
                "'" + kills[3] + "','" + kills[4] + "','" + kills[5] + "','" + kills[6] + "','" + kills[7] + "','" + kills[8] + "','" + kills[9] + "','" + death[0] + "','" + death[1] + "','" + death[2] + "','" + death[3] + "','" + death[4] + "'," +
                "'" + death[5] + "','" + death[6] + "','" + death[7] + "','" + death[8] + "','" + death[9] + "','" + assist[0] + "','" + assist[1] + "','" + assist[2] + "','" + assist[3] + "','" + assist[4] + "','" + assist[5] + "','" + assist[6] + "'," +
                "'" + assist[7] + "','" + assist[8] + "','" + assist[9] + "','" + level[0] + "','" + level[1] + "','" + level[2] + "','" + level[3] + "','" + level[4] + "','" + level[5] + "','" + level[6] + "','" + level[7] + "','" + level[8] + "'," +
                "'" + level[9] + "','" + item_1[0] + "','" + item_2[0] + "','" + item_3[0] + "','" + item_4[0] + "','" + item_5[0] + "','" + item_6[0] + "','" + item_7[0] + "','" + item_1[1] + "','" + item_2[1] + "','" + item_3[1] + "','" + item_4[1] + "'," +
                "'" + item_5[1] + "','" + item_6[1] + "','" + item_7[1] + "','" + item_1[2] + "','" + item_2[2] + "','" + item_3[2] + "','" + item_4[2] + "','" + item_5[2] + "','" + item_6[2] + "','" + item_7[2] + "','" + item_1[3] + "'," +
                "'" + item_2[3] + "','" + item_3[3] + "','" + item_4[3] + "','" + item_5[3] + "','" + item_6[3] + "','" + item_7[3] + "','" + item_1[4] + "','" + item_2[4] + "','" + item_3[4] + "','" + item_4[4] + "','" + item_5[4] + "'," +
                "'" + item_6[4] + "','" + item_7[4] + "','" + item_1[5] + "','" + item_2[5] + "','" + item_3[5] + "','" + item_4[5] + "','" + item_5[5] + "','" + item_6[5] + "','" + item_7[5] + "','" + item_1[6] + "','" + item_2[6] + "'," +
                "'" + item_3[6] + "','" + item_4[6] + "','" + item_5[6] + "','" + item_6[6] + "','" + item_7[6] + "','" + item_1[7] + "','" + item_2[7] + "','" + item_3[7] + "','" + item_4[7] + "','" + item_5[7] + "','" + item_6[7] + "'," +
                "'" + item_7[7] + "','" + item_1[8] + "','" + item_2[8] + "','" + item_3[8] + "','" + item_4[8] + "','" + item_5[8] + "','" + item_6[8] + "','" + item_7[8] + "','" + item_1[9] + "','" + item_2[9] + "','" + item_3[9] + "'," +
                "'" + item_4[9] + "','" + item_5[9] + "','" + item_6[9] + "','" + item_7[9] + "','" + gold[0] + "','" + gold[1] + "','" + gold[2] + "','" + gold[3] + "','" + gold[4] + "','" + gold[5] + "','" + gold[6] + "'," +
                "'" + gold[7] + "','" + gold[8] + "','" + gold[9] + "','" + damage_objective[0] + "','" + damage_objective[1] + "','" + damage_objective[2] + "','" + damage_objective[3] + "','" + damage_objective[4] + "','" + damage_objective[5] + "','" + damage_objective[6] + "','" + damage_objective[7] + "'," +
                "'" + damage_objective[8] + "','" + damage_objective[9] + "','" + damage_champions[0] + "','" + damage_champions[1] + "','" + damage_champions[2] + "','" + damage_champions[3] + "','" + damage_champions[4] + "','" + damage_champions[5] + "','" + damage_champions[6] + "','" + damage_champions[7] + "','" + damage_champions[8] +
                "','" + damage_champions[9] + "','" + damage_buildings[0] + "','" + damage_buildings[1] + "','" + damage_buildings[2] + "','" + damage_buildings[3] + "','" + damage_buildings[4] + "','" + damage_buildings[5] + "','" + damage_buildings[6] + "','" + damage_buildings[7] + "','" + damage_buildings[8] + "','" + damage_buildings[9] + "')";

        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement(sql);

            posted.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

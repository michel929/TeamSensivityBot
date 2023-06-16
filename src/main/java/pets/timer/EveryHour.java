package pets.timer;

import functions.GetInfos;
import mysql.dashboard.PunkteSystem;
import pets.Function;
import pets.mysql.PetsManager;
import pets.tiere.Tier;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.TimerTask;

public class EveryHour extends TimerTask {
    @Override
    public void run() {
        ArrayList<Tier> tiers = PetsManager.getPets();

        for (Tier t: tiers) {
            int happy = 0;

            if(t.getDurstheute() == Function.amountDrinkDay() - 1){
                happy = happy + 40;
            }else if(t.getDurstheute() >= Function.amountDrinkDay() / 2){
                happy = happy + 30;
            }else if(t.getDurstheute() > 0){
                happy = happy + 10;
            }

            if(t.getHungerheute() == Function.amountFoodDay() - 1){
                happy = happy + 30;
            }else if(t.getHungerheute() >= Function.amountFoodDay() / 2){
                happy = happy + 20;
            }else if(t.getHungerheute() > 0){
                happy = happy + 10;
            }

            if(happy + t.getHappy() / 2 < 100){
                happy = happy + t.getHappy() / 2;
            }else {
                happy = 100;
            }

            PetsManager.update(happy, "happy", t.getDiscord_id());

            if(happy >= 80){
                PunkteSystem.uploadPoints(t.getDiscord_id(), 100 * t.getType().getMulti());
                PunkteSystem.upload(t.getDiscord_id(), 100 * t.getType().getMulti(), 1, "Durch dein Haustier");
                PetsManager.update(t.getPoints() + 100 * t.getType().getMulti(), "points", t.getDiscord_id());

                String url = "https://dashboard.sensivity.team/connect/discord/update-points.php?discord_id=" + t.getDiscord_id();
                String url2 = "https://dashboard.sensivity.team/connect/discord/refresh.php?id=" + t.getDiscord_id();
                try {
                    if(GetInfos.getPoints(new URL(url)).contains("Unauthorized")){
                        GetInfos.streamBOT(new URL(url2));
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

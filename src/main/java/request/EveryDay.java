package request;

import functions.GetInfos;
import main.Main;
import mysql.GetAllTokens;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import riot.RiotAPI;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.TimerTask;

public class EveryDay extends TimerTask {
    @Override
    public void run() {

        System.out.println("EveryDAy Request");

        String mostPoints = PunkteSystem.getMostPoints();
        Guild g = Main.INSTANCE.getGuild();

        Role r = g.getRoleById("1108320006921527296");
        g.addRoleToMember(g.getMemberById(mostPoints), r).queue();

        for (Member m: g.getMembers()) {
            if(m.getRoles().contains(r)){
                g.removeRoleFromMember(m, r).queue();
            }
        }

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
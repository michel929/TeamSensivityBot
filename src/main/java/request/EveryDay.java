package request;

import functions.GetInfos;
import functions.LolGuess;
import main.Start;
import mysql.GetAllTokens;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimerTask;

public class EveryDay extends TimerTask {
    @Override
    public void run() {

        System.out.println("EveryDAy Request");

        LolGuess.updateLOLGuess();

        String mostPoints = PunkteSystem.getMostPoints();
        Guild g = Start.INSTANCE.getGuild();

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
    }
}
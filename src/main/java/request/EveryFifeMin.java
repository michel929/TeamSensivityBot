package request;

import main.Main;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.ArrayList;
import java.util.TimerTask;

public class EveryFifeMin extends TimerTask {

    public static Role steam_role = null;
    public static Role riot_role = null;
    public static Role connect_role = null;

    @Override
    public void run() {
        Guild g = Main.INSTANCE.getGuild();

        //Connections Steam
        ArrayList<String> steam = PlayerInfos.getUserFromConnectonTyp("steam");

        for (String s : steam) {
            Member member = g.getMemberById(s);

            if(member != null) {
                if (!member.getRoles().contains(steam_role)) {
                    g.addRoleToMember(member, steam_role).queue();
                }
            }

            if(!member.getRoles().contains(connect_role)) {
                g.addRoleToMember(member, connect_role).queue();
            }
        }

        //Connections Riot
        ArrayList<String> riot = PlayerInfos.getUserFromConnectonTyp("riotgames");
        ArrayList<String> riot_lol = PlayerInfos.getUserFromConnectonTyp("leagueoflegends");

        for (String r : riot) {
            Member member = g.getMemberById(r);

            if(member != null) {
                if (!member.getRoles().contains(riot_role)) {
                    g.addRoleToMember(member, riot_role).queue();
                }

                if(!member.getRoles().contains(connect_role)) {
                    g.addRoleToMember(member, connect_role).queue();
                }
            }
        }

        for (String r : riot_lol) {
            Member member = g.getMemberById(r);

            if(member != null) {
                if (!member.getRoles().contains(riot_role)) {
                    g.addRoleToMember(member, riot_role).queue();
                }

                if(!member.getRoles().contains(connect_role)) {
                    g.addRoleToMember(member, connect_role).queue();
                }
            }
        }
    }
}

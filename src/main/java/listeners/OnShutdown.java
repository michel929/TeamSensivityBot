package listeners;

import createChill.listeners.MemberJoinChannel;
import functions.GetInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class OnShutdown extends ListenerAdapter {
    @Override
    public void onShutdown(ShutdownEvent event) {
        //PointSystem
        ConcurrentHashMap<Member, LocalDateTime> members = MemberJoinChannel.getMembers();

        for (Member me: members.keySet()) {
            if (PlayerInfos.isExist(me.getId(), "discord_id", "users")) {
                LocalDateTime date = members.get(me);
                Minutes m = Minutes.minutesBetween(date, LocalDateTime.now());

                if(m.getMinutes() > 1) {
                    PunkteSystem.uploadMinutes(date, LocalDateTime.now(), me.getId(), m.getMinutes());

                    if(me.isBoosting()){
                        PunkteSystem.uploadPoints(me.getId(), m.getMinutes() + (m.getMinutes() / 2));
                        PunkteSystem.upload(me.getId(), m.getMinutes() + (m.getMinutes() / 2), 1, "Durch Aktivität im SprachChannel. (Points x 1.5 Booster)");
                    }else {
                        PunkteSystem.uploadPoints(me.getId(), m.getMinutes());
                        PunkteSystem.upload(me.getId(), m.getMinutes(), 1, "Durch Aktivität im SprachChannel.");
                    }

                    if(!PlayerInfos.getInfo(me.getId(),"discord_id", "discord_token", "users").equals("0")){
                        String url = "https://dashboard.sensivity.team/connect/discord/update-points.php?discord_id=" + me.getId();
                        String url2 = "https://dashboard.sensivity.team/connect/discord/refresh.php?id=" + me.getId();
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
    }
}

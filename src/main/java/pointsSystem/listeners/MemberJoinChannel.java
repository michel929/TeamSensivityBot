package pointsSystem.listeners;

import functions.GetInfos;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;

import java.net.MalformedURLException;
import java.net.URL;

public class MemberJoinChannel extends ListenerAdapter {
    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        Role r = event.getGuild().getRoleById("1073170184820498505");
        if(event.getChannelJoined() == null) {
            if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users") && createChill.listeners.MemberJoinChannel.getMembers().containsKey(event.getMember()) && event.getMember().getRoles().contains(r)) {
                LocalDateTime date = createChill.listeners.MemberJoinChannel.getMembers().get(event.getMember());
                Minutes m = Minutes.minutesBetween(date, LocalDateTime.now());

                if (m.getMinutes() > 1) {
                    PunkteSystem.uploadMinutes(date, LocalDateTime.now(), event.getMember().getId(), m.getMinutes());

                    if (event.getMember().isBoosting()) {
                        PunkteSystem.uploadPoints(event.getMember().getId(), m.getMinutes() + (m.getMinutes() / 2));
                        PunkteSystem.upload(event.getMember().getId(), m.getMinutes() + (m.getMinutes() / 2), 1, "Durch Aktivität im SprachChannel. (Points x 1.5 Booster)");
                    } else {
                        PunkteSystem.uploadPoints(event.getMember().getId(), m.getMinutes());
                        PunkteSystem.upload(event.getMember().getId(), m.getMinutes(), 1, "Durch Aktivität im SprachChannel.");
                    }

                    if (!PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "discord_token", "users").equals("0")) {
                        String url = "https://dashboard.sensivity.team/connect/discord/update-points.php?discord_id=" + event.getMember().getId();
                        String url2 = "https://dashboard.sensivity.team/connect/discord/refresh.php?id=" + event.getMember().getId();
                        try {
                            if (GetInfos.getPoints(new URL(url)).contains("Unauthorized")) {
                                GetInfos.streamBOT(new URL(url2));
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                createChill.listeners.MemberJoinChannel.getMembers().remove(event.getMember());
            }
        }

        if(event.getChannelLeft() == null){
            if (BotInfos.getBotInfos("punktesystem").equals("1")) {
                if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users") && event.getMember().getRoles().contains(r)) {
                    createChill.listeners.MemberJoinChannel.getMembers().put(event.getMember(), LocalDateTime.now());
                }
            }
        }
    }
}

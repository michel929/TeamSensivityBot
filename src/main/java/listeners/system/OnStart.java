package listeners.system;

import dashboard.system.listeners.StatusChange;
import functions.GetGameRoles;
import createChill.listeners.MemberJoinChannel;
import main.Start;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.UploadRole;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import request.EveryDay;
import request.EveryFifeMin;
import request.TwentySec;

import java.util.List;
import java.util.Timer;

import static main.Start.VERSION_ID;

public class OnStart extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {

        Guild g = event.getJDA().getGuildById(Start.GUILD_ID);
        Start.INSTANCE.setGuild(g);
        Start.INSTANCE.setGameRoles(new GetGameRoles(g.getRoles()));

        new Timer().schedule(new TwentySec(), 0, 1000 * 20);
        new Timer().schedule(new EveryDay(), 0, 1000 * 60 * 60 * 24);
        new Timer().schedule(new EveryFifeMin() , 0, 1000 * 60 * 5);

        EveryFifeMin.steam_role = Start.INSTANCE.getGuild().getRoleById("1124250023526408293");
        EveryFifeMin.riot_role = Start.INSTANCE.getGuild().getRoleById("1124248677465198614");
        EveryFifeMin.connect_role = Start.INSTANCE.getGuild().getRoleById("1124248473483616296");

        List<Role> rollen = g.getRoles();

        //Update Rollen in Datenbank
        String hex = "";

        UploadRole.dropTable("discord_role");
        UploadRole.dropTable("user_role");

        for (Member m: g.getMembers()) {
            for (Role r: m.getRoles()) {
                PlayerInfos.insertRole(m.getId(), r.getId());
            }

            //Status Change Add all
            if(PlayerInfos.isExist(m.getId(), "discord_id", "users")){

                LocalDateTime last = PlayerInfos.getLastStatus(m.getId());

                if(last != null) {
                    Minutes min = Minutes.minutesBetween(last, LocalDateTime.now());

                    PlayerInfos.uploadStatus(m.getOnlineStatus().toString(), last, LocalDateTime.now(), m.getId(), min.getMinutes());
                }

                StatusChange.status.put(m.getId(), LocalDateTime.now());
            }
        }

        for (Role r: rollen) {

                if(!(r.getColor() == null)) {
                    hex = String.format("#%02x%02x%02x", r.getColor().getRed(), r.getColor().getGreen(), r.getColor().getBlue());
                }else {
                    hex = "#95a5a6";
                }

                UploadRole.insertRole(r.getId(), hex, r.getName(), r.getPositionRaw());
        }


        List<VoiceChannel> voice = g.getVoiceChannels();
        List<VoiceChannel> chillVoice = g.getCategoryById(BotInfos.getBotInfos("chill_cat")).getVoiceChannels();

        //Get Alle User in Channel and OnlinePlayer
        int OnlineUser = 0;
        for (VoiceChannel v: voice) {
            MemberJoinChannel.add(v.getMembers());
            for(Member member: v.getMembers()) {
                OnlineUser++;
            }
        }

        //Get All ChillChannel
        for (VoiceChannel v: chillVoice) {
            if(!v.getId().equals(BotInfos.getBotInfos("chill_channel"))){
                if(v.getMembers().size() == 0){
                    v.delete().queue();
                }else {
                    MemberJoinChannel.channel.add(v);
                }
            }
        }

        //OnlinePlayer
        BotInfos.updateInfoInt("user_online", OnlineUser);

        //UserCount
        BotInfos.updateInfoInt("user_count", g.getMemberCount());

        Start.api.getPresence().setStatus(OnlineStatus.ONLINE);
        Start.api.getPresence().setPresence(Activity.customStatus("VERSION " + VERSION_ID), true);
    }
}

package listeners;

import main.Start;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MemberJoinChannel extends ListenerAdapter {

    public static List<Channel> channel = new ArrayList<>();
    private static ConcurrentHashMap<Member, LocalDateTime> members = new ConcurrentHashMap<>();

    static int i = 1;
    EnumSet<Permission> permission = EnumSet.of(Permission.MANAGE_CHANNEL, Permission.VOICE_CONNECT, Permission.VOICE_MUTE_OTHERS, Permission.VOICE_DEAF_OTHERS, Permission.VOICE_MOVE_OTHERS);

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        Category c = Start.INSTANCE.getApi().getGuildById(Start.GUILD_ID).getCategoryById(BotInfos.getBotInfos("chill_cat"));

        if(event.getChannelLeft() != null) {
            //Create-Chill
            if (channel.contains(event.getChannelLeft())) {
                if (event.getChannelLeft().getMembers().size() == 0) {
                    event.getChannelLeft().delete().queue();
                    channel.remove(event.getChannelLeft());
                    i--;
                }
            }
        }

        if(event.getChannelJoined() != null) {
            //Create-Chill
            if (BotInfos.getBotInfos("chill_create").equals("1")) {
                if (event.getChannelJoined().getId().equals(BotInfos.getBotInfos("chill_channel"))) {
                    c.createVoiceChannel("Chill | " + i).addPermissionOverride(event.getMember(), permission, null).queue(voiceChannel -> {
                        Start.INSTANCE.getApi().getGuildById(Start.GUILD_ID).moveVoiceMember(event.getMember(), voiceChannel).queue();
                        channel.add(voiceChannel);
                    });
                    i++;
                }
            }
        }

        if(event.getChannelLeft() == null){
            //OnlineUser
            BotInfos.addOnlineUser();

            //PointsSystem
            if (BotInfos.getBotInfos("punktesystem").equals("1")) {
                if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {
                    members.put(event.getMember(), LocalDateTime.now());
                }
            }
        }

        if(event.getChannelJoined() == null){
            //OnlineUser
            BotInfos.removeOnlineUser();

            //PointSystem
            if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users") && members.containsKey(event.getMember())) {
                LocalDateTime date = members.get(event.getMember());
                Minutes m = Minutes.minutesBetween(date, LocalDateTime.now());

                if(m.getMinutes() > 1) {
                    PunkteSystem.uploadMinutes(date, LocalDateTime.now(), event.getMember().getId(), m.getMinutes());
                    PunkteSystem.uploadPoints(event.getMember().getId(), m.getMinutes());
                    PunkteSystem.upload(event.getMember().getId(), m.getMinutes(), 1, "Durch Aktivität im SprachChannel.");
                }

                members.remove(event.getMember());
            }
        }
    }

    public static void addI(){
        i++;
    }
    public static void add(List<Member> member){

        for (Member m: member) {
            if(PlayerInfos.isExist(m.getId(), "discord_id", "users")) {
                members.put(m, LocalDateTime.now());
            }
        }
    }
}

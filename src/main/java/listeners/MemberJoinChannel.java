package listeners;

import functions.GetInfos;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MemberJoinChannel extends ListenerAdapter {

    public static List<Channel> channel = new ArrayList<>();
    public static List<Channel> watch = new ArrayList<>();
    private static ConcurrentHashMap<Member, LocalDateTime> members = new ConcurrentHashMap<>();
    EnumSet<Permission> permission = EnumSet.of(Permission.MANAGE_CHANNEL, Permission.VOICE_CONNECT, Permission.VOICE_MUTE_OTHERS, Permission.VOICE_DEAF_OTHERS, Permission.VOICE_MOVE_OTHERS);

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        Category c = event.getGuild().getCategoryById(BotInfos.getBotInfos("chill_cat"));

        if(event.getChannelLeft() != null) {
            //Create-Chill
            if (channel.contains(event.getChannelLeft())) {
                if (event.getChannelLeft().getMembers().size() == 0) {
                    event.getChannelLeft().delete().queue();
                    channel.remove(event.getChannelLeft());
                    watch.remove(event.getChannelLeft());
                }
            }
        }

        if(event.getChannelJoined() != null) {
            //isWatchroom
            if(watch.contains(event.getChannelJoined())){
                event.getMember().mute(true).queue();
                event.getMember().getUser().openPrivateChannel().queue(privateChannel -> {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Achtung WatchRoom");
                    builder.setDescription("Du bist einem WatchRoom gejoined. In diesem Channel wird ein Film oder eine Serie geschaut bitte nimm Rücksicht. Um nicht mehr gemuted zu sein bestätige diese Nachricht mit dem Button.");
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setColor(Color.red);
                    privateChannel.sendMessageEmbeds(builder.build()).addActionRow(Button.success("verstanden", "Verstanden!")).queue();
                });
            }else {
                event.getMember().mute(false).queue();
            }

            //Create-Chill
            if (BotInfos.getBotInfos("chill_create").equals("1")) {
                if (event.getChannelJoined().getId().equals(BotInfos.getBotInfos("chill_channel"))) {
                    boolean finish = false;
                    int x = 1;

                    while(finish == false) {
                        int w = 0;
                        for (Channel v : channel) {
                            if(v.getName().contains("" + x)){
                                x++;
                            }else {
                                w++;
                            }
                        }

                        if(w == channel.size()){
                            finish = true;
                        }
                    }

                    c.createVoiceChannel("Chill | " + x).addPermissionOverride(event.getMember(), permission, null).queue(voiceChannel -> {
                        event.getGuild().moveVoiceMember(event.getMember(), voiceChannel).queue();
                        channel.add(voiceChannel);

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                        builder.setColor(Color.decode("#9914fa"));
                        builder.setTitle("Watch Room / Normal Room");
                        builder.setDescription("Wandel den Cannel in einen WatchRoom. Das bedeutet jeder der joined wird erst Serverweit gemuted und wird erst wieder entmuted wenn er bestätigt das ihr einen Film schaut.");

                        voiceChannel.sendMessageEmbeds(builder.build()).addActionRow(Button.secondary("watch", "Change Channel Type")).setSuppressEmbeds(true).queue();
                    });
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

                    if(event.getMember().isBoosting()){
                        PunkteSystem.uploadPoints(event.getMember().getId(), m.getMinutes() + (m.getMinutes() / 2));
                        PunkteSystem.upload(event.getMember().getId(), m.getMinutes() + (m.getMinutes() / 2), 1, "Durch Aktivität im SprachChannel. (Points x 1.5 Booster)");
                    }else {
                        PunkteSystem.uploadPoints(event.getMember().getId(), m.getMinutes());
                        PunkteSystem.upload(event.getMember().getId(), m.getMinutes(), 1, "Durch Aktivität im SprachChannel.");
                    }

                    if(!PlayerInfos.getInfo(event.getMember().getId(),"discord_id", "discord_token", "users").equals("0")){
                        String url = "https://dashboard.sensivity.team/connect/discord/update-points.php?discord_id=" + event.getMember().getId();
                        String url2 = "https://dashboard.sensivity.team/connect/discord/refresh.php?id=" + event.getMember().getId();
                        try {
                            if(GetInfos.getPoints(new URL(url)).contains("Unauthorized")){
                                GetInfos.streamBOT(new URL(url2));
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                members.remove(event.getMember());
            }
        }
    }

    public static void add(List<Member> member){

        for (Member m: member) {
            if(PlayerInfos.isExist(m.getId(), "discord_id", "users")) {
                members.put(m, LocalDateTime.now());
            }
        }
    }

    public static ConcurrentHashMap<Member, LocalDateTime> getMembers() {
        return members;
    }

}

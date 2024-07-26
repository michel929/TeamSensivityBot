package createChill.listeners;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.joda.time.LocalDateTime;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MemberJoinChannel extends ListenerAdapter {

    public static List<Channel> channel = new ArrayList<>();
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
                    watchRoom.listeners.MemberJoinChannel.watch.remove(event.getChannelLeft());
                }
            }
        }

        if(event.getChannelJoined() != null) {
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
                        builder.setDescription("Verwandel den Channel in einen WatchRoom. Das bedeutet jeder der joined wird erst Serverweit gemuted und wird erst wieder entmuted wenn er bestätigt das ihr einen Film schaut.");

                        voiceChannel.sendMessageEmbeds(builder.build()).addActionRow(Button.secondary("watch", "Change Channel Type")).setSuppressedNotifications(true).queue();
                    });
                }
            }
        }

        if(event.getChannelLeft() == null){
            //OnlineUser
            BotInfos.addOnlineUser();
        }

        if(event.getChannelJoined() == null){
            //OnlineUser
            BotInfos.removeOnlineUser();
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

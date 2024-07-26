package watchRoom.listeners;

import mysql.BotInfos;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;

public class MemberJoinChannel extends ListenerAdapter {

    public static ArrayList<Channel> watch = new ArrayList<>();

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
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
                    privateChannel.sendMessageEmbeds(builder.build()).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.success("verstanden", "Verstanden!")).queue();
                });
            }else {
                event.getMember().mute(false).queue();
            }
        }
    }
}

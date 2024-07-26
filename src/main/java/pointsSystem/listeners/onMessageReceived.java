package pointsSystem.listeners;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onMessageReceived extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        //PointsSystem
        if(PlayerInfos.isExist(event.getAuthor().getId(), "discord_id", "users") && BotInfos.getBotInfos("punktesystem").equals("1")){

            if(event.getChannelType().equals(ChannelType.TEXT)){
                Role r = event.getGuild().getRoleById("1073170184820498505");
                if(event.getMember().getRoles().contains(r)){
                    PunkteSystem.uploadPoints(event.getAuthor().getId(), 1);
                    PunkteSystem.upload(event.getAuthor().getId(), 1, 1, "Nachricht geschickt.");
                }
            }
        }
    }
}

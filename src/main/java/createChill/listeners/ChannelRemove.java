package createChill.listeners;

import main.Start;
import mysql.BotInfos;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChannelRemove extends ListenerAdapter {
    @Override
    public void onChannelDelete(ChannelDeleteEvent event) {

        //Chill Channel update
       if(event.getChannel().getId().equals(BotInfos.getBotInfos("chill_channel"))){
           Start.INSTANCE.getGuild().createVoiceChannel("Create Chill").queue(voiceChannel -> {
               BotInfos.updateInfo("chill_channel", voiceChannel.getId());
           });
       }
    }
}

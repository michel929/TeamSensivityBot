package unendlichkeit.listeners;

import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageDelete extends ListenerAdapter {
    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        if(event.getMessageId().equals(MessageRecived.messageid)){

            if(PlayerInfos.isExist(MessageRecived.userid, "discord_id", "users")) {
                int points = Integer.parseInt(PlayerInfos.getInfo(MessageRecived.userid, "discord_id", "unendlichkeit", "users"));
                PlayerInfos.updatePlayerInfos(MessageRecived.userid, "unendlichkeit", String.valueOf(points -1));
            }

            TextChannel textChannel = event.getChannel().asTextChannel();
            textChannel.getHistory().retrievePast(1).queue(messages -> {
                String message = messages.get(0).getContentDisplay();
                String newString = "";

                for (int i = 0; i < message.length(); i++) {
                    char a = message.charAt(i);
                    int ascii = (int) a;

                    if(ascii > 47 && ascii < 58){
                        newString = newString + a;
                    }
                }

                if(!newString.isEmpty()) {
                    MessageRecived.zahl = Long.parseLong(newString);
                    MessageRecived.userid = messages.get(0).getAuthor().getId();
                    MessageRecived.messageid = messages.get(0).getId();

                    System.out.println(newString + " | " + messages.get(0).getId());
                }else {
                    MessageRecived.zahl = 0;
                }
            });
        }
    }
}

package unendlichkeit.listeners;


import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class MessageRecived extends ListenerAdapter {
    public static long zahl;
    public static String userid;
    public static String messageid;
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getChannel().getId().equals("1144648374520402163")){

            String message = event.getMessage().getContentDisplay();
            String newString = "";

            for (int i = 0; i < message.length(); i++) {
                char a = message.charAt(i);
                int ascii = (int) a;

                if(ascii > 47 && ascii < 58){
                    newString = newString + a;
                }
            }

            if(!newString.isEmpty()){
                if(!event.getMember().getId().equals(userid)) {
                    long hohe = Long.parseLong(newString);
                    if (hohe == zahl + 1) {
                        zahl = zahl + 1;
                        userid = event.getMember().getId();
                        messageid = event.getMessageId();

                        if(PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {
                            int points = Integer.parseInt(PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "unendlichkeit", "users"));
                            PlayerInfos.updatePlayerInfos(event.getMember().getId(), "unendlichkeit", String.valueOf(points + 1));
                        }
                    } else {
                        event.getMessage().delete().queue();
                    }
                }else {
                    event.getMessage().delete().queue();

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Du musst Warten!");
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setColor(Color.red);
                    builder.setDescription("Du musst warten bis jemand anderes die nächste Zahl geschrieben hat.");

                    event.getChannel().sendMessageEmbeds(builder.build()).setSuppressedNotifications(true).queue(message1 -> {
                        message1.delete().queueAfter(10, TimeUnit.SECONDS);
                    });
                }
            }
        }
    }
}

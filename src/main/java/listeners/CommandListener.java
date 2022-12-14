package listeners;

import main.Start;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //PointsSystem
        if(PlayerInfos.isExist(event.getAuthor().getId(), "discord_id", "users") && BotInfos.getBotInfos("punktesystem").equals("1")){
            PunkteSystem.uploadPoints(event.getAuthor().getId(), 1);
        }

        //Commands
        String message = event.getMessage().getContentDisplay();

            if (event.isFromType(ChannelType.TEXT)) {
                TextChannel channel = (TextChannel) event.getChannel();

                if (message.startsWith("&")) {
                    String[] args = message.substring(1).split(" ");

                    if (args.length > 0) {
                        if (!Start.INSTANCE.getCmdMan().perform(args[0], event.getMember(), channel, event.getMessage())) {
                            channel.sendMessage("Unbekannter Command").queue();
                        }
                    }
                }

            } else if (event.isFromType(ChannelType.PRIVATE)) {
                PrivateChannel channel = (PrivateChannel) event.getChannel();

                if (message.startsWith("&")) {
                    String[] args = message.substring(1).split(" ");

                    if (args.length > 0) {
                        if (!Start.INSTANCE.getCmdMan().perform(args[0], event.getAuthor(), channel, event.getMessage())) {
                            channel.sendMessage("Unbekannter Command").queue();
                        }
                    }
                }
            } else if (event.isFromType(ChannelType.NEWS)) {
                if (message.startsWith("&")) {
                    event.getChannel().sendMessage("Befehle k??nnen nur in normalen Text Kan??len verwendet werden!").queue();
                }
            }

        }
}
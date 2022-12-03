package commands;

import commands.types.ServerCommand;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearCommand implements ServerCommand {
    @Override
    public void performCommand(Member m, TextChannel channel, Message message) throws ParseException {
        message.delete().queueAfter(10, TimeUnit.SECONDS);

        if(m.hasPermission(Permission.ADMINISTRATOR)) {
            String[] args = message.getContentDisplay().substring(1).split(" ");

            if(args.length < 2){
                MessageHistory history = MessageHistory.getHistoryFromBeginning(channel).complete();
                List<Message> mess = history.getRetrievedHistory();

                for (Message message1 : mess) {

                    message1.delete().queue();
                }
            }else {
                try {

                    int anzahl = Integer.parseInt(args[1]);

                    MessageHistory history = MessageHistory.getHistoryFromBeginning(channel).complete();
                    List<Message> mess = history.getRetrievedHistory();

                    List<Message> selected = mess.subList(anzahl, mess.size() - 1);

                    for (Message message1 : selected) {

                        message1.delete().queue();
                    }
                }catch (Exception e){
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.RED);
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setDescription("Benutze ein Integer als Argument und kein String, ...");
                    builder.setAuthor("Team Sensivity");
                    builder.setTitle("Falscher Datentyp!");

                    channel.sendMessageEmbeds(builder.build()).queue((message1) -> {
                        message1.delete().queueAfter(10, TimeUnit.SECONDS);
                    });
                }
            }
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.RED);
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setDescription("Du hast keine Berechtigungen für diesen Command...");
            builder.setAuthor("Team Sensivity");
            builder.setTitle("NO PERMISSION!!");

            channel.sendMessageEmbeds(builder.build()).queue((message1) -> {
                message1.delete().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }
}

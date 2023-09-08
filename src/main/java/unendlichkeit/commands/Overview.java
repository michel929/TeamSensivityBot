package unendlichkeit.commands;

import commands.types.ServerCommand;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.parser.ParseException;

import java.awt.*;

public class Overview implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) throws ParseException {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
        builder.setTitle("Unendlichkeit Score");
        builder.setColor(Color.decode("#9914fa"));
        builder
    }
}

package commands.embeds.anleitungen;

import types.ServerCommand;
import main.Main;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.parser.ParseException;

import java.awt.*;

public class SelectGames implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) throws ParseException {
        if(event.getMember().hasPermission(Permission.ADMINISTRATOR)){
            EmbedBuilder schritt1 = new EmbedBuilder();
            schritt1.setColor(Color.decode("#9914fa"));
            schritt1.setThumbnail(BotInfos.getBotInfos("logo_url"));
            schritt1.addField("Schritt 1:", "Klicke auf den Reiter 'Kanäle & Rollen'.", false);
            schritt1.setImage("https://sensivity.team/bot/img/anleitung/Schritt_1.png");
            schritt1.setTitle("Select Games");

            EmbedBuilder schritt2 = new EmbedBuilder();
            schritt2.setColor(Color.decode("#9914fa"));
            schritt2.setThumbnail(BotInfos.getBotInfos("logo_url"));
            schritt2.addField("Schritt 2:", "Hier kannst du nun die Games auswählen die du spielst.", false);
            schritt2.setImage("https://sensivity.team/bot/img/anleitung/Schritt_2.png");
            schritt2.setTitle("Select Games");

            event.getChannel().sendMessageEmbeds(schritt1.build()).queue();
            event.getChannel().sendMessageEmbeds(schritt2.build()).queue();
        }else {
            event.getChannel().sendMessageEmbeds(Main.INSTANCE.getEmbedMessages().getNoPermission()).queue();
        }
    }
}

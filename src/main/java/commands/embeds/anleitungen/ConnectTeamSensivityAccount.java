package commands.embeds.anleitungen;

import types.ServerCommand;
import main.Main;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.parser.ParseException;

import java.awt.*;

public class ConnectTeamSensivityAccount implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) throws ParseException {
        if(event.getMember().hasPermission(Permission.ADMINISTRATOR)){
            EmbedBuilder schritt1 = new EmbedBuilder();
            schritt1.setColor(Color.decode("#9914fa"));
            schritt1.setThumbnail(BotInfos.getBotInfos("logo_url"));
            schritt1.addField("Schritt 1:", "Drück auf den Dropdown Pfeil neben dem Servernamen.", false);
            schritt1.setImage("https://sensivity.team/bot/img/account/Schritt_1.png");
            schritt1.setTitle("Connect Team Sensivity Account");

            EmbedBuilder schritt2 = new EmbedBuilder();
            schritt2.setColor(Color.decode("#9914fa"));
            schritt2.setThumbnail(BotInfos.getBotInfos("logo_url"));
            schritt2.addField("Schritt 2:", "Wähle nun Verknüpfte Rollen aus.", false);
            schritt2.setImage("https://sensivity.team/bot/img/account/Schritt_2.png");
            schritt2.setTitle("Connect Team Sensivity Account");

            EmbedBuilder schritt3 = new EmbedBuilder();
            schritt3.setColor(Color.decode("#9914fa"));
            schritt3.setThumbnail(BotInfos.getBotInfos("logo_url"));
            schritt3.addField("Schritt 3:", "Klicke dich durch das Menü bis du zu einer Webseite weiter geleitet wirst.", false);
            schritt3.setImage("https://sensivity.team/bot/img/account/Schritt_3.png");
            schritt3.setTitle("Connect Team Sensivity Account");

            EmbedBuilder schritt4 = new EmbedBuilder();
            schritt4.setColor(Color.decode("#9914fa"));
            schritt4.setThumbnail(BotInfos.getBotInfos("logo_url"));
            schritt4.addField("Schritt 4:", "Sobald Sie 'Accounts erfolgreich verknüpft!' sehen können Sie das Fenster wieder schließen und die Rolle aktivieren.", false);
            schritt4.setImage("https://sensivity.team/bot/img/account/Schritt_4.png");
            schritt4.setTitle("Connect Team Sensivity Account");

            EmbedBuilder schritt5 = new EmbedBuilder();
            schritt5.setColor(Color.decode("#9914fa"));
            schritt5.setThumbnail(BotInfos.getBotInfos("logo_url"));
            schritt5.addField("Schritt 5:", "Danach noch auf 'Fertig' in Discord klicken und danach auf 'Abschließen' .", false);
            schritt5.setImage("https://sensivity.team/bot/img/account/Schritt_5.png");
            schritt5.setTitle("Connect Team Sensivity Account");

            event.getChannel().sendMessageEmbeds(schritt1.build()).queue();
            event.getChannel().sendMessageEmbeds(schritt2.build()).queue();
            event.getChannel().sendMessageEmbeds(schritt3.build()).queue();
            event.getChannel().sendMessageEmbeds(schritt4.build()).queue();
            event.getChannel().sendMessageEmbeds(schritt5.build()).queue();
        }else {
            event.getChannel().sendMessageEmbeds(Main.INSTANCE.getEmbedMessages().getNoPermission()).queue();
        }
    }
}

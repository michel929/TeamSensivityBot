package templates;

import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbedMessages {

    private MessageEmbed noAccount;
    private MessageEmbed notActive;
    public EmbedMessages(){

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.red);
        builder.setDescription("Du brauchst einen TeamSensivity Account um diese Aktion auszuführen. Benutze entweder die Website oder den /connect Command.");
        builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
        builder.setTitle("Keinen Team Sensivity Account");

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.red);
        embedBuilder.setDescription("Dieser Befehl ist zurzeit deaktiviert. Versuche es später erneut.");
        embedBuilder.setThumbnail(BotInfos.getBotInfos("logo_url"));
        embedBuilder.setTitle("Befel ist deaktiviert.");


        noAccount = builder.build();
        notActive = embedBuilder.build();
    }

    public MessageEmbed getNoAccount() {
        return noAccount;
    }

    public MessageEmbed getNotActive() {
        return notActive;
    }
}

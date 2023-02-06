package templates;

import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbedMessages {

    public static MessageEmbed userHasNoAccount(){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.red);
        builder.setDescription("Du brauchst einen TeamSensivity Account um diese Aktion auszuführen. Benutze entweder die Website oder den /connect Command.");
        builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
        builder.setTitle("Keinen Team Sensivity Account");

        return builder.build();
    }
}

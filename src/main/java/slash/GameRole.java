package slash;

import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import slash.types.ServerSlash;

import java.awt.*;

public class GameRole implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if(BotInfos.getBotInfos("cmd_role_on").equals("1")) {
          //TODO wenn das Feature verfügbar ist
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.red);
            builder.setDescription("Dieser Befehl ist zurzeit deaktiviert. Versuche es später erneut.");
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setTitle("Befel ist deaktiviert.");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }
    }
}

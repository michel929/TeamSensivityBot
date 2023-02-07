package slash;

import main.Main;
import main.Start;
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
            event.replyEmbeds(Main.INSTANCE.getEmbedMessages().getNotActive()).setEphemeral(true).queue();
        }
    }
}

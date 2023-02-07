package slash;

import main.Main;
import main.Start;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import slash.types.ServerSlash;

import java.awt.*;

public class LockUsername implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if (BotInfos.getBotInfos("cmd_lock_on").equals("1")) {
            if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "user_rename")) {

                PlayerInfos.removeUserToList(event.getMember().getId());

                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.decode("#2ecc71"));
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setDescription("Dein Nickname kann nun wieder geändert werden.");
                builder.setTitle("Username entsperrt");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            } else {
                PlayerInfos.addUserToList(event.getMember().getId());

                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.decode("#2ecc71"));
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setDescription("Es kann nun keiner mehr deinen Nicknamen ändern.");
                builder.setTitle("Username gesperrt");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }
        }else {
            event.replyEmbeds(Main.INSTANCE.getEmbedMessages().getNotActive()).setEphemeral(true).queue();
        }
    }
}

package dashboard.connect.slash;

import main.Start;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import types.ServerSlash;

import java.awt.*;

public class Connect implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if(PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {

            if (event.getSubcommandName().equals("steam")) {

                if(!PlayerInfos.getConnectionFromType("steam").isEmpty()) {

                }else {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("Connect Steam");
                    embed.setDescription("Benutze den Button um deinen Steam Account zu verknüpfen.");
                    embed.setColor(Color.decode("#9914fa"));
                    embed.setThumbnail(BotInfos.getBotInfos("logo_url"));

                    event.replyEmbeds(embed.build()).setActionRow(Button.link("https://dashboard.sensivity.team/connect/steam/bot/init-openId.php?discord_id=" + event.getMember().getId(), "Connect Steam")).setEphemeral(true);
                }
            }
        }else {
            event.replyEmbeds(Start.INSTANCE.getEmbedMessages().getNoAccount()).setEphemeral(true).queue();
        }
    }
}

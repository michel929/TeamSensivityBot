package slash.dashboard;

import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import slash.types.ServerSlash;

import java.awt.*;

public class ConnectRiot implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if(BotInfos.getBotInfos("cmd_riot_on").equals("1")) {
            TextInput steamid = TextInput.create("riotID", "Riot SummonerName", TextInputStyle.SHORT)
                    .setPlaceholder("Enter your SummonerName")
                    .setMinLength(3)
                    .setMaxLength(16)
                    .build();

            Modal modal = Modal.create("riot", "Connect your RiotAccount")
                    .addActionRows(ActionRow.of(steamid))
                    .build();

            event.replyModal(modal).queue();
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

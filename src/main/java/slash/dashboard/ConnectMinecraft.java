package slash.dashboard;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import slash.types.ServerSlash;

import java.awt.*;

public class ConnectMinecraft implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {
            if (BotInfos.getBotInfos("cmd_minecraft_on").equals("1")) {
                TextInput steamid = TextInput.create("minecraftCode", "Minecraft Verifizierungscode", TextInputStyle.SHORT)
                        .setPlaceholder("Enter your Code")
                        .setMinLength(36)
                        .setMaxLength(36)
                        .build();

                Modal modal = Modal.create("minecraft", "Connect your MinecraftAccount")
                        .addActionRows(ActionRow.of(steamid))
                        .build();

                event.replyModal(modal).queue();
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.red);
                builder.setDescription("Dieser Befehl ist zurzeit deaktiviert. Versuche es später erneut.");
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setTitle("Befel ist deaktiviert.");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.red);
            builder.setDescription("Du brauchst einen TeamSensivityAccount um diesen Command zu benutzen. Benutze **/connect** um ein Account zu erstellen");
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setTitle("Kein TeamSensivityAccount.");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }
    }
}

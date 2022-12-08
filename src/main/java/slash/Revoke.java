package slash;

import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import slash.types.ServerSlash;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Revoke implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {
            PlayerInfos.removeAccount(event.getUser().getId());
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.red);
            builder.setDescription("Du besitzt keinen TeamSensivityAccount mehr... Du kannst dir jedoch einen mit **/connect** erstellen.");
            builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
            builder.setTitle("Befehl fehlgeschlagen!");

            event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                message.delete().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }
}

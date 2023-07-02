package slash;

import main.Main;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import slash.types.ServerSlash;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;

public class Bday implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if(PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {
            if (event.getSubcommandName().equals("add")) {
                int day = event.getOption("tag").getAsInt();
                int monat = event.getOption("monat").getAsInt();
                int jahr = event.getOption("jahr").getAsInt();

                LocalDate date = LocalDate.parse(jahr + "-" + monat + "-" + day);
                PlayerInfos.updatePlayerInfos(event.getMember().getId(), "bday", date);

                EmbedBuilder builder = new EmbedBuilder();
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setTitle("Bday erfolgreich gesetzt");
                builder.setColor(Color.decode("#2ecc71"));
                builder.setDescription("Du hast deinen Geburtstag erfolgreich festgelegt.");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();

            } else if (event.getSubcommandName().equals("remove")) {

                PlayerInfos.updatePlayerInfos(event.getMember().getId(), "bday");

                EmbedBuilder builder = new EmbedBuilder();
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setTitle("Bday erfolgreich entfernt");
                builder.setColor(Color.decode("#2ecc71"));
                builder.setDescription("Du hast deinen Geburtstag erfolgreich gelöscht.");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();

            }else if (event.getSubcommandName().equals("next")){

            }
        }else {
            event.replyEmbeds(Main.INSTANCE.getEmbedMessages().getNoAccount()).setEphemeral(true).queue();
        }
    }
}

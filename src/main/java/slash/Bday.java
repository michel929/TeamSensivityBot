package slash;

import main.Main;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.joda.time.LocalDateTime;
import types.ServerSlash;

import java.awt.*;
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

            } else if(event.getSubcommandName().equals("info")){

                System.out.println("test");

                String datestring = PlayerInfos.getInfo(event.getMember().getId(), "discord_id","bday","users");

                if(datestring != null) {

                    LocalDateTime date = LocalDateTime.parse(datestring);

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setTitle("Du hast am " + date.getDayOfMonth() + "." + date.getMonthOfYear() + "." + date.getYear() + " geburtstag");
                    builder.setDescription("Du hast in <t:" + date.plusYears(LocalDateTime.now().getYear() - date.getYear()).toDate().getTime() / 1000 + ":R> geburtstag.");

                    event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                }else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.RED);
                    builder.setTitle("Noch kein Bday gesetzt!");
                    builder.setDescription("Du hast uns noch nicht verraten wann du geburtstag hast. Bitte benutze den */bday add* Befehl um uns deinen Geburtstag zu verraten.");
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));

                    event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                }

            }
        }else {
            event.replyEmbeds(Main.INSTANCE.getEmbedMessages().getNoAccount()).setEphemeral(true).queue();
        }
    }
}

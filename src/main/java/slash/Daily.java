package slash;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import slash.types.ServerSlash;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.*;

public class Daily implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if(BotInfos.getBotInfos("cmd_daily_on").equals("1")) {
            if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {
                LocalDate date = PlayerInfos.getDaily(event.getMember().getId());
                LocalDate now = LocalDate.now();

                Date d1 = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date d2 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

                long elapsedms = d1.getTime() - d2.getTime();
                long diff = TimeUnit.MINUTES.convert(elapsedms, TimeUnit.MILLISECONDS);

                if(diff >= 1440){
                    PlayerInfos.updatePlayerInfos(event.getMember().getId(), "daily", java.sql.Date.valueOf(LocalDate.now()));
                    PunkteSystem.uploadPoints(event.getMember().getId(), 100);

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setDescription("Du hast deine DailyRewards eingesammelt du hast **10 Punkte** erhalten.");
                    builder.setTitle("DailyReward");
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setColor(Color.decode("#2ecc71"));

                    event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                }else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setDescription("Du hast bereits deine DailyRewards eingesammelt.");
                    builder.setTitle("DailyReward");
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setColor(Color.RED);

                    event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                }
            }
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

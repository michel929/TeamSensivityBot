package slash;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import slash.types.ServerSlash;

import static java.time.temporal.ChronoUnit.HOURS;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class Daily implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if(BotInfos.getBotInfos("cmd_daily_on").equals("1")) {
            if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {
                LocalDate date = PlayerInfos.getDaily(event.getMember().getId());
                LocalDate now = LocalDate.now();
                long diff = HOURS.between(date, now);

                if(diff >= 24){
                    PlayerInfos.updatePlayerInfos(event.getMember().getId(), "daily", Date.valueOf(LocalDate.now()));
                    PunkteSystem.uploadPoints(event.getMember().getId(), 10);

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setDescription("Du hast deine DailyRewards eingesammelt du hast **10 Punkte** erhalten.");
                    builder.setTitle("DailyReward");
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setColor(Color.decode("#2ecc71"));

                    event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                        message.delete().queueAfter(10, TimeUnit.SECONDS);
                    });
                }else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setDescription("Du hast bereits deine DailyRewards eingesammelt.");
                    builder.setTitle("DailyReward");
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setColor(Color.RED);

                    event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                        message.delete().queueAfter(10, TimeUnit.SECONDS);
                    });
                }
            }
        }
    }
}

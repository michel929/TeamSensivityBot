package music.slash;

import music.PlayerManager;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import slash.types.ServerSlash;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Volume implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if(BotInfos.getBotInfos("cmd_music_on").equals("1")) {
            int volume = event.getOption("volume").getAsInt();

            if (volume <= 100 && volume >= 0) {
                PlayerManager.getINSTANCE().getMusicManager(event.getGuild()).audioPlayer.setVolume(volume);

                EmbedBuilder builder = new EmbedBuilder();
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setColor(Color.decode("#2ecc71"));
                builder.setTitle("Volume auf " + volume + "% gesetzt");
                builder.setDescription("Du hast erfolgreich die Lautstärke angepasst.");

                event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                    message.delete().queueAfter(10, TimeUnit.SECONDS);
                });
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.red);
                builder.setDescription("Volume muss zwischen 0 und 100 liegen.");
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setTitle("Fehler beim benutzen des Commands");

                event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                    message.delete().queueAfter(10, TimeUnit.SECONDS);
                });
            }
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.red);
            builder.setDescription("Dieser Befehl ist zurzeit deaktiviert. Versuche es später erneut.");
            builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
            builder.setTitle("Befel ist deaktiviert.");

            event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                message.delete().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }
}

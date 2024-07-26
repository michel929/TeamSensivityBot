package music.slash;

import music.PlayerManager;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import types.ServerSlash;

import java.awt.*;

public class Stop implements ServerSlash{

    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if(BotInfos.getBotInfos("cmd_music_on").equals("1")) {
            PlayerManager.getINSTANCE().getMusicManager(event.getGuild()).audioPlayer.stopTrack();
            final AudioManager audioManager = event.getGuild().getAudioManager();

            audioManager.closeAudioConnection();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setColor(Color.decode("#2ecc71"));
            builder.setTitle("Song wurde gestoppt");
            builder.setDescription("Du hast erfolgreich den Song gestoppt.");

            event.replyEmbeds(builder.build()).queue();
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.red);
            builder.setDescription("Dieser Befehl ist zurzeit deaktiviert. Versuche es später erneut.");
            builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
            builder.setTitle("Befel ist deaktiviert.");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }
    }
}

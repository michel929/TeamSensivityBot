package music.slash;

import music.PlayerManager;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import slash.types.ServerSlash;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Stop implements ServerSlash{

    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        PlayerManager.getINSTANCE().getMusicManager(event.getGuild()).audioPlayer.stopTrack();
        final AudioManager audioManager = event.getGuild().getAudioManager();

        audioManager.closeAudioConnection();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
        builder.setColor(Color.decode("#2ecc71"));
        builder.setTitle("Song wurde gestoppt");
        builder.setDescription("Du hast erfolgreich den Song gestoppt.");

        event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
            message.delete().queueAfter(10, TimeUnit.SECONDS);
        });
    }
}

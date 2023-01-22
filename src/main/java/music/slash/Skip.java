package music.slash;

import main.Start;
import music.PlayerManager;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import slash.types.ServerSlash;

import java.awt.*;

public class Skip implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if(BotInfos.getBotInfos("cmd_music_on").equals("1")) {
            Guild g = event.getGuild();

            PlayerManager.getINSTANCE().getMusicManager(g).scheduler.nextTrack();

            if(PlayerManager.getINSTANCE().getMusicManager(g).audioPlayer.getPlayingTrack() != null) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setColor(Color.decode("#2ecc71"));
                builder.setTitle("Song wurde geskippt");
                builder.setDescription("Spiele jetzt: **" + PlayerManager.getINSTANCE().getMusicManager(g).audioPlayer.getPlayingTrack().getInfo().title + "**.");

                String u = PlayerManager.getINSTANCE().getMusicManager(g).audioPlayer.getPlayingTrack().getInfo().uri;

                u = u.substring(u.indexOf("=") + 1);

                builder.setImage("https://img.youtube.com/vi/" + u + "/0.jpg");

                event.replyEmbeds(builder.build()).queue();
            }else {
                final AudioManager audioManager = Start.INSTANCE.getGuild().getAudioManager();
                audioManager.closeAudioConnection();

                EmbedBuilder builder = new EmbedBuilder();
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setColor(Color.decode("#2ecc71"));
                builder.setTitle("Kein weiterer Song in der Warteschlange");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }
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

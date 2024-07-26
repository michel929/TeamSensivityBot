package music.slash;

import music.PlayerManager;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import types.ServerSlash;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

public class Play implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if(BotInfos.getBotInfos("cmd_music_on").equals("1")) {
            Guild guild = event.getGuild();

            if (event.getMember().getVoiceState().inAudioChannel()) {
                final AudioManager audioManager = guild.getAudioManager();
                final VoiceChannel memberChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();

                audioManager.openAudioConnection(memberChannel);
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.red);
                builder.setDescription("Du musst in einem SprachChannel sein um diesen Command zu benutzen.");
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setTitle("Fehler beim benutzen des Commands.");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }

            String u = event.getOption("song").getAsString();

            if (!isUrl(u)) {
                u = "ytsearch:" + u + " audio";
            }

            PlayerManager.getINSTANCE().loadAndPlay((TextChannel) event.getChannel(), u, event);
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.red);
            builder.setDescription("Dieser Befehl ist zurzeit deaktiviert. Versuche es später erneut.");
            builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
            builder.setTitle("Befel ist deaktiviert.");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }
    }

    public boolean isUrl(String url){
        try {
            new URI(url);
            return true;
        }catch (URISyntaxException e){
            return false;
        }
    }
}

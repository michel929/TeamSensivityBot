package slash.music;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import slash.types.ServerSlash;

public class Play implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        AudioManager audioManager = guild.getAudioManager();

        if(event.getMember().getVoiceState().inAudioChannel()) {
            String u = event.getOption("url").getAsString();
            if(u.contains("https://www.youtube.com")) {
                AudioChannel channel = event.getMember().getVoiceState().getChannel();

                audioManager.setSendingHandler();
                audioManager.openAudioConnection(channel);
            }else {

            }
        }else {

        }
    }
}

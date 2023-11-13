package listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.channel.ChannelManager;

public class GameActivity extends ListenerAdapter {
    @Override
    public void onUserActivityStart(UserActivityStartEvent event) {
        Member m = event.getMember();

        if(m.getVoiceState().inAudioChannel()){

        }
    }
}

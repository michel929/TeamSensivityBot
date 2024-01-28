package listeners;

import net.dv8tion.jda.api.events.session.SessionDisconnectEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnBotDisconnect extends ListenerAdapter {
    @Override
    public void onSessionDisconnect(SessionDisconnectEvent event) {
        event.getJDA().shutdownNow();
        System.exit(0);
    }
}

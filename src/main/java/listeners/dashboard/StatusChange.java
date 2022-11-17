package listeners.dashboard;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StatusChange extends ListenerAdapter {
    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {

        if(BotInfos.getBotInfos("syncSystem").equals("1")) {
            String status = event.getNewOnlineStatus().toString();
            PlayerInfos.updatePlayerInfos(event.getUser().getId(), "discord_status", status);
        }
    }
}

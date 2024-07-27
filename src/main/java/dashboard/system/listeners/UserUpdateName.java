package dashboard.system.listeners;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserUpdateName extends ListenerAdapter {

    @Override
    public void onUserUpdateName(UserUpdateNameEvent event) {
        if(BotInfos.getBotInfos("syncSystem").equals("1")) {
            String name = event.getUser().getName();
            PlayerInfos.updatePlayerInfos(event.getUser().getId(), "discord_username", name);
        }
    }
}

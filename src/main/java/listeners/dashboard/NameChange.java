package listeners.dashboard;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class NameChange extends ListenerAdapter {
    @Override
    public void onUserUpdateName(UserUpdateNameEvent event) {

        if(BotInfos.getBotInfos("syncSystem").equals("1")) {
            String name = event.getUser().getAsTag();
            PlayerInfos.updatePlayerInfos(event.getUser().getId(), "discord_username", name);
        }

    }
}

package dashboard.system.listeners;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.events.user.update.UserUpdateAvatarEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AvatarChange extends ListenerAdapter {
    @Override
    public void onUserUpdateAvatar(UserUpdateAvatarEvent event) {
        if(BotInfos.getBotInfos("syncSystem").equals("1")) {
            String url = event.getNewAvatarUrl();
            PlayerInfos.updatePlayerInfos(event.getUser().getId(), "discord_pb", url);
        }
    }
}

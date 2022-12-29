package listeners.dashboard;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.events.user.update.UserUpdateDiscriminatorEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserTagUpdate extends ListenerAdapter {
    @Override
    public void onUserUpdateDiscriminator(UserUpdateDiscriminatorEvent event) {
        if(BotInfos.getBotInfos("syncSystem").equals("1")) {
            String name = event.getUser().getAsTag();
            System.out.println(name);
            PlayerInfos.updatePlayerInfos(event.getUser().getId(), "discord_username", name);
        }
    }
}

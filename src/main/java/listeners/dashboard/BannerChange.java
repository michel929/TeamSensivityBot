package listeners.dashboard;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.user.update.GenericUserPresenceEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BannerChange extends ListenerAdapter {
    @Override
    public void onGenericUserPresence(GenericUserPresenceEvent event) {
        if (BotInfos.getBotInfos("syncSystem").equals("1")) {
            User.Profile p = event.getMember().getUser().retrieveProfile().complete();
            String banner = "";
            if(p.getBannerUrl() != null) {
                banner = p.getBannerUrl();
            }else {
                banner = Integer.toHexString(p.getAccentColor().getRed()) + Integer.toHexString(p.getAccentColor().getGreen()) + Integer.toHexString(p.getAccentColor().getBlue()) + Integer.toHexString(p.getAccentColor().getAlpha());
            }

            PlayerInfos.updatePlayerInfos(event.getMember().getId(), "discord_banner", banner);
        }
    }
}

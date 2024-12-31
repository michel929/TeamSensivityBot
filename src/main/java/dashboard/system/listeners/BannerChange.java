package dashboard.system.listeners;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.user.update.GenericUserPresenceEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BannerChange extends ListenerAdapter {
    @Override
    public void onGenericUserPresence(GenericUserPresenceEvent event) {
        if (BotInfos.getBotInfos("syncSystem").equals("1")) {
            event.getMember().getUser().retrieveProfile().queue(p -> {
                String banner = "";
                if(p.getBannerUrl() != null) {
                    banner = p.getBannerUrl();
                }else {
                    banner ="#" + Integer.toHexString((p.getAccentColorRaw() & 0xffffff) | 0x1000000).substring(1);
                }

                PlayerInfos.updatePlayerInfos(event.getMember().getId(), "discord_banner", banner);
            });
        }
    }
}

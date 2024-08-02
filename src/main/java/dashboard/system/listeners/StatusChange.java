package dashboard.system.listeners;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;

import java.util.concurrent.ConcurrentHashMap;

import static mysql.dashboard.PlayerInfos.uploadStatus;

public class StatusChange extends ListenerAdapter {

    public static ConcurrentHashMap<String, LocalDateTime> status = new ConcurrentHashMap<>();

    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {

        if(PlayerInfos.isExist(event.getUser().getId(), "discord_id", "users") && BotInfos.getBotInfos("syncSystem").equals("1")) {
            String status2 = event.getNewOnlineStatus().toString();
            String status_old = event.getOldOnlineStatus().toString();

            if(status.containsKey(event.getMember().getId())){
                LocalDateTime first = status.get(event.getMember().getId());
                Minutes m = Minutes.minutesBetween(first, LocalDateTime.now());

                uploadStatus(status_old, first, LocalDateTime.now(), event.getUser().getId(), m.getMinutes());

                status.remove(event.getMember().getId());
                status.put(event.getMember().getId(), LocalDateTime.now());
            }else {
                status.put(event.getMember().getId(), LocalDateTime.now());
            }

            PlayerInfos.updatePlayerInfos(event.getUser().getId(), "discord_status", status2);
        }
    }
}

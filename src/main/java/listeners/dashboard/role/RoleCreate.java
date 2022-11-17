package listeners.dashboard.role;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleCreate extends ListenerAdapter {
    @Override
    public void onRoleCreate(RoleCreateEvent event) {
        String name = event.getRole().getName();
        String color = Integer.toHexString(event.getRole().getColor().getRed()) + Integer.toHexString(event.getRole().getColor().getGreen()) + Integer.toHexString(event.getRole().getColor().getBlue()) + Integer.toHexString(event.getRole().getColor().getAlpha());
        String id = event.getRole().getId();
        int position = event.getRole().getPosition();

        if(!PlayerInfos.isExist(id, "role_id", "discord_role")) {
            BotInfos.createRole(id, color, name, position);
        }
    }
}

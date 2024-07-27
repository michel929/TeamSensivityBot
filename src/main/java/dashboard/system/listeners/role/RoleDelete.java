package dashboard.system.listeners.role;

import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleDelete extends ListenerAdapter {
    @Override
    public void onRoleDelete(RoleDeleteEvent event) {
        String id = event.getRole().getId();
        PlayerInfos.deleteInfo(id, "role_id", "discord_role");
    }
}

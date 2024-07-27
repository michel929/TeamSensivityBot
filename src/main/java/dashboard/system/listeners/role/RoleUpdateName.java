package dashboard.system.listeners.role;

import mysql.dashboard.PlayerInfos;
import mysql.dashboard.UploadRole;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleUpdateName extends ListenerAdapter {
    @Override
    public void onRoleUpdateName(RoleUpdateNameEvent event) {
        if(!PlayerInfos.isExist(event.getRole().getId(), "role_id", "discord_role")) {
            UploadRole.updateRole("role_name", event.getNewName(), event.getRole().getId());
        }
    }
}

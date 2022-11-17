package listeners.dashboard.role;

import mysql.dashboard.PlayerInfos;
import mysql.dashboard.UploadRole;
import net.dv8tion.jda.api.events.role.update.RoleUpdatePositionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleUpdatePosition extends ListenerAdapter {
    @Override
    public void onRoleUpdatePosition(RoleUpdatePositionEvent event) {
        if(!PlayerInfos.isExist(event.getRole().getId(), "role_id", "discord_role")) {
            UploadRole.updateRole("role_position", event.getNewPosition(), event.getRole().getId());
        }
    }
}

package dashboard.system.listeners.role;

import mysql.dashboard.PlayerInfos;
import mysql.dashboard.UploadRole;
import net.dv8tion.jda.api.events.role.update.RoleUpdateColorEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleUpdateColor extends ListenerAdapter {
    @Override
    public void onRoleUpdateColor(RoleUpdateColorEvent event) {
        if(!PlayerInfos.isExist(event.getRole().getId(), "role_id", "discord_role")) {
            String color = "#" + Integer.toHexString((event.getRole().getColorRaw() & 0xffffff) | 0x1000000).substring(1);
            UploadRole.updateRole("color", color, event.getRole().getId());
        }
    }
}

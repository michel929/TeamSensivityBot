package listeners.dashboard.role;

import mysql.dashboard.PlayerInfos;
import mysql.dashboard.UploadRole;
import net.dv8tion.jda.api.events.role.update.RoleUpdateColorEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleUpdateColor extends ListenerAdapter {
    @Override
    public void onRoleUpdateColor(RoleUpdateColorEvent event) {
        if(!PlayerInfos.isExist(event.getRole().getId(), "role_id", "discord_role")) {
            String color = Integer.toHexString(event.getNewColor().getRed()) + Integer.toHexString(event.getNewColor().getGreen()) + Integer.toHexString(event.getNewColor().getBlue()) + Integer.toHexString(event.getNewColor().getAlpha());
            UploadRole.updateRole("color", color, event.getRole().getId());
        }
    }
}

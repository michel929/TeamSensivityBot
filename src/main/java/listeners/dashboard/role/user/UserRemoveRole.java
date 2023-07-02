package listeners.dashboard.role.user;

import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserRemoveRole extends ListenerAdapter {
    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
        for (Role r: event.getRoles()) {
            if(PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "discord_role", "user_role") != null){
                PlayerInfos.removeRole(event.getMember().getId(), r.getId(), "user_role");
            }
        }
    }
}

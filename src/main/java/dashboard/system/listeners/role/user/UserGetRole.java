package dashboard.system.listeners.role.user;

import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserGetRole extends ListenerAdapter {
    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
        for (Role r: event.getRoles()) {
            if(PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "discord_role", "user_role") == null){
               PlayerInfos.insertRole(event.getMember().getId(), r.getId());
            }
        }
    }
}

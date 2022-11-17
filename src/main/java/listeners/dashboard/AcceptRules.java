package listeners.dashboard;

import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdatePendingEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AcceptRules extends ListenerAdapter {
    @Override
    public void onGuildMemberUpdatePending(GuildMemberUpdatePendingEvent event) {
        Member m = event.getMember();

        User.Profile p = m.getUser().retrieveProfile().complete();
        String banner = "";

        if(p.getBannerUrl() != null) {
            banner = p.getBannerUrl();
        }else {
            banner = p.getAccentColor().toString();
        }

        if(!PlayerInfos.isExist(m.getId(), "discord_id", "users")) {
            PlayerInfos.createAccount(m.getId(), m.getEffectiveName(), m.getEffectiveAvatarUrl(), banner);

            for (Role role: m.getRoles()) {
                if(!PlayerInfos.isExist(role.getId(), "discord_role", "user_role")) {
                    PlayerInfos.insertRole(m.getId(), role.getId());
                }
            }
        }
    }
}

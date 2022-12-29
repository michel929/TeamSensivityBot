package listeners.dashboard;

import mysql.BotInfos;
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
            banner ="#" + Integer.toHexString((p.getAccentColorRaw() & 0xffffff) | 0x1000000).substring(1);
        }

        if(!PlayerInfos.isExist(m.getId(), "discord_id", "users")) {
            Role re = event.getGuild().getRoleById(BotInfos.getBotInfos("dashboard_role"));
            event.getGuild().addRoleToMember(m, re).queue();

            PlayerInfos.createAccount(m.getId(), m.getUser().getAsTag(), m.getEffectiveAvatarUrl(), banner);

            for (Role role: m.getRoles()) {
                PlayerInfos.insertRole(m.getId(), role.getId());
            }
        }
    }
}

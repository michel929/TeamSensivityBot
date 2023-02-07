package listeners;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PlayerJoin extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member m = event.getMember();
        Role r = event.getGuild().getRoleById(BotInfos.getBotInfos("discord_role"));
        Role res = event.getGuild().getRoleById(BotInfos.getBotInfos("game_role"));

        BotInfos.addUserCount();

        event.getGuild().addRoleToMember(m, r).queue();
        event.getGuild().addRoleToMember(m, res).queue();

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

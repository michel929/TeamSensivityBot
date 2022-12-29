package listeners;

import mysql.BotInfos;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PlayerJoin extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member m = event.getMember();
        Role r = event.getGuild().getRoleById(BotInfos.getBotInfos("discord_role"));
        Role re = event.getGuild().getRoleById(BotInfos.getBotInfos("game_role"));

        BotInfos.addUserCount();

        event.getGuild().addRoleToMember(m, r).queue();
        event.getGuild().addRoleToMember(m, re).queue();
    }
}

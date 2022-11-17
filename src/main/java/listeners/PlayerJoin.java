package listeners;

import main.Start;
import mysql.BotInfos;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PlayerJoin extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member m = event.getMember();
        Role r = Start.INSTANCE.getApi().getGuildById(Start.GUILD_ID).getRoleById(BotInfos.getBotInfos("discord_role"));
        Role re = Start.INSTANCE.getApi().getGuildById(Start.GUILD_ID).getRoleById(BotInfos.getBotInfos("game_role"));

        m.getRoles().add(r);
        m.getRoles().add(re);
    }
}

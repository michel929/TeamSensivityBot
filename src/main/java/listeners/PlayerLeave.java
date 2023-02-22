package listeners;

import logging.LogSystem;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PlayerLeave extends ListenerAdapter {
    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        if(PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")){
            PlayerInfos.removeAccount(event.getUser().getId());
        }

        BotInfos.removeUserCount();

        LogSystem.logGeneral(event.getMember().getId(), "User hat den Server verlassen.", event.getUser().getAsTag());
    }
}

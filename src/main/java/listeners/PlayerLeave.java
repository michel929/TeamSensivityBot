package listeners;

import hosting.Funktionen;
import logging.LogSystem;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class PlayerLeave extends ListenerAdapter {
    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        if(PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")){
            PlayerInfos.removeAccount(event.getUser().getId());
        }

        try {
            int id = Funktionen.userExist(event.getUser().getId());
            if(id != 0){
                Funktionen.deleteUser(id);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BotInfos.removeUserCount();

        LogSystem.logGeneral(event.getMember().getId(), "User hat den Server verlassen.", event.getUser().getAsTag());
    }
}

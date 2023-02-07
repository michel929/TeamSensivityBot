package listeners;

import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberRenameUsername extends ListenerAdapter {
    @Override
    public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent event) {
        if(PlayerInfos.isExist(event.getMember().getId(), "discord_id", "user_rename")){
            event.getMember().modifyNickname(event.getOldNickname()).complete();
        }
    }
}

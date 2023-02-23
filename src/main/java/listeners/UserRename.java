package listeners;

import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildAuditLogEntryCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserRename extends ListenerAdapter {
    @Override
    public void onGuildAuditLogEntryCreate(GuildAuditLogEntryCreateEvent event) {
        if(event.getEntry().getType() == ActionType.MEMBER_UPDATE){
            Member punisher = event.getGuild().getMemberById(event.getEntry().getUserId());
            Member opfer = event.getGuild().getMemberById(event.getEntry().getTargetId());

            System.out.println(event.getEntry().getTargetId());

            if(!punisher.hasPermission(Permission.ADMINISTRATOR) || !punisher.getUser().isBot()){
                if (PlayerInfos.isExist(event.getEntry().getTargetId(), "discord_id", "user_rename")) {
                    opfer.modifyNickname(opfer.getUser().getName()).complete();
                }
            }
        }
    }
}

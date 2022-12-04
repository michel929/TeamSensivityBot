package karmasystem;

import mysql.KarmaSystem;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildMuteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PlayerMute extends ListenerAdapter {
    @Override
    public void onGuildVoiceGuildMute(GuildVoiceGuildMuteEvent event) {
        if(event.isGuildMuted()){
            event.getGuild().retrieveAuditLogs().type(ActionType.MEMBER_UPDATE).limit(1).queue(list -> {
                AuditLogEntry entry = list.get(0);

                if (!entry.getUser().isBot()) {
                    KarmaSystem.createEvent(entry.getUser().getId(), "ServerMute", event.getMember().getId(), -10);
                }
            });
        }else {
            event.getGuild().retrieveAuditLogs().type(ActionType.MEMBER_UPDATE).limit(1).queue(list -> {
                AuditLogEntry entry = list.get(0);

                if (!entry.getUser().isBot()) {
                    KarmaSystem.createEvent(entry.getUser().getId(), "ServerEndMute", event.getMember().getId(), 10);
                }
            });
        }
    }
}

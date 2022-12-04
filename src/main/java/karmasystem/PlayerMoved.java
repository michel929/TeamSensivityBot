package karmasystem;

import mysql.KarmaSystem;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.audit.AuditLogOption;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.LocalDateTime;

public class PlayerMoved extends ListenerAdapter {

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {

        if(event.getChannelLeft() != null){

        }

        if(event.getChannelLeft() != null && event.getChannelJoined() != null) {

            event.getGuild().retrieveAuditLogs().type(ActionType.MEMBER_VOICE_MOVE).limit(1).queue(list -> {
                AuditLogEntry entry = list.get(0);

                if (!entry.getUser().isBot()) {
                    Channel c = entry.getOption(AuditLogOption.CHANNEL);

                    if(c.getId().equals(event.getChannelJoined().getId())) {
                        LocalDateTime now = LocalDateTime.now();
                        KarmaSystem.addMove(entry.getUser().getId(), event.getMember().getId(), now.getSecond());
                    }
                }
            });
        }
    }
}

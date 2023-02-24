package commands;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import org.json.simple.parser.ParseException;

import java.util.List;

public class GetEmojis implements ServerCommand {
    @Override
    public void performCommand(Member m, TextChannel channel, Message message) throws ParseException {
        if(m.hasPermission(Permission.ADMINISTRATOR)) {
            Guild g = channel.getGuild();

            List<RichCustomEmoji> emojis = g.getEmojis();

            for (RichCustomEmoji e : emojis) {
                channel.sendMessage(e + "").setSuppressedNotifications(true).queue();
            }
        }
    }
}

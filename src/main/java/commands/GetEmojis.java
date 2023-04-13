package commands;

import commands.types.ServerCommand;
import main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.parser.ParseException;

import java.util.List;

public class GetEmojis implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) throws ParseException {
        if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            Guild g = event.getGuild();

            List<RichCustomEmoji> emojis = g.getEmojis();

            for (RichCustomEmoji e : emojis) {
                event.getChannel().sendMessage(e + "").setSuppressedNotifications(true).queue();
            }
        } else{
            event.getChannel().sendMessageEmbeds(Main.INSTANCE.getEmbedMessages().getNoPermission()).queue();
        }
    }
}

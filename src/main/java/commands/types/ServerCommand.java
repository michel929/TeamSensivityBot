package commands.types;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.json.simple.parser.ParseException;

public interface ServerCommand {
    public void performCommand(Member m, TextChannel channel, Message message) throws ParseException;
}

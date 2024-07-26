package types;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.parser.ParseException;

public interface ServerCommand {
    public void performCommand(MessageReceivedEvent event) throws ParseException;
}

package commands;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.parser.ParseException;

public class Shutdown implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) throws ParseException {
        if(event.getMember().getId().equals("422148236875137059")){
            event.getJDA().shutdownNow();
            System.exit(0);
        }
    }
}

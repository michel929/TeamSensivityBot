package commands.embeds;

import commands.types.ServerCommand;
import main.Main;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.json.simple.parser.ParseException;

import java.awt.*;

public class TicketCommand implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) throws ParseException {
        Member m = event.getMember();

        if(m.getId().equals("422148236875137059")){
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.decode("#2ecc71"));
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setTitle("TicketSystem");
            builder.setDescription("Hier kannst du ein Ticket öffnen, falls etwas mit deinem GameServer nicht funktioniert, oder du hilfe brauchst. Drück einfach unten den Button um ein neues Ticket zu erstellen.");

            event.getChannel().sendMessageEmbeds(builder.build()).addActionRow(Button.success("createTicket", "Erstelle ein Ticket")).queue();
        }else {
            event.getChannel().sendMessageEmbeds(Main.INSTANCE.getEmbedMessages().getNoPermission()).setSuppressedNotifications(true).queue();
        }
    }
}

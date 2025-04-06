package games.luck.commands;

import main.Start;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.json.simple.parser.ParseException;
import types.ServerCommand;

import java.awt.*;

public class WheelCommand implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) throws ParseException {
        if(event.getMember().hasPermission(Permission.ADMINISTRATOR)){
        EmbedBuilder wheel = new EmbedBuilder();
        wheel.setColor(Color.decode("#9914fa"));
        wheel.setThumbnail(BotInfos.getBotInfos("logo_url"));
        wheel.setTitle("Lucky Wheel");
        wheel.setImage("https://sensivity.team/bot/img/slot/übersicht.png");
        wheel.setDescription("Dreh das Rad und gewinne tolle Features wie XP und Rechte.");

        event.getChannel().sendMessageEmbeds(wheel.build()).addActionRow(Button.success("spin", "Drehen (500 Punkte)")).queue();

        }else {
            event.getChannel().sendMessageEmbeds(Start.INSTANCE.getEmbedMessages().getNoPermission()).queue();
        }
    }
}

package schachAPI.commands;

import schachAPI.EloEntity;
import schachAPI.Function;
import schachAPI.Schach;
import types.ServerCommand;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SchachLeaderBoard implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) throws ParseException {
        if(event.getMember().getId().equals("422148236875137059")){
            event.getChannel().sendMessageEmbeds(Function.createBoard().build()).addActionRow(Button.secondary("reload-board", "Reload"), Button.link("https://www.chess.com/club/team-sensivity", "Club beitreten")).queue();
        }
    }
}

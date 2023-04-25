package commands;

import commands.types.ServerCommand;
import main.Main;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.parser.ParseException;
import riot.RiotAPI;

import java.util.HashMap;

public class CollectLeagueGames implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) throws ParseException {
        if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            HashMap<String, String> league = PlayerInfos.getLeaguePuuids();
            RiotAPI.getMatches(league);
        } else {
            event.getChannel().sendMessageEmbeds(Main.INSTANCE.getEmbedMessages().getNoPermission()).queue();
        }
    }
}

package main.manager;

import commands.*;
import commands.embeds.CreateLobby;
import commands.embeds.PointsCommand;
import commands.embeds.anleitungen.ConnectTeamSensivityAccount;
import commands.embeds.anleitungen.SelectGames;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.parser.ParseException;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {
    public ConcurrentHashMap<String, ServerCommand> commands;

    public CommandManager(){
        this.commands = new ConcurrentHashMap<>();

        commands.put("update", new UpdateCommand());
        commands.put("dbdp", new DBDProfile());
        commands.put("reset", new ResetCommand());
        commands.put("clear", new ClearCommand());
        commands.put("points", new PointsCommand());
        commands.put("cl", new CreateLobby());
        commands.put("emoji", new GetEmojis());
        commands.put("consen", new ConnectTeamSensivityAccount());
        commands.put("selgam", new SelectGames());
        commands.put("fetchleague", new CollectLeagueGames());
    }

    public boolean perform(String command, MessageReceivedEvent event){

        ServerCommand cmd;
        if((cmd = this.commands.get(command.toLowerCase())) != null){
            try {
                cmd.performCommand(event);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
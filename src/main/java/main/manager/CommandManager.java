package main.manager;

import commands.*;
import commands.embeds.GameServerShop;
import commands.embeds.PointsCommand;
import commands.embeds.SchachLeaderBoard;
import commands.embeds.TicketCommand;
import commands.embeds.anleitungen.ConnectTeamSensivityAccount;
import commands.embeds.anleitungen.SelectGames;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.parser.ParseException;
import unendlichkeit.commands.Overview;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {
    public ConcurrentHashMap<String, ServerCommand> commands;

    public CommandManager(){
        this.commands = new ConcurrentHashMap<>();

        commands.put("update", new UpdateCommand());
        commands.put("reset", new ResetCommand());
        commands.put("clear", new ClearCommand());
        commands.put("points", new PointsCommand());
        commands.put("emoji", new GetEmojis());
        commands.put("consen", new ConnectTeamSensivityAccount());
        commands.put("selgam", new SelectGames());
        commands.put("admin", new ResetAdmin());
        commands.put("unendlichkeit", new Overview());
        commands.put("ticket", new TicketCommand());
        commands.put("shop", new GameServerShop());
        commands.put("shutdown", new Shutdown());
        commands.put("board", new SchachLeaderBoard());
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
package main.manager;

import admin.commands.ClearCommand;
import admin.commands.GetEmojis;
import admin.commands.ResetCommand;
import admin.commands.Shutdown;
import admin.commands.UpdateCommand;
import games.luck.commands.WheelCommand;
import hosting.commands.GameServerShop;
import pointsSystem.commands.PointsCommand;
import schachAPI.commands.SchachLeaderBoard;
import ticket.commands.TicketCommand;
import commands.embeds.anleitungen.ConnectTeamSensivityAccount;
import commands.embeds.anleitungen.SelectGames;
import types.ServerCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.parser.ParseException;

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
        commands.put("ticket", new TicketCommand());
        commands.put("shop", new GameServerShop());
        commands.put("shutdown", new Shutdown());
        commands.put("board", new SchachLeaderBoard());

        commands.put("wheel", new WheelCommand());
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
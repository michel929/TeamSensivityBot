package main.manager;

import pointsSystem.buttons.YourPoints;
import schachAPI.buttons.ReloadBoard;
import types.ServerButton;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import ticket.CreateTicket;
import watchRoom.buttons.WatchRoom;
import watchRoom.buttons.WatchRoomAccept;

import java.util.concurrent.ConcurrentHashMap;

public class ButtonManager {
    public ConcurrentHashMap<String, ServerButton> buttons;

    public ButtonManager() {
        this.buttons = new ConcurrentHashMap<>();

        buttons.put("showPoints", new YourPoints());
        buttons.put("watch", new WatchRoom());
        buttons.put("verstanden", new WatchRoomAccept());
        buttons.put("createTicket", new CreateTicket());
        buttons.put("reload-board", new ReloadBoard());
    }

    public boolean perform(String command, ButtonInteractionEvent event){

        ServerButton cmd = null;

        for (String s: buttons.keySet()) {
            if(command.contains(s)) {
                cmd = this.buttons.get(s);
            }
        }

        if(cmd != null){
            cmd.performCommand(event);
            return true;
        }
        return false;
    }
}

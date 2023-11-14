package main.manager;

import buttons.Revive;
import buttons.WatchRoom;
import buttons.WatchRoomAccept;
import buttons.YourPoints;
import buttons.types.ServerButton;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import ticket.CreateTicket;

import java.util.concurrent.ConcurrentHashMap;

public class ButtonManager {
    public ConcurrentHashMap<String, ServerButton> buttons;

    public ButtonManager() {
        this.buttons = new ConcurrentHashMap<>();

        buttons.put("showPoints", new YourPoints());
        buttons.put("revive", new Revive());
        buttons.put("watch", new WatchRoom());
        buttons.put("verstanden", new WatchRoomAccept());
        buttons.put("createTicket", new CreateTicket());
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

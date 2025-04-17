package main.manager;

import pointsSystem.modals.MoveUser;
import pointsSystem.modals.RenameUser;
import types.ServerModal;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

import java.util.concurrent.ConcurrentHashMap;

public class ModalManager {
    public ConcurrentHashMap<String, ServerModal> modals;

    public ModalManager() {
        this.modals = new ConcurrentHashMap<>();

        modals.put("rename", new RenameUser());
        modals.put("move", new MoveUser());
    }

    public boolean perform(String command, ModalInteractionEvent event){

        ServerModal cmd = null;

        for (String s: modals.keySet()) {
            if(command.contains(s)) {
                cmd = this.modals.get(s);
            }
        }

        if(cmd != null){
            cmd.performCommand(event);
            return true;
        }
        return false;
    }
}

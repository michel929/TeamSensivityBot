package main.manager;

import modals.ConnectMinecraft;
import modals.RenameUser;
import modals.type.ServerModal;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import pets.modals.Name;

import java.util.concurrent.ConcurrentHashMap;

public class ModalManager {
    public ConcurrentHashMap<String, ServerModal> modals;

    public ModalManager() {
        this.modals = new ConcurrentHashMap<>();

        modals.put("rename", new RenameUser());
        modals.put("minecraft", new ConnectMinecraft());
        modals.put("haustier", new Name());
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

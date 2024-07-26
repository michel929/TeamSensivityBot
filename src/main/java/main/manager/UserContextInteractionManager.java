package main.manager;
import pointsSystem.contextInteraction.KickUserForPoints;
import pointsSystem.contextInteraction.MuteUserForPoints;
import pointsSystem.contextInteraction.RenameUserForPoints;
import admin.contextInteraction.UpdateUser;
import types.UserContextInteraction;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

import java.util.concurrent.ConcurrentHashMap;

public class UserContextInteractionManager {

        public ConcurrentHashMap<String, UserContextInteraction> userContext;

        public UserContextInteractionManager() {
            this.userContext = new ConcurrentHashMap<>();

            userContext.put("Create TeamSensivity Account", new UpdateUser());
            userContext.put("Muten (1000 Points)", new MuteUserForPoints());
            userContext.put("Kick (1000 Points)", new KickUserForPoints());
            userContext.put("Rename (500 Points)", new RenameUserForPoints());
        }

        public boolean perform(String command, UserContextInteractionEvent event){

            UserContextInteraction cmd;
            if((cmd = this.userContext.get(command)) != null){
                cmd.performCommand(event);
                return true;
            }
            return false;
        }

}

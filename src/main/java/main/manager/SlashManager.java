package main.manager;
import dashboard.connect.slash.Connect;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import pointsSystem.slash.Daily;
import pointsSystem.slash.Punkte;
import slash.*;
import slash.dashboard.*;
import types.ServerSlash;

import java.util.concurrent.ConcurrentHashMap;

public class SlashManager {
        public ConcurrentHashMap<String, ServerSlash> slashs;

        public SlashManager() {
            this.slashs = new ConcurrentHashMap<>();

            slashs.put("login", new Login());
            slashs.put("revoke", new Revoke());
            slashs.put("bday", new Bday());
            slashs.put("lock", new LockUsername());

            slashs.put("connect", new Connect());

            slashs.put("points", new Punkte());
            slashs.put("daily", new Daily());
        }

        public boolean perform(String command, SlashCommandInteractionEvent event){

            ServerSlash cmd;
            if((cmd = this.slashs.get(command.toLowerCase())) != null){
                    cmd.performCommand(event);
                return true;
            }
            return false;
        }
}

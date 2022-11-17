package main;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import slash.Connect;
import slash.Login;
import slash.SWF;
import slash.Token;
import slash.types.ServerSlash;

import java.util.concurrent.ConcurrentHashMap;

public class SlashManager {
        public ConcurrentHashMap<String, ServerSlash> slashs;

        public SlashManager() {
            this.slashs = new ConcurrentHashMap<>();

            slashs.put("login", new Login());
            slashs.put("connect", new Connect());
            slashs.put("swf", new SWF());
            slashs.put("token", new Token());
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

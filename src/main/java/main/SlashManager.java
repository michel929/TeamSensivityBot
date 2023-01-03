package main;
import music.slash.Play;
import music.slash.Stop;
import music.slash.Volume;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import slash.*;
import dbd.swf.slash.SWF;
import slash.dashboard.*;
import slash.types.ServerSlash;

import java.util.concurrent.ConcurrentHashMap;

public class SlashManager {
        public ConcurrentHashMap<String, ServerSlash> slashs;

        public SlashManager() {
            this.slashs = new ConcurrentHashMap<>();

            slashs.put("login", new Login());
            slashs.put("connect", new Connect());
            slashs.put("token", new Token());
            slashs.put("revoke", new Revoke());

            slashs.put("steam", new ConnectSteam());
            slashs.put("riot", new ConnectRiot());

            slashs.put("swf", new SWF());
            slashs.put("points", new Punkte());
            slashs.put("daily", new Daily());

            slashs.put("play", new Play());
            slashs.put("volume", new Volume());
            slashs.put("stop", new Stop());
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

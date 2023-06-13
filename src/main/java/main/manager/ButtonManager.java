package main.manager;

import buttons.Revive;
import buttons.WatchRoom;
import buttons.WatchRoomAccept;
import buttons.YourPoints;
import dbd.swf.buttons.SWFJoin;
import dbd.swf.buttons.SWFLeave;
import dbd.swf.buttons.SWFYes;
import buttons.types.ServerButton;
import games.lobby.buttons.*;
import games.lobby.buttons.einsatz.Einsatz100Jack;
import games.lobby.buttons.einsatz.Einsatz1000Jack;
import games.lobby.buttons.einsatz.Einsatz500Jack;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import pets.buttons.*;

import java.util.concurrent.ConcurrentHashMap;

public class ButtonManager {
    public ConcurrentHashMap<String, ServerButton> buttons;

    public ButtonManager() {
        this.buttons = new ConcurrentHashMap<>();

        buttons.put("swfjoin", new SWFJoin());
        buttons.put("swfleave", new SWFLeave());
        buttons.put("swfyes", new SWFYes());
        buttons.put("showPoints", new YourPoints());
        buttons.put("revive", new Revive());
        buttons.put("watch", new WatchRoom());
        buttons.put("verstanden", new WatchRoomAccept());

        //Pets
        buttons.put("tier-shop", new BuyTier());
        buttons.put("animalshop", new BuyAnimal());
        buttons.put("kaufen", new Kaufen());
        buttons.put("food", new Food());
        buttons.put("next", new Next());
        buttons.put("pre", new Pre());

        //GameLobby
        buttons.put("start-game", new StartLobby());
        buttons.put("startGameNow", new StartGame());
        buttons.put("joinLobby", new JoinLobby());
        buttons.put("leaveOld", new LeaveLobby());
        buttons.put("100e", new Einsatz100Jack());
        buttons.put("500e", new Einsatz500Jack());
        buttons.put("1000e", new Einsatz1000Jack());
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

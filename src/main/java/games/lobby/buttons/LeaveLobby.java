package games.lobby.buttons;

import buttons.types.ServerButton;
import games.Player;
import games.lobby.Lobby;
import main.Main;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

public class LeaveLobby implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        ConcurrentHashMap<Member, Lobby> lobby = Main.INSTANCE.getSelectSave().getLobby();

        boolean contains = false;

        for (Lobby l : lobby.values()) {
            for (Player p : l.getPlayer()) {
                if (p.getM() == event.getMember()) {
                    l.removePlayer(event.getMember());
                    contains = true;
                }
            }

            if (l.getHost().equals(event.getMember().getId())) {
                if(l.getPlayer().size() == 0){
                    lobby.remove(l);
                    l.getChannel().delete().queue();
                }else {
                    l.setHost(l.getPlayer().get(0).getM().getId());
                    l.getPlayer().remove(0);
                }
                contains = true;
            }
        }

        if (!contains) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Fehler beim Verlassen der Gruppe!");
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setColor(Color.red);
            builder.setDescription("Du bist nicht in dieser Lobby deswegen kannst du Sie auch nicht verlassen.");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }

        System.out.println(lobby.size());
    }
}

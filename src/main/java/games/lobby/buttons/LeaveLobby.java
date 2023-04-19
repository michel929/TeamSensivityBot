package games.lobby.buttons;

import buttons.types.ServerButton;
import games.Player;
import games.lobby.Lobby;
import main.Main;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.awt.*;
import java.util.ArrayList;

public class LeaveLobby implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        ArrayList<Lobby> lobby = Main.INSTANCE.getSelectSave().getLobby();

        boolean contains = false, remove = false;
        Lobby lobby1 = null;

        for (Lobby l : lobby) {
            for (Player p : l.getPlayer()) {
                if (p.getM() == event.getMember()) {
                    l.removePlayer(event.getMember());
                    contains = true;
                    lobby1 = l;
                }
            }

            if (l.getHost().equals(event.getMember().getId())) {
                if(l.getPlayer().size() == 0){
                    lobby1 = l;
                    l.getChannel().delete().queue();
                }else {
                    l.setHost(l.getPlayer().get(0).getM().getId());
                    l.removePlayer(event.getMember());
                }
                contains = true;
                lobby1 = l;
            }
        }

        if(remove){
            lobby.remove(lobby1);
        }

        if (!contains) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Fehler beim Verlassen der Gruppe!");
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setColor(Color.red);
            builder.setDescription("Du bist nicht in dieser Lobby deswegen kannst du Sie auch nicht verlassen.");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }else {
            lobby1.addPlayer(event.getMember());

            EmbedBuilder builder = new EmbedBuilder();
            builder.setDescription("Spiele jetzt mit " + event.getGuild().getMemberById(lobby1.getHost()).getAsMention() + "das Spiel seiner Wahl.");
            builder.setColor(Color.decode("#9914fa"));
            builder.setTitle("GameLobby");
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setFooter((lobby1.getPlayer().size() + 1) + " Player in der Lobby");

            Lobby finalLobby = lobby1;
            lobby1.getMessageId().editMessageEmbeds(builder.build()).queue(message -> {
                finalLobby.setMessageId(message);
            });
        }
    }
}

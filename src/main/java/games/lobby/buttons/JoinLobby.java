package games.lobby.buttons;

import buttons.types.ServerButton;
import games.Player;
import games.lobby.Lobby;
import main.Main;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.ArrayList;

public class JoinLobby implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        if(PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")){
            String lobbyid = event.getId().replace("startGameNow", "");
            System.out.println(lobbyid);
            ArrayList<Lobby> lobby = Main.INSTANCE.getSelectSave().getLobby();
            boolean contains = false;
            Lobby lobby1 = null;


            for (Lobby l: lobby) {
                if(l.getId().equals(lobbyid)){
                    lobby1 = l;
                }

                for (Player p : l.getPlayer()) {
                    if (p.getM() == event.getMember()) {
                        contains = true;
                    }
                }

                if(l.getHost().equals(event.getMember().getId())){
                    contains = true;
                }
            }

            if(contains){
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(Color.red);
                embedBuilder.setDescription("Du bist bereits in einer GameLobby... Möchtest du die alte Lobby verlassen und dafür der Neuen beitreten?");
                embedBuilder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                embedBuilder.setTitle("Fehler beim beitreten der Lobby");

                event.replyEmbeds(embedBuilder.build()).addActionRow(Button.danger("leaveOld", "Alte Lobby verlassen")).setEphemeral(true).queue();
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
        }else {
            event.replyEmbeds(Main.INSTANCE.getEmbedMessages().getNoAccount()).setEphemeral(true).queue();
        }
    }
}

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
import java.util.UUID;

public class StartLobby implements ServerButton {

    @Override
    public void performCommand(ButtonInteractionEvent event) {
        if(PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")){
            if(Main.INSTANCE.getSelectSave().isInList(event.getMember().getId())){
                startGame(event, Main.INSTANCE.getSelectSave().getValue(event.getMember().getId()));
            }else {
                startGame(event, "jack");
            }
        }else {
            event.replyEmbeds(Main.INSTANCE.getEmbedMessages().getNoAccount()).setEphemeral(true).queue();
        }
    }

    private void startGame(ButtonInteractionEvent event, String game){
        ArrayList<Lobby> lobby = Main.INSTANCE.getSelectSave().getLobby();

        boolean contains = false;

        for (Lobby l: lobby) {
            for (Player p : l.getPlayer()) {
                if(p.getM() == event.getMember()){
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
            lobby.add(new Lobby(UUID.randomUUID().toString(), game, event.getMember().getId()));
            Lobby l = lobby.get(lobby.size() - 1);

            if(game.equals("jack")) {
                event.getGuild().getCategoryById("1097807839315107900").createTextChannel("Black Jack").queue(channel -> {
                    l.setChannel(channel);

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setDescription("Spiele jetzt mit " + event.getMember().getAsMention() + "das Spiel seiner Wahl.");
                    builder.setColor(Color.decode("#9914fa"));
                    builder.setTitle("GameLobby");
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setFooter("1 Player in der Lobby");

                    channel.sendMessageEmbeds(builder.build()).setActionRow(Button.success("joinLobby" + l.getId(), "Join Lobby"), Button.primary("startGameNow" + l.getId(), "Starte das Game"), Button.danger("leaveOld", "Verlasse die Lobby")).queue(message -> {
                        l.setMessageId(message);
                    });
                });
            }

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Du hast eine Lobby erstellt!");
            embedBuilder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            embedBuilder.setColor(Color.decode("#2ecc71"));
            embedBuilder.setDescription("Du hast eine Lobby erstellt und bist der Host des Games.");

            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        }
    }
}

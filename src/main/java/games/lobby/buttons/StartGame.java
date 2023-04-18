package games.lobby.buttons;

import buttons.types.ServerButton;
import games.Player;
import games.lobby.Lobby;
import main.Main;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class StartGame implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        Member m = Main.INSTANCE.getGuild().getMemberById(event.getId().replace("startGameNow", ""));
        Lobby l = Main.INSTANCE.getSelectSave().getLobby().get(m);

        if((l.getPlayer().size() + 1) >= l.getMinPlayer()){
            if(l.getGame().equals("jack")){
                startJack(l);
            }
        }
    }


    private void startJack(Lobby lobby){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("BlackJack");
        builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
        builder.setColor(Color.decode("#9914fa"));
        builder.setDescription("Wählt euren Einsatz, wenn ihr gewinnt bekommt ihr das 4-Fache wieder. Bei einem Unentschieden bekommt ihr euren Einsatz zurück.");
        String einsatz = "";
        for (Player p: lobby.getPlayer()) {
            einsatz = einsatz + p.getM().getAsMention() + ": " + p.getEinsatz() + " ";
        }

        builder.setFooter(einsatz);

        lobby.getMessageId().editMessageEmbeds(builder.build()).setActionRow(Button.secondary("100e" + lobby.getId(), "100 Points"), Button.secondary("500e" + lobby.getId(), "500 Points"), Button.secondary("1000e" + lobby.getId(), "1000 Points")).queue(message -> {
            lobby.setMessageId(message);
        });
    }
}

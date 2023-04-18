package games.lobby.buttons.einsatz;

import buttons.types.ServerButton;
import games.Player;
import games.blackjack.StartJack;
import games.lobby.Lobby;
import main.Main;
import mysql.BotInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.awt.*;

public class Einsatz1000Jack implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        Member m = Main.INSTANCE.getGuild().getMemberById(event.getId().replace("1000e", ""));
        Lobby l = Main.INSTANCE.getSelectSave().getLobby().get(m);

        boolean contains = false, everyone = true;

        for (Player p : l.getPlayer()) {
            if(p.getM() == event.getMember()){
                contains = true;
                if(p.getEinsatz() == 0) {
                    p.setEinsatz(1000);

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Du hast deinen Ensatz gewählt!");
                    builder.setColor(Color.decode("#9914fa"));
                    builder.setDescription("Du hast deinen Einsatz auf 1000 Points gesetzt.");
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));

                    event.replyEmbeds(builder.build()).setEphemeral(true).queue();

                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle("BlackJack");
                    embedBuilder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    embedBuilder.setColor(Color.decode("#9914fa"));
                    embedBuilder.setDescription("Wählt euren Einsatz, wenn ihr gewinnt bekommt ihr das 4-Fache wieder. Bei einem Unentschieden bekommt ihr euren Einsatz zurück.");
                    String einsatz = "";
                    for (Player player : l.getPlayer()) {
                        einsatz = einsatz + player.getM().getAsMention() + ": " + player.getEinsatz() + " ";

                        if (player.getEinsatz() == 0) {
                            everyone = false;
                        }
                    }

                    embedBuilder.setFooter(einsatz);

                    l.getMessageId().editMessageEmbeds(embedBuilder.build()).queue(message -> {
                        l.setMessageId(message);
                    });
                }else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setDescription("Du hast bereits deinen Einsatz festgelegt.");
                    builder.setTitle("Fehler beim Auswählen des Einsatzes!");
                    builder.setColor(Color.red);

                    event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                }
            }
        }

        if(everyone){
            //RUNDE STARTET
            for (Player p : l.getPlayer()) {
                PunkteSystem.uploadPoints(p.getM().getId(), - p.getEinsatz());
                PunkteSystem.upload(event.getMember().getId(), p.getEinsatz(), 0, "Einsatz für BlackJack.");
            }

            StartJack.start(l);
        }

        if(!contains){
            EmbedBuilder builder = new EmbedBuilder();
            builder.setDescription("Du bist kein Teilnehmer in diesem Game.");
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setColor(Color.red);
            builder.setTitle("Fehler beim Auswählen des Einsatzes!");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }
    }
}

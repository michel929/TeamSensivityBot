package buttons;

import SchachAPI.EloEntity;
import SchachAPI.Schach;
import buttons.types.ServerButton;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReloadBoard implements ServerButton {

    @Override
    public void performCommand(ButtonInteractionEvent event) {

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("Chess Leaderboard");
        builder.setColor(Color.decode("#9914fa"));
        builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
        builder.setDescription("Du möchtest auch im Leaderboard erscheinen, dann tritt einfach dem **Team Sensivity Club** auf Chess.com bei. Benutze dafür einfach unten den Join Button.");

        ArrayList<String> users = Schach.getUser();

        ArrayList<EloEntity> ChessUserPoints = new ArrayList<>();

        for(int i = 0; i < users.size(); i++){

            if(!users.get(i).equals("michel_929")) {

                long points = Schach.getUserPoints(users.get(i));

                ChessUserPoints.add(new EloEntity(points, users.get(i)));

            }
        }

        Collections.sort(ChessUserPoints, new Comparator<EloEntity>() {
            @Override
            public int compare(EloEntity s1, EloEntity s2) {
                return Long.compare(s1.getElo(), s2.getElo());
            }
        });

        int f = 1;

        for(int i = 4 - 1; i >= 0; i--){
            builder.addField("Platz " + f + ":" , "**" + ChessUserPoints.get(i).getName() + "** | " + ChessUserPoints.get(i).getElo() + " ELO", false);

            f++;
        }

        event.editMessageEmbeds(builder.build()).queue();
    }
}

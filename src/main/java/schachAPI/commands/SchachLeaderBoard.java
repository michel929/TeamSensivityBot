package schachAPI.commands;

import schachAPI.EloEntity;
import schachAPI.Schach;
import types.ServerCommand;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SchachLeaderBoard implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) throws ParseException {
        if(event.getMember().getId().equals("422148236875137059")){

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

            event.getChannel().sendMessageEmbeds(builder.build()).addActionRow(Button.secondary("reload-board", "Reload"), Button.link("https://www.chess.com/club/team-sensivity", "Club beitreten")).queue();
        }
    }
}

package commands;

import commands.types.ServerCommand;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.json.simple.parser.ParseException;

import java.awt.*;

public class PointsCommand implements ServerCommand {
    @Override
    public void performCommand(Member m, TextChannel channel, Message message) throws ParseException {
        if(m.hasPermission(Permission.ADMINISTRATOR)){
            EmbedBuilder builder = new EmbedBuilder();

            builder.setColor(Color.decode("#2ecc71"));
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setTitle("PunkteSystem");
            builder.setDescription("Hier findest du alle Informationen zu deinem Punktestand, etc.");

            channel.sendMessageEmbeds(builder.build()).addActionRow(Button.primary("showPoints", "Dein Punktestand"), Button.link("https://sensivity.team/points.php", "Rangliste")).queue();
        }
    }
}

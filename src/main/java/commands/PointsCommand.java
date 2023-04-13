package commands;

import commands.types.ServerCommand;
import main.Main;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.json.simple.parser.ParseException;

import java.awt.*;

public class PointsCommand implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) throws ParseException {
        if(event.getMember().hasPermission(Permission.ADMINISTRATOR)){
            EmbedBuilder builder = new EmbedBuilder();

            builder.setColor(Color.decode("#2ecc71"));
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setTitle("PunkteSystem");
            builder.setDescription("Hier findest du alle Informationen zu deinem Punktestand, etc.");

            event.getChannel().sendMessageEmbeds(builder.build()).addActionRow(Button.success("showPoints", "Dein Punktestand"), Button.link("https://sensivity.team/points.php", "Rangliste")).queue();
        }else {
            event.getChannel().sendMessageEmbeds(Main.INSTANCE.getEmbedMessages().getNoPermission()).queue();
        }
    }
}

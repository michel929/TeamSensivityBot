package commands;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.awt.*;

public class DBDProfile implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) {
        if(event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor("Team Sensivity");
            builder.setColor(Color.decode("#2ecc71"));
            builder.setThumbnail("https://sensivity.team/bot/img/dbd_logo.jpg");
            builder.setDescription("Hier kannst du dein DBD Profil bearbeiten...");

            event.getChannel().sendMessageEmbeds(builder.build()).queue();
            event.getChannel().sendMessage("Wähle deine MainRole:").setActionRow(dropdown1()).queue();
        }
    }

    private static StringSelectMenu dropdown1(){
        return StringSelectMenu.create("dbd_role")
                .setPlaceholder("Select your Role")
                .addOption("Survivor", "survivor")
                .addOption("Killer", "killer")
                .setRequiredRange(1, 1)
                .build();
    }
}

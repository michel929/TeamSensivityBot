package slash;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import slash.types.ServerSlash;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Punkte implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {

        if(event.getSubcommandName() != null) {
            if(event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (event.getSubcommandName().equals("add")) {
                    Member m = event.getOption("Member").getAsMember();
                    //TODO add Points
                } else if (event.getSubcommandName().equals("remove")) {
                    Member m = event.getOption("Member").getAsMember();
                    //TODO remove Points
                } else if (event.getSubcommandName().equals("set")) {
                    Member m = event.getOption("Member").getAsMember();
                    //TODO set Points
                }
            }
        }else {
            if (event.getOption("Member").getAsMember() != null) {
                if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    Member m = event.getOption("Member").getAsMember();

                    if (PlayerInfos.isExist(m.getId(), "discord_id", "users")) {
                        int punkte = Integer.parseInt(PlayerInfos.getInfo(m.getId(), "discord_id", "points", "users"));

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle(m.getEffectiveName() + "s Punkte");
                        builder.setDescription("Der User hat **" + punkte + " Punkte**.");
                        builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                        builder.setColor(Color.decode("#2ecc71"));

                        event.getChannel().sendMessageEmbeds(builder.build()).queue();
                    } else {
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("Keinen Account!");
                        builder.setDescription("Der Member hat kein Team Sensivity Account.");
                        builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                        builder.setColor(Color.RED);

                        event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                            message.delete().queueAfter(10, TimeUnit.SECONDS);
                        });
                    }
                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Keine Berechtigung dafür!");
                    builder.setDescription("Du hast keine Berechtigung die Punkte von anderen Spielern abzufragen.");
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setColor(Color.RED);

                    event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                        message.delete().queueAfter(10, TimeUnit.SECONDS);
                    });
                }
            } else {
                if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {
                    int punkte = Integer.parseInt(PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "points", "users"));

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle(event.getMember().getEffectiveName() + "s Punkte");
                    builder.setDescription("Der User hat **" + punkte + " Punkte**.");
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setColor(Color.decode("#2ecc71"));

                    event.getChannel().sendMessageEmbeds(builder.build()).queue();
                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Keinen Account!");
                    builder.setDescription("Der Member hat kein Team Sensivity Account.");
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setColor(Color.RED);

                    event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                        message.delete().queueAfter(10, TimeUnit.SECONDS);
                    });
                }
            }
        }
    }
}

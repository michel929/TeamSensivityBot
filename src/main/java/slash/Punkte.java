package slash;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
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
        if (BotInfos.getBotInfos("cmd_points_on").equals("1")) {
            if (event.getSubcommandName() != null) {
                if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    if (event.getSubcommandName().equals("add")) {
                        Member m = event.getOption("Member").getAsMember();
                        PunkteSystem.uploadPoints(m.getId(), event.getOption("Punkte").getAsInt());

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle(m.getEffectiveName() + "s Punkte");
                        builder.setDescription("Du hast diesem User " + event.getOption("Punkte").getAsInt() + " Punkte hinzugefügt.");
                        builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                        builder.setColor(Color.decode("#2ecc71"));

                        event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                            message.delete().queueAfter(10, TimeUnit.SECONDS);
                        });

                    } else if (event.getSubcommandName().equals("remove")) {
                        Member m = event.getOption("Member").getAsMember();
                        if (PunkteSystem.getPoints(m.getId()) - event.getOption("Punkte").getAsInt() <= 0) {
                            PunkteSystem.uploadPoints(m.getId(), -PunkteSystem.getPoints(m.getId()));
                        } else {
                            PunkteSystem.uploadPoints(m.getId(), -event.getOption("Punkte").getAsInt());
                        }

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle(m.getEffectiveName() + "s Punkte");
                        builder.setDescription("Du hast diesem User " + event.getOption("Punkte").getAsInt() + " Punkte abgezogen.");
                        builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                        builder.setColor(Color.decode("#2ecc71"));

                        event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                            message.delete().queueAfter(10, TimeUnit.SECONDS);
                        });

                    } else if (event.getSubcommandName().equals("set")) {
                        Member m = event.getOption("Member").getAsMember();
                        if (event.getOption("Punkte").getAsInt() <= 0) {
                            PunkteSystem.uploadPoints(m.getId(), -PunkteSystem.getPoints(m.getId()));
                        } else {
                            PunkteSystem.uploadPoints(m.getId(), event.getOption("Punkte").getAsInt() - PunkteSystem.getPoints(m.getId()));
                        }

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle(m.getEffectiveName() + "s Punkte");
                        builder.setDescription("Du hast dem User seinen Punktestand auf " + event.getOption("Punkte").getAsInt() + " Punkte gesetzt.");
                        builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                        builder.setColor(Color.decode("#2ecc71"));

                        event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                            message.delete().queueAfter(10, TimeUnit.SECONDS);
                        });
                    }
                }
            } else {
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
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.red);
            builder.setDescription("Dieser Befehl ist zurzeit deaktiviert. Versuche es später erneut.");
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setTitle("Befel ist deaktiviert.");

            event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                message.delete().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }
}

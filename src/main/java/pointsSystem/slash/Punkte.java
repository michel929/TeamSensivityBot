package pointsSystem.slash;

import functions.GetInfos;
import main.Start;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import types.ServerSlash;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Punkte implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if (BotInfos.getBotInfos("cmd_points_on").equals("1")) {
                if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    if(!event.getMember().getId().equals("181090908572221450")) {
                        if (event.getSubcommandName().equals("add")) {
                            Member m = event.getOption("member").getAsMember();
                            PunkteSystem.uploadPoints(m.getId(), event.getOption("punkte").getAsInt());

                            EmbedBuilder builder = new EmbedBuilder();

                            builder.setTitle(m.getEffectiveName() + "s Punkte");
                            builder.setDescription("Du hast diesem User " + event.getOption("punkte").getAsInt() + " Punkte hinzugefügt.");
                            builder.setThumbnail(PlayerInfos.getInfo(m.getId(), "discord_id", "discord_pb", "users"));
                            builder.setColor(Color.decode("#2ecc71"));

                            if (!PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "discord_token", "users").equals("0")) {
                                String url = "https://dashboard.sensivity.team/connect/discord/update-points.php?discord_id=" + m.getId();
                                String url2 = "https://dashboard.sensivity.team/connect/discord/refresh.php?id=" + m.getId();
                                try {
                                    if (GetInfos.getPoints(new URL(url)).contains("Unauthorized")) {
                                        GetInfos.streamBOT(new URL(url2));
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }

                            event.replyEmbeds(builder.build()).addActionRow(Button.link("https://sensivity.team/points.php", "PunkteSystem")).setEphemeral(true).queue();
                        } else if (event.getSubcommandName().equals("remove")) {
                            Member m = event.getOption("member").getAsMember();
                            if (PunkteSystem.getPoints(m.getId()) - event.getOption("punkte").getAsInt() <= 0) {
                                PunkteSystem.uploadPoints(m.getId(), -PunkteSystem.getPoints(m.getId()));
                            } else {
                                PunkteSystem.uploadPoints(m.getId(), -event.getOption("punkte").getAsInt());
                            }

                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle(m.getEffectiveName() + "s Punkte");
                            builder.setDescription("Du hast diesem User " + event.getOption("punkte").getAsInt() + " Punkte abgezogen.");
                            builder.setThumbnail(PlayerInfos.getInfo(m.getId(), "discord_id", "discord_pb", "users"));
                            builder.setColor(Color.decode("#2ecc71"));

                            if (!PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "discord_token", "users").equals("0")) {
                                String url = "https://dashboard.sensivity.team/connect/discord/update-points.php?discord_id=" + m.getId();
                                String url2 = "https://dashboard.sensivity.team/connect/discord/refresh.php?id=" + m.getId();
                                try {
                                    if (GetInfos.getPoints(new URL(url)).contains("Unauthorized")) {
                                        GetInfos.streamBOT(new URL(url2));
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }

                            event.replyEmbeds(builder.build()).addActionRow(Button.link("https://sensivity.team/points.php", "PunkteSystem")).setEphemeral(true).queue();


                        } else if (event.getSubcommandName().equals("set")) {
                            Member m = event.getOption("member").getAsMember();
                            if (event.getOption("punkte").getAsInt() <= 0) {
                                PunkteSystem.uploadPoints(m.getId(), -PunkteSystem.getPoints(m.getId()));
                            } else {
                                PunkteSystem.uploadPoints(m.getId(), event.getOption("punkte").getAsInt() - PunkteSystem.getPoints(m.getId()));
                            }

                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle(m.getEffectiveName() + "s Punkte");
                            builder.setDescription("Du hast dem User seinen Punktestand auf " + event.getOption("punkte").getAsInt() + " Punkte gesetzt.");
                            builder.setThumbnail(PlayerInfos.getInfo(m.getId(), "discord_id", "discord_pb", "users"));
                            builder.setColor(Color.decode("#2ecc71"));

                            if (!PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "discord_token", "users").equals("0")) {
                                String url = "https://dashboard.sensivity.team/connect/discord/update-points.php?discord_id=" + m.getId();
                                String url2 = "https://dashboard.sensivity.team/connect/discord/refresh.php?id=" + m.getId();
                                try {
                                    if (GetInfos.getPoints(new URL(url)).contains("Unauthorized")) {
                                        GetInfos.streamBOT(new URL(url2));
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            event.replyEmbeds(builder.build()).addActionRow(Button.link("https://sensivity.team/points.php", "PunkteSystem")).setEphemeral(true).queue();
                        }
                    }else {
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setDescription("MAAAAX du Lump hören Sie auf zu Cheaten");
                        embedBuilder.setColor(Color.RED);
                        embedBuilder.setTitle("MAAAAX  STOOOPPPP!!!");
                        embedBuilder.setThumbnail(BotInfos.getBotInfos("logo_url"));

                        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                    }
                }
                    if (event.getSubcommandName().equals("info")) {
                        if (!event.getOptions().isEmpty()) {
                            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                                Member m = event.getOption("member").getAsMember();

                                if (PlayerInfos.isExist(m.getId(), "discord_id", "users")) {
                                    int punkte = Integer.parseInt(PlayerInfos.getInfo(m.getId(), "discord_id", "points", "users"));

                                    EmbedBuilder builder = new EmbedBuilder();
                                    builder.setTitle(m.getEffectiveName() + "s Punkte");
                                    builder.setDescription("Der User hat **" + punkte + " Punkte**.");
                                    builder.setThumbnail(PlayerInfos.getInfo(m.getId(), "discord_id", "discord_pb", "users"));
                                    builder.setColor(Color.decode("#2ecc71"));

                                    event.replyEmbeds(builder.build()).addActionRow(Button.link("https://sensivity.team/points.php", "PunkteSystem")).setEphemeral(true).queue();

                                } else {
                                    EmbedBuilder builder = new EmbedBuilder();
                                    builder.setTitle("Keinen Account!");
                                    builder.setDescription("Der Member hat kein Team Sensivity Account.");
                                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                                    builder.setColor(Color.RED);

                                    event.replyEmbeds(builder.build()).addActionRow(Button.link("https://sensivity.team/points.php", "PunkteSystem")).setEphemeral(true).queue();

                                }
                            } else {
                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setTitle("Keine Berechtigung dafür!");
                                builder.setDescription("Du hast keine Berechtigung die Punkte von anderen Spielern abzufragen.");
                                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                                builder.setColor(Color.RED);

                                event.replyEmbeds(builder.build()).setEphemeral(true).queue();

                            }
                        } else {
                            if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {
                                int punkte = Integer.parseInt(PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "points", "users"));

                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setTitle(event.getMember().getEffectiveName() + "s Punkte");
                                builder.setDescription("Du hast **" + punkte + " Punkte**.");
                                builder.setThumbnail(PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "discord_pb", "users"));
                                builder.setColor(Color.decode("#2ecc71"));

                                event.replyEmbeds(builder.build()).addActionRow(Button.link("https://sensivity.team/points.php", "PunkteSystem")).setEphemeral(true).queue();

                            } else {
                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setTitle("Keinen Account!");
                                builder.setDescription("Der Member hat kein Team Sensivity Account.");
                                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                                builder.setColor(Color.RED);

                                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                            }
                        }
                    }
        }else {
            event.replyEmbeds(Start.INSTANCE.getEmbedMessages().getNotActive()).setEphemeral(true).queue();
        }
    }
}

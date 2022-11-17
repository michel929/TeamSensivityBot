package slash;

import functions.CreateImage;
import functions.DBD_Chars;
import main.Start;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.FileUpload;
import slash.types.ServerSlash;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SWF implements ServerSlash {
    private File img;
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        Member m = event.getMember();

        if (BotInfos.getBotInfos("cmd_swf_on").equals("1")) {
            if (PlayerInfos.isExist(m.getId(), "discord_id", "users")) {
                if (PlayerInfos.getInfo(m.getId(), "discord_id", "survivor_main", "users") != null) {

                    event.getChannel().sendTyping().queue();
                    List<String> images = new ArrayList<>();
                    images.add("https://sensivity.team/bot/img/dbd_logo.png");
                    images.add("https://sensivity.team/bot/img/dbd_fog.png");

                    List<Member> members = new ArrayList<>();
                    members.add(m);
                    for (OptionMapping o : event.getOptions()) {
                        if (!o.getAsMember().getId().equals(event.getMember().getId())) {
                            members.add(o.getAsMember());
                        }
                    }
                    boolean fehler = false;
                    String uuid = UUID.randomUUID().toString();

                    if (members.size() > 1) {
                        for (Member mem : members) {
                            if (!PlayerInfos.isExist(mem.getId(), "discord_id", "users") || PlayerInfos.getInfo(m.getId(), "discord_id", "survivor_main", "users") == null) {

                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setColor(Color.decode("#2ecc71"));
                                builder.setDescription("Ein User für den du versucht hast einen Platz zu Reservieren, hat entweder keinen Team Sensivity Account oder hat sein DBD Profil noch nicht vervollständigt.");
                                builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
                                builder.setTitle("Fehler bei Benutzung des Commands");

                                event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                                    message.delete().queueAfter(10, TimeUnit.SECONDS);
                                });
                                fehler = true;
                            } else {
                                if (!mem.getId().equals(event.getMember().getId())) {
                                    Start.INSTANCE.getApi().openPrivateChannelById(mem.getId()).queue(privateChannel -> {
                                        EmbedBuilder builder = new EmbedBuilder();
                                        builder.setColor(Color.decode("#2ecc71"));
                                        builder.setDescription("Du wurdest eingeladen einer SWF zu joinen...");
                                        builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
                                        builder.setTitle("Einladung für eine SWF");

                                        privateChannel.sendMessageEmbeds(builder.build()).setActionRow(Button.success("swfyes" + uuid, "Join SWF")).queue();
                                    });
                                }
                            }
                        }
                    }
                    if (!fehler) {
                        EmbedBuilder builder = new EmbedBuilder();

                        if (members.size() == 1) {
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/grauneu.png");
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/dbd_chars/survivor/" + DBD_Chars.getDBDChar(m.getId()) + ".png");
                            builder.setDescription("**Player 1:** " + m.getAsMention() + DBD_Chars.getDBDRank(m.getId()) + " \n **Player 2:** NONE \n **Player 3:** NONE \n **Player 4:** NONE");
                        } else if (members.size() == 2) {
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/grauneu.png");
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/dbd_chars/survivor/" + DBD_Chars.getDBDChar(m.getId()) + ".png");
                            builder.setDescription("**Player 1:** " + m.getAsMention() + DBD_Chars.getDBDRank(m.getId()) + " \n **Player 2:** " + members.get(1).getAsMention() + DBD_Chars.getDBDRank(members.get(1).getId()) + " (Reserviert) \n **Player 3:** NONE \n **Player 4:** NONE");
                        } else if (members.size() == 3) {
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/grauneu.png");
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/dbd_chars/survivor/" + DBD_Chars.getDBDChar(m.getId()) + ".png");
                            builder.setDescription("**Player 1:** " + m.getAsMention() + DBD_Chars.getDBDRank(m.getId()) + " \n **Player 2:**" + members.get(1).getAsMention() + DBD_Chars.getDBDRank(members.get(1).getId()) + " (Reserviert) \n **Player 3:** " + members.get(2).getAsMention() + DBD_Chars.getDBDRank(members.get(2).getId()) + " (Reserviert) \n **Player 4:** NONE");
                        } else {
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/grauneu.png");
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/dbd_chars/survivor/" + DBD_Chars.getDBDChar(m.getId()) + ".png");
                            builder.setDescription("**Player 1:** " + m.getAsMention() + DBD_Chars.getDBDRank(m.getId()) + " \n **Player 2:** " + members.get(1).getAsMention() + DBD_Chars.getDBDRank(members.get(1).getId()) + " (Reserviert) \n **Player 3:** " + members.get(2).getAsMention() + DBD_Chars.getDBDRank(members.get(2).getId()) + " (Reserviert) \n **Player 4:** " + members.get(3).getAsMention() + DBD_Chars.getDBDRank(members.get(3).getId()) + " (Reserviert)");
                        }

                        images.add("https://sensivity.team/assets/images/sensivity/titel.png");
                        images.add("https://sensivity.team/bot/img/x.png");

                        try {
                            CreateImage.createImage(images);
                            img = new File("/home/michel929/TeamSensivity/findyourswf.png");
                            builder.setImage("attachment://FindYourSWF.png");
                            builder.setTitle("Find Your SWF");
                            builder.setColor(Color.decode("#2ecc71"));
                            builder.setAuthor(m.getEffectiveName(), "https://sensivity.team", m.getEffectiveAvatarUrl());
                            builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
                            event.getChannel().sendMessageEmbeds(builder.build()).addFiles(FileUpload.fromData(img, "FindYourSWF.png")).setActionRow(Button.success("swfjoin" + uuid, "Join SWF"), Button.danger("swfleave" + uuid, "Leave SWF"), Button.link("https://sensivity.team/swf?uuid=" + uuid, "About the Team")).queue(message -> {

                                if (members.size() == 1) {
                                    mysql.SWF.createSWF(uuid, m.getId(), message.getId());
                                } else if (members.size() == 2) {
                                    mysql.SWF.createSWF(uuid, m.getId(), members.get(1).getId(), message.getId());
                                } else if (members.size() == 3) {
                                    mysql.SWF.createSWF(uuid, m.getId(), members.get(1).getId(), members.get(2).getId(), message.getId());
                                } else {
                                    mysql.SWF.createSWF(uuid, m.getId(), members.get(1).getId(), members.get(2).getId(), members.get(3).getId(), message.getId());
                                }
                            });
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    System.out.println(m.getId());
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.red);
                    builder.setDescription("Du musst dein DBD_Profil vervollständigen. Entweder kannst du das im Dashboard bearbeiten oder du kannst es in dem dafür vorgesehenen DiscordChannel bearbeiten.");
                    builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
                    builder.setTitle("Fehler bei Benutzung des Commands");

                    event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                        message.delete().queueAfter(10, TimeUnit.SECONDS);
                    });
                }
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.red);
                builder.setDescription("Du brauchst einen TeamSensivity Account um diesen Command zu benutzen. Benutze */register* um dir einen Account anzulegen.");
                builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
                builder.setTitle("Fehler bei Benutzung des Commands");

                event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                    message.delete().queueAfter(10, TimeUnit.SECONDS);
                });
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

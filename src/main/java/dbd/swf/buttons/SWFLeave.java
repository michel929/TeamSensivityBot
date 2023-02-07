package dbd.swf.buttons;

import buttons.types.ServerButton;
import dbd.swf.Functions;
import functions.CreateImage;
import functions.DBD_Chars;
import main.Main;
import main.Start;
import mysql.SWF;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.FileUpload;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SWFLeave implements ServerButton {

    private File img;

    @Override
    public void performCommand(ButtonInteractionEvent event) {
        String uuid = event.getComponent().getId().replace("swfleave", "");

        boolean fool = false;

        int i = 0;
        for (String mem : SWF.getPlayer(uuid)) {
            if(mem == null){
                i++;
            }else {
                if (mem.equals(event.getMember().getId())) {
                    SWF.leaveSWF(uuid, event.getMember().getId());
                    fool = true;
                    i++;
                }
            }
        }

        if(i == SWF.getPlayer(uuid).size()){
            SWF.removeSWF(uuid);
            event.getMessage().delete().queue();
        }else {
            java.util.List<Member> members = new ArrayList<>();
            List<Member> reserviert = SWF.getReserviert(uuid);

            boolean fools = false;

            for (String mem : SWF.getPlayer(uuid)) {
                if (mem != null) {
                    if (mem.equals(event.getMember().getId())) {
                        fools = true;
                    }
                    members.add(Main.INSTANCE.getGuild().getMemberById(mem));
                }
            }

            for (Member m: members) {
                if(reserviert.contains(m)){
                    reserviert.remove(m);
                }
            }

            if (!fools) {
                    java.util.List<String> images = new ArrayList<>();
                    images.add("https://sensivity.team/bot/img/dbd_logo.png");
                    images.add("https://sensivity.team/bot/img/dbd_fog.png");

                    boolean fehler = false;

                    if (!fehler) {
                        Member m = members.get(0);
                        EmbedBuilder builder = new EmbedBuilder();
                        if (members.size() == 1) {
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/grauneu.png");
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/dbd_chars/survivor/" + DBD_Chars.getDBDChar(m.getId()) + ".png");

                            if(reserviert.size() == 0) {
                                builder.setDescription("**Player 1:** " + m.getAsMention() + DBD_Chars.getDBDRank(m.getId()) + " \n **Player 2:** NONE \n **Player 3:** NONE \n **Player 4:** NONE");
                            }else if(reserviert.size() == 1){
                                builder.setDescription("**Player 1:** " + m.getAsMention() + DBD_Chars.getDBDRank(m.getId()) + " \n **Player 2:** " + reserviert.get(0).getAsMention() + DBD_Chars.getDBDRank(reserviert.get(0).getId()) + " (Reserviert) \n **Player 3:** NONE \n **Player 4:** NONE");
                            }else if (reserviert.size() == 2){
                                builder.setDescription("**Player 1:** " + m.getAsMention() + DBD_Chars.getDBDRank(m.getId()) + " \n **Player 2:** " + reserviert.get(0).getAsMention() + DBD_Chars.getDBDRank(reserviert.get(0).getId()) + " (Reserviert) \n **Player 3:** " + reserviert.get(1).getAsMention() + DBD_Chars.getDBDRank(reserviert.get(1).getId()) + " (Reserviert) \n **Player 4:** NONE");
                            }else {
                                builder.setDescription("**Player 1:** " + m.getAsMention() + DBD_Chars.getDBDRank(m.getId()) + " \n **Player 2:** " + reserviert.get(0).getAsMention() + DBD_Chars.getDBDRank(reserviert.get(0).getId()) + " (Reserviert) \n **Player 3:** " + reserviert.get(1).getAsMention() + DBD_Chars.getDBDRank(reserviert.get(1).getId()) + " (Reserviert) \n **Player 4:** " + reserviert.get(2).getAsMention() + DBD_Chars.getDBDRank(reserviert.get(2).getId()) + " (Reserviert)");
                            }
                        } else if (members.size() == 2) {
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/grauneu.png");
                            images.add("https://sensivity.team/bot/img/dbd_chars/survivor/" + DBD_Chars.getDBDChar(members.get(1).getId()) + ".png");
                            images.add("https://sensivity.team/bot/img/dbd_chars/survivor/" + DBD_Chars.getDBDChar(m.getId()) + ".png");

                            if(reserviert.size() == 0){
                                builder.setDescription("**Player 1:** " + m.getAsMention() + DBD_Chars.getDBDRank(m.getId()) + " \n **Player 2:** " + members.get(1).getAsMention() + DBD_Chars.getDBDRank(members.get(1).getId()) + " \n **Player 3:** NONE \n **Player 4:** NONE");
                            }else if(reserviert.size() == 1){
                                builder.setDescription("**Player 1:** " + m.getAsMention() + DBD_Chars.getDBDRank(m.getId()) + " \n **Player 2:** " + members.get(1).getAsMention() + DBD_Chars.getDBDRank(members.get(1).getId()) + " \n **Player 3:** " + reserviert.get(0).getAsMention() + DBD_Chars.getDBDRank(reserviert.get(0).getId()) + " (Reserviert) \n **Player 4:** NONE");
                            }else if (reserviert.size() == 2){
                                builder.setDescription("**Player 1:** " + m.getAsMention() + DBD_Chars.getDBDRank(m.getId()) + " \n **Player 2:** " + members.get(1).getAsMention() + DBD_Chars.getDBDRank(members.get(1).getId()) + " \n **Player 3:** " + reserviert.get(0).getAsMention() + DBD_Chars.getDBDRank(reserviert.get(0).getId()) + " (Reserviert) \n **Player 4:** " + reserviert.get(1).getAsMention() + DBD_Chars.getDBDRank(reserviert.get(1).getId()) + " (Reserviert)");
                            }
                        } else if (members.size() == 3) {
                            images.add("https://sensivity.team/bot/img/unknown.png");
                            images.add("https://sensivity.team/bot/img/dbd_chars/survivor/" + DBD_Chars.getDBDChar(members.get(2).getId()) + ".png");
                            images.add("https://sensivity.team/bot/img/grauneu.png");
                            images.add("https://sensivity.team/bot/img/dbd_chars/survivor/" + DBD_Chars.getDBDChar(members.get(1).getId()) + ".png");
                            images.add("https://sensivity.team/bot/img/dbd_chars/survivor/" + DBD_Chars.getDBDChar(m.getId()) + ".png");

                            if(reserviert.size() == 0){
                                builder.setDescription("**Player 1:** " + m.getAsMention() + DBD_Chars.getDBDRank(m.getId()) + " \n **Player 2:**" + members.get(1).getAsMention() + DBD_Chars.getDBDRank(members.get(1).getId()) + " \n **Player 3:** " + members.get(2).getAsMention() + DBD_Chars.getDBDRank(members.get(2).getId()) + " \n **Player 4:** NONE");
                            }else if(reserviert.size() == 1){
                                builder.setDescription("**Player 1:** " + m.getAsMention() + DBD_Chars.getDBDRank(m.getId()) + " \n **Player 2:**" + members.get(1).getAsMention() + DBD_Chars.getDBDRank(members.get(1).getId()) + " \n **Player 3:** " + members.get(2).getAsMention() + DBD_Chars.getDBDRank(members.get(2).getId()) + " \n **Player 4:** " + reserviert.get(0).getAsMention() + DBD_Chars.getDBDRank(reserviert.get(0).getId()) + " (Reserviert)");
                            }
                        } else {
                            images.add("https://sensivity.team/bot/img/dbd_chars/survivor/" + DBD_Chars.getDBDChar(members.get(3).getId()) + ".png");
                            images.add("https://sensivity.team/bot/img/dbd_chars/survivor/" + DBD_Chars.getDBDChar(members.get(2).getId()) + ".png");
                            images.add("https://sensivity.team/bot/img/grauneu.png");
                            images.add("https://sensivity.team/bot/img/dbd_chars/survivor/" + DBD_Chars.getDBDChar(members.get(1).getId()) + ".png");
                            images.add("https://sensivity.team/bot/img/dbd_chars/survivor/" + DBD_Chars.getDBDChar(m.getId()) + ".png");
                            builder.setDescription("**Player 1:** " + m.getAsMention() + DBD_Chars.getDBDRank(m.getId()) + " \n **Player 2:** " + members.get(1).getAsMention() + DBD_Chars.getDBDRank(members.get(1).getId()) + " \n **Player 3:** " + members.get(2).getAsMention() + DBD_Chars.getDBDRank(members.get(2).getId()) + " \n **Player 4:** " + members.get(3).getAsMention() + DBD_Chars.getDBDRank(members.get(3).getId()));
                        }

                        images.add("https://sensivity.team/assets/images/sensivity/titel.png");
                        images.add("https://sensivity.team/bot/img/x.png");

                        try {
                            CreateImage.createImage(images);
                            img = new File("/home/michel929/TeamSensivity/findyourswf.png");
                            builder.setImage("attachment://FindYourSWF" + Functions.getI() + ".png");
                            builder.setTitle("Find Your SWF");
                            builder.setColor(Color.decode("#2ecc71"));
                            builder.setAuthor(m.getEffectiveName(), "https://sensivity.team", m.getEffectiveAvatarUrl());
                            event.getMessage().delete().queue();
                            builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");

                            if (members.size() == 4) {
                                event.getChannel().sendMessageEmbeds(builder.build()).addFiles(FileUpload.fromData(img, "FindYourSWF" + Functions.getI() + ".png")).setActionRow(Button.success("swfjoin" + uuid, "Join SWF").asDisabled(), Button.danger("swfleave" + uuid, "Leave SWF"), Button.link("https://sensivity.team/swf?uuid=" + uuid, "About the Team")).queue(message -> {
                                    SWF.updateMessageID(uuid, message.getId());
                                });
                            } else {
                                event.getChannel().sendMessageEmbeds(builder.build()).addFiles(FileUpload.fromData(img, "FindYourSWF" + Functions.getI() + ".png")).setActionRow(Button.success("swfjoin" + uuid, "Join SWF"), Button.danger("swfleave" + uuid, "Leave SWF"), Button.link("https://sensivity.team/swf?uuid=" + uuid, "About the Team")).queue(message -> {
                                    SWF.updateMessageID(uuid, message.getId());
                                });
                            }
                            Functions.addI();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }

        if(!fool){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(Color.red);
            embedBuilder.setTitle("Du bist nicht in der SWF");
            embedBuilder.setDescription("Du bist in keiner SWF oder du bist nicht in dieser SWF.");
            embedBuilder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue((message) -> {
                message.delete().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }
}

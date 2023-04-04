package dbd.swf;

import functions.CreateImage;
import functions.DBD_Chars;
import main.Main;
import main.Start;
import mysql.BotInfos;
import mysql.SWF;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.FileUpload;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Functions {
    private static File img;
    private static int i = 0;

    public static void createImage(Member member, String uuid, Channel c){
        List<Member> members = new ArrayList<>();
        List<Member> reserviert = SWF.getReserviert(uuid);

        if(reserviert.contains(member)){
            reserviert.remove(member);
        }

        members.add(member);

        boolean fool = false;

        for (String mem : SWF.getPlayer(uuid)) {
            if(mem != null) {
                if(mem.equals(member.getId())){
                    fool = true;
                }
                members.add(Main.INSTANCE.getGuild().getMemberById(mem));
            }
        }

        for (Member m: members) {
            if(reserviert.contains(m)){
                reserviert.remove(m);
            }
        }

        if(!fool) {
            if (SWF.isFree(uuid, member.getId())) {

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

                    images.add("https://sensivity.team/bot/img/swf/titel.png");
                    images.add("https://sensivity.team/bot/img/x.png");
                    try {
                        CreateImage.createImage(images);
                        img = new File("/home/michel929/TeamSensivity/findyourswf.png");
                        builder.setImage("attachment://FindYourSWF" + i + ".png");
                        builder.setTitle("Find Your SWF");
                        builder.setColor(Color.decode("#2ecc71"));
                        builder.setAuthor(m.getEffectiveName(), "https://sensivity.team", m.getEffectiveAvatarUrl());
                        builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");

                        Channel channel = Main.INSTANCE.getApi().getGuildChannelById(BotInfos.getBotInfos("DBD_FIND_SWF"));
                        MessageHistory history = MessageHistory.getHistoryFromBeginning((MessageChannel) channel).complete();
                        List<Message> mess = history.getRetrievedHistory();

                        String messageid = SWF.getMessageID(uuid);

                        for (Message message: mess) {
                            if(message.getId().equals(messageid)) {
                                message.delete().queue();
                            }
                        }

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        builder.setColor(Color.decode("#2ecc71"));
                        embedBuilder.setTitle("Erfolgreich beigetreten");
                        embedBuilder.setDescription("Du bist der SWF erfolgreich beigetreten. Mehr Infos zur SWF findest du entweder Wenn du unten dem Link folgst oder auf unserem Server in den Channel #find_your_swf gehst.");
                        embedBuilder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");

                        if(c.getType() == ChannelType.TEXT) {
                            TextChannel textChannel = (TextChannel) c;
                            textChannel.sendMessageEmbeds(embedBuilder.build()).queue();
                        }else {
                            PrivateChannel textChannel = (PrivateChannel) c;
                            textChannel.sendMessageEmbeds(embedBuilder.build()).queue();
                        }

                        if (members.size() == 4) {
                            ((MessageChannel) channel).sendMessageEmbeds(builder.build()).addFiles(FileUpload.fromData(img, "FindYourSWF" + i + ".png")).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.success("swfjoin" + uuid, "Join SWF").asDisabled(), net.dv8tion.jda.api.interactions.components.buttons.Button.danger("swfleave" + uuid, "Leave SWF"), net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://sensivity.team/swf?uuid=" + uuid, "About the Team")).queue(message -> {
                                SWF.updateMessageID(uuid, message.getId());
                            });
                        } else {
                            ((MessageChannel) channel).sendMessageEmbeds(builder.build()).addFiles(FileUpload.fromData(img, "FindYourSWF" + i + ".png")).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.success("swfjoin" + uuid, "Join SWF"), net.dv8tion.jda.api.interactions.components.buttons.Button.danger("swfleave" + uuid, "Leave SWF"), Button.link("https://sensivity.team/swf?uuid=" + uuid, "About the Team")).queue(message -> {
                                SWF.updateMessageID(uuid, message.getId());
                            });
                        }
                        i++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(Color.red);
            embedBuilder.setTitle("Fehler beim Beitreten der SWF");
            embedBuilder.setDescription("Du bist bereits in einer SWF. Du kannst nur in einer SWF gleichzeitig sein.");
            embedBuilder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");

            if(c.getType() == ChannelType.TEXT) {
                TextChannel textChannel = (TextChannel) c;
                textChannel.sendMessageEmbeds(embedBuilder.build()).queue((message) -> message.delete().queueAfter(10, TimeUnit.SECONDS));
            }else {
                PrivateChannel textChannel = (PrivateChannel) c;
                textChannel.sendMessageEmbeds(embedBuilder.build()).queue((message) -> message.delete().queueAfter(10, TimeUnit.SECONDS));
            }
        }
    }

    public static int getI() {
        return i;
    }

    public static void addI() {
        i++;
    }
}

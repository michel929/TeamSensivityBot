package games.luck.buttons;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import types.ServerButton;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SpinButton implements ServerButton {

    ArrayList<Integer> zahlen = new ArrayList<Integer>();

    @Override
    public void performCommand(ButtonInteractionEvent event) {
        zahlen.clear();

        //nix
        zahlen.add(1);
        zahlen.add(1);
        zahlen.add(1);
        zahlen.add(2);
        zahlen.add(3);
        zahlen.add(3);
        zahlen.add(3);
        zahlen.add(4);
        zahlen.add(4);
        zahlen.add(4);
        zahlen.add(4);

        //100XP
        zahlen.add(7);
        zahlen.add(7);
        zahlen.add(7);
        zahlen.add(7);

        //500 TSP
        zahlen.add(8);
        zahlen.add(8);
        zahlen.add(8);
        zahlen.add(8);

        //2000 TSP
        zahlen.add(9);
        zahlen.add(9);

        //rechte
        zahlen.add(10);

        if(PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")){

            if(PunkteSystem.getPoints(event.getMember().getId()) >= 500) {

                PunkteSystem.uploadPoints(event.getMember().getId(), -500);
                PunkteSystem.upload(event.getMember().getId(), 500, 0, "Ausgegeben für Glücksspiel.");

                int i = zahlen.get((int) (Math.random() * ((zahlen.size() - 1))));

                EmbedBuilder wheel = new EmbedBuilder();
                wheel.setColor(Color.decode("#9914fa"));
                wheel.setThumbnail(BotInfos.getBotInfos("logo_url"));
                wheel.setTitle("Lucky Wheel");
                wheel.setFooter(event.getMember().getEffectiveName(), event.getMember().getEffectiveAvatarUrl());

                if (i == 7) {
                    wheel.setImage("https://sensivity.team/bot/img/slot/7_3.gif");

                    int level = Integer.parseInt(PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "level", "users"));
                    int xp = Integer.parseInt(PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "xp", "users"));

                    int ober = level * 1000;

                    if((xp + 500) >= ober){
                        PlayerInfos.updatePlayerInfos(event.getMember().getId(), "level", (level + 1));
                        PlayerInfos.updatePlayerInfos(event.getMember().getId(), "xp", 0);
                    }else {
                        PlayerInfos.updatePlayerInfos(event.getMember().getId(), "xp", (xp + 500));
                    }


                } else if (i == 8) {
                    wheel.setImage("https://sensivity.team/bot/img/slot/8_1.gif");
                    PunkteSystem.uploadPoints(event.getMember().getId(), 500);
                    PunkteSystem.upload(event.getMember().getId(), 500, 1, "Hast du im Glücksspiel gewonnen.");
                } else if (i == 9) {
                    wheel.setImage("https://sensivity.team/bot/img/slot/9_1.gif");
                    PunkteSystem.uploadPoints(event.getMember().getId(), 2000);
                    PunkteSystem.upload(event.getMember().getId(), 2000, 1, "Hast du im Glücksspiel gewonnen.");
                } else if (i == 10) {
                    wheel.setImage("https://sensivity.team/bot/img/slot/9_1.gif");
                    PunkteSystem.uploadPoints(event.getMember().getId(), 2000);
                    PunkteSystem.upload(event.getMember().getId(), 2000, 1, "Hast du im Glücksspiel gewonnen.");
                } else if (i == 1) {
                    wheel.setImage("https://sensivity.team/bot/img/slot/n_1.gif");
                } else if (i == 2) {
                    wheel.setImage("https://sensivity.team/bot/img/slot/n_2.gif");
                } else if (i == 3) {
                    wheel.setImage("https://sensivity.team/bot/img/slot/n_3.gif");
                } else if (i == 4) {
                    wheel.setImage("https://sensivity.team/bot/img/slot/n_4.gif");
                }

                event.replyEmbeds(wheel.build()).setSuppressedNotifications(true).queue(embed -> {
                    embed.deleteOriginal().queueAfter(5, TimeUnit.SECONDS);
                });
            }else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.RED);
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setDescription("Du hast nicht genug Points um diese Buchung zu tätigen.");
                builder.setTitle("Buchung fehlgeschlagen!");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }
        }
    }
}

package buttons;

import buttons.types.ServerButton;
import minecraft.Hardcore;
import mysql.Minecraft;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.awt.*;

public class Revive implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        Member m = event.getMember();
        String memberID = event.getComponent().getId().replace("revive", "");

        if(PlayerInfos.isExist(m.getId(), "discord_id", "users")){
            if(PlayerInfos.getInfo(memberID, "discord_id", "dead", "hardcore").equals("1")) {
                if (PunkteSystem.getPoints(m.getId()) >= 5000) {
                    PunkteSystem.uploadPoints(m.getId(), -5000);
                    Minecraft.updatePlayer(m.getId(), "dead", 0);

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.decode("#2ecc71"));
                    builder.setThumbnail("https://mc-heads.net/avatar/" + PlayerInfos.getInfo(memberID, "discord_id", "uuid", "hardcore") + "/nohelm.png");
                    builder.setDescription(m.getUser().getAsTag() + " hat " + event.getGuild().getMemberById(memberID).getUser().getAsTag() + " wiederbelebt.");
                    builder.setTitle("Erfolgreich wiederbelebt!");

                    event.getChannel().sendMessageEmbeds(builder.build()).queue();

                    builder.setColor(Color.decode("#2ecc71"));
                    builder.setThumbnail("https://sensivity.team/bot/img/minecraft/kopf/villager.png");
                    builder.setDescription("Du hast den Spieler wiederbelebt.");
                    builder.setTitle("Buchung erfolgreich!");

                    event.replyEmbeds(builder.build()).setEphemeral(true).queue();

                    Hardcore.deadPlayer.remove(memberID);
                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.RED);
                    builder.setThumbnail("https://sensivity.team/bot/img/minecraft/kopf/pillager.png");
                    builder.setDescription("Du hast nicht genug Points um diese Buchung zu tätigen.");
                    builder.setTitle("Buchung fehlgeschlagen!");

                    event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                }
            }else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.RED);
                builder.setThumbnail("https://sensivity.team/bot/img/minecraft/kopf/pillager.png");
                builder.setDescription("Der User lebt bereits wieder!");
                builder.setTitle("Buchung fehlgeschlagen!");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.RED);
            builder.setThumbnail("https://sensivity.team/bot/img/minecraft/kopf/pillager.png");
            builder.setDescription("Du brauchst einen TeamSensivityAccount um diesen Buchung zu tätigen.");
            builder.setTitle("Buchung fehlgeschlagen!");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }
    }
}

package modals;

import functions.GetInfos;
import logging.LogSystem;
import modals.type.ServerModal;
import mysql.BotInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class RenameUser implements ServerModal {
    @Override
    public void performCommand(ModalInteractionEvent event) {
        String newName = event.getValue("rename").getAsString();
        Member m = event.getGuild().getMemberById(event.getModalId().replace("rename", ""));

        PunkteSystem.uploadPoints(event.getMember().getId(), -500);
        PunkteSystem.upload(event.getMember().getId(), 500, 0,  m.getUser().getName() + " umbenannt.");

        m.modifyNickname(newName).queue();
        LogSystem.logGeneral(event.getMember().getId(), "User hat " + m.getUser().getName() + " umbenannt", event.getMember().getUser().getAsTag());

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Erfolgreich umbenannt...");
        builder.setColor(Color.decode("#2ecc71"));
        builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
        builder.setDescription("Du hast den User (" + m.getUser().getName() + ") erfolgreich umbenannt.");

        event.replyEmbeds(builder.build()).setEphemeral(true).queue();

        String url = "https://dashboard.sensivity.team/connect/discord/update-points.php?discord_id=" + event.getMember().getId();
        String url2 = "https://dashboard.sensivity.team/connect/discord/refresh.php?id=" + event.getMember().getId();
        try {
            if(GetInfos.getPoints(new URL(url)).contains("Unauthorized")){
                GetInfos.streamBOT(new URL(url2));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

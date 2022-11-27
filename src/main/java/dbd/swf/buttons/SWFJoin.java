package dbd.swf.buttons;

import buttons.types.ServerButton;
import dbd.swf.Functions;
import functions.CreateImage;
import functions.DBD_Chars;
import main.Start;
import mysql.SWF;
import mysql.dashboard.PlayerInfos;
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

public class SWFJoin implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        String uuid = event.getComponent().getId().replace("swfjoin", "");

        if (!PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users") || PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "survivor_main", "users") == null) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.decode("#2ecc71"));
                builder.setDescription("Du hast entweder keinen Team Sensivity Account oder hat sein DBD Profil noch nicht vervollständigt.");
                builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
                builder.setTitle("Fehler beim joinen der SWF");

            event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                message.delete().queueAfter(10, TimeUnit.SECONDS);
            });
        } else {
            Functions.createImage(event.getMember(), uuid, event.getChannel());
        }
    }
}

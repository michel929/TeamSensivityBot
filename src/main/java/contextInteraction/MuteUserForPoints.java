package contextInteraction;

import contextInteraction.type.UserContextInteraction;
import main.Main;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

import java.awt.*;

public class MuteUserForPoints implements UserContextInteraction {
    @Override
    public void performCommand(UserContextInteractionEvent event) {
        if(PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")){
            if(PunkteSystem.getPoints(event.getMember().getId()) >= 10000){
                if(event.getTargetMember().getVoiceState().inAudioChannel()) {

                    PunkteSystem.uploadPoints(event.getMember().getId(), -10000);
                    PunkteSystem.upload(event.getMember().getId(), 10000, 0, event.getTargetMember().getUser().getName() + " stummgeschaltet.");

                    event.getTargetMember().mute(true).queue();

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.decode("#2ecc71"));
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setDescription("Du hast den User (" + event.getTargetMember().getUser().getName() + ") stummgeschaltet");
                    builder.setFooter("PS: Keiner weiß das du es warst.");
                    builder.setTitle("Erfolgreich stummgeschaltet!");

                    event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                }else{
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.RED);
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setDescription("Der User ist in keinem VoiceChannel.");
                    builder.setTitle("Buchung fehlgeschlagen!");

                    event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                }
            }else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.RED);
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setDescription("Du hast nicht genug Points um diese Buchung zu tätigen.");
                builder.setTitle("Buchung fehlgeschlagen!");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }
        }else {
            event.replyEmbeds(Main.INSTANCE.getEmbedMessages().getNoAccount()).setEphemeral(true).queue();
        }
    }
}

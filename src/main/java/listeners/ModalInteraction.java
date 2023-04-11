package listeners;

import main.Main;
import mysql.BotInfos;
import mysql.Minecraft;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;

import java.awt.*;

public class ModalInteraction extends ListenerAdapter {
    @Override
    public void onModalInteraction(ModalInteractionEvent event) {

        if(event.getModalId().equals("minecraft")){
            String code = event.getValue("minecraftCode").getAsString();

            if(PlayerInfos.isExist(code, "code", "minecraft")) {
                String uuid = PlayerInfos.getInfo(code, "code", "uuid", "minecraft");

                PlayerInfos.updatePlayerInfos(event.getUser().getId(), "minecraft_uuid", uuid);

                if(!PlayerInfos.isExist(event.getUser().getId(), "discord_id", "hardcore")){
                    Minecraft.createHardcore(event.getUser().getId(), uuid);
                }else {
                    Minecraft.updatePlayer(event.getUser().getId(), "uuid", uuid);
                }

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Minecraft Account erfolgreich verknüpft...");
                builder.setColor(Color.decode("#2ecc71"));
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setDescription("Du hast erfolgreich deinen Minecraft Account verknüpft.");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Fehler beim Verbinden des Minecraft Accounts...");
                builder.setColor(Color.red);
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setDescription("Gib einen gültigen Code ein...");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }

        }else if (event.getModalId().contains("rename")){
            String newName = event.getValue("rename").getAsString();
            Member m = event.getGuild().getMemberById(event.getModalId().replace("rename", ""));

            PunkteSystem.uploadPoints(event.getMember().getId(), -500);
            PunkteSystem.upload(event.getMember().getId(), 500, 0,  m.getUser().getName() + " umbenannt.");

            m.modifyNickname(newName).queue();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Erfolgreich umbenannt...");
            builder.setColor(Color.decode("#2ecc71"));
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setDescription("Du hast den User (" + m.getUser().getName() + ") erfolgreich umbenannt.");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }
    }
}

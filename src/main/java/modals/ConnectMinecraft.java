package modals;

import modals.type.ServerModal;
import mysql.BotInfos;
import mysql.Minecraft;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

import java.awt.*;

public class ConnectMinecraft implements ServerModal {
    @Override
    public void performCommand(ModalInteractionEvent event) {
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
    }
}

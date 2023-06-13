package pets.modals;

import modals.type.ServerModal;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import pets.mysql.PetsManager;

import java.awt.*;

public class Name implements ServerModal {
    @Override
    public void performCommand(ModalInteractionEvent event) {
        String name = event.getValue("rename").getAsString();

        if(PetsManager.isExist(event.getMember().getId())){
            PetsManager.update(name, "name", event.getMember().getId());
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.decode("#2ecc71"));
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setDescription("Du hast erfolgreich dein Haustier umbenannt.");
            builder.setTitle("Erfolgreich!");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.RED);
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setDescription("Du brauchst ein Haustier dafür.");
            builder.setTitle("Umbenennen fehlgeschlagen!");

        }
    }
}

package pets.buttons;

import buttons.types.ServerButton;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;

public class Rename implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        String discord_id = event.getComponentId().replace("-rename", "");

        if(discord_id.equals(event.getMember().getId())){
            event.replyModal(createModal()).queue();
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setDescription("Das ist nicht dein Haustier, deswegen kannst du es nicht umbenennen.");
            builder.setColor(Color.RED);
            builder.setTitle("Umbennen fehlgeschlagen!");
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }
    }

    private Modal createModal(){
        TextInput rename = TextInput.create("rename", "Neuer Name für dein Haustier:", TextInputStyle.SHORT)
                .setMinLength(1)
                .setMaxLength(30)
                .setRequired(true)
                .build();

        return Modal.create("animalrename", "Neuer Namen")
                .addComponents(ActionRow.of(rename))
                .build();
    }
}

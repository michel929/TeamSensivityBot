package contextInteraction;

import contextInteraction.type.UserContextInteraction;
import main.Main;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;

public class RenameUserForPoints implements UserContextInteraction {
    @Override
    public void performCommand(UserContextInteractionEvent event) {
        if(PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")){
            if(PunkteSystem.getPoints(event.getMember().getId()) >= 500){
                event.replyModal(createModal(event.getTargetMember().getUser().getId())).queue();
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


    private Modal createModal(String id){
        TextInput rename = TextInput.create("rename", "New Username:", TextInputStyle.SHORT)
                .setMinLength(1)
                .setMaxLength(30)
                .setRequired(true)
                .setValue(id)
                .build();

        return Modal.create("rename" + id, "Rename User")
                .addComponents(ActionRow.of(rename))
                .build();
    }
}

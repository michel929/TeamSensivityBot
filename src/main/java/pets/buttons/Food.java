package pets.buttons;

import buttons.types.ServerButton;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import pets.mysql.PetsManager;
import pets.tiere.Tier;

import java.awt.*;
import java.sql.Timestamp;

public class Food implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        String discord_id = event.getComponentId().replace("food", "");

        Tier animal = PetsManager.getAnimal(discord_id);

        if(animal.getHunger().plusHours(3).isBeforeNow()){
            //TODO MUSS NOCH GEMACHT WERDEN
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Nicht Hungrig!");
            builder.setColor(Color.red);
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setDescription("Das Haustier hat keinen Hunger. Du kannst es erst <t:" + animal.getHunger().plusHours(3).getMillis() / 1000 + ":R> füttern");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }
    }
}

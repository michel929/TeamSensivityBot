package pets.buttons;

import buttons.types.ServerButton;
import main.Main;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import pets.tiere.Animal;

import java.awt.*;

public class BuyAnimal implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        String animal = event.getId().replace("-animalshop", "");
        Animal cat = Main.INSTANCE.getPets().getAnimal(animal).get(0);

        EmbedBuilder schritt1 = new EmbedBuilder();
        schritt1.setColor(Color.decode("#9914fa"));
        schritt1.setThumbnail(BotInfos.getBotInfos("logo_url"));
        schritt1.setDescription("Wähle nun das Tier aus welches du kaufen möchtest...");
        schritt1.setTitle("Tierhandlung");
        schritt1.setFooter(String.valueOf(cat.getId()));
        schritt1.setImage(cat.getUrl());
        schritt1.addField(cat.getName() + ":", cat.getPoints() + " Points", true);

        event.getMessage().delete().queue();

        event.replyEmbeds(schritt1.build()).addActionRow(
                        net.dv8tion.jda.api.interactions.components.buttons.Button.secondary(animal + "-pre", ""),
                        net.dv8tion.jda.api.interactions.components.buttons.Button.success(cat.getId() + "-kaufen", "Kaufen"),
                        net.dv8tion.jda.api.interactions.components.buttons.Button.secondary(animal + "-next", ""))
                .setEphemeral(true).queue();
    }
}

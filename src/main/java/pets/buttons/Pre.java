package pets.buttons;

import buttons.types.ServerButton;
import main.Main;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import pets.tiere.Animal;
import pets.tiere.Pets;

import java.awt.*;

public class Pre implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        String animal = event.getComponentId().replace("-pre", "");
        Pets pets = Main.INSTANCE.getPets();

            int footer = Integer.parseInt(event.getMessage().getEmbeds().get(0).getFooter().getText());
            Animal cats = pets.getAnimalByID(footer);
            if(cats.getPos() == 1){
                Animal cat = pets.getAnimal(animal).get(pets.getAnimal(animal).size() - 1);

                EmbedBuilder schritt1 = new EmbedBuilder();
                schritt1.setColor(Color.decode("#9914fa"));
                schritt1.setThumbnail(BotInfos.getBotInfos("logo_url"));
                schritt1.setDescription("Wähle nun das Tier aus welches du kaufen möchtest...");
                schritt1.setTitle("Tierhandlung");
                schritt1.setImage(cat.getUrl());
                schritt1.setFooter(cat.getId() + "");
                schritt1.addField(cat.getName() + ":", cat.getPoints() + " Points", true);

                event.editMessageEmbeds(schritt1.build()).setActionRow(
                                net.dv8tion.jda.api.interactions.components.buttons.Button.secondary(animal + "-pre", Emoji.fromFormatted("U+25C0")),
                                net.dv8tion.jda.api.interactions.components.buttons.Button.success(cat.getId() + "-kaufen", "Kaufen"),
                                net.dv8tion.jda.api.interactions.components.buttons.Button.secondary(animal + "-next", Emoji.fromFormatted("U+25B6")))
                        .queue();
            }else {
                Animal cat = pets.getAnimalByID(footer - 1);

                EmbedBuilder schritt1 = new EmbedBuilder();
                schritt1.setColor(Color.decode("#9914fa"));
                schritt1.setThumbnail(BotInfos.getBotInfos("logo_url"));
                schritt1.setDescription("Wähle nun das Tier aus welches du kaufen möchtest...");
                schritt1.setTitle("Tierhandlung");
                schritt1.setImage(cat.getUrl());
                schritt1.setFooter(cat.getId() + "");
                schritt1.addField(cat.getName() + ":", cat.getPoints() + " Points", true);

                event.editMessageEmbeds(schritt1.build()).setActionRow(
                                net.dv8tion.jda.api.interactions.components.buttons.Button.secondary(animal + "-pre", Emoji.fromFormatted("U+25C0")),
                                net.dv8tion.jda.api.interactions.components.buttons.Button.success(cat.getId() + "-kaufen", "Kaufen"),
                                net.dv8tion.jda.api.interactions.components.buttons.Button.secondary(animal + "-next", Emoji.fromFormatted("U+25B6")))
                        .queue();
            }
    }
}

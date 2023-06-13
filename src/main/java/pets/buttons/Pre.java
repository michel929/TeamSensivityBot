package pets.buttons;

import buttons.types.ServerButton;
import main.Main;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import pets.tiere.Animal;
import pets.tiere.Pets;

import java.awt.*;

public class Pre implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        String animal = event.getId().replace("-pre", "");
        Pets pets = Main.INSTANCE.getPets();

            int footer = Integer.parseInt(event.getMessage().getEmbeds().get(0).getFooter().getText());
            if(footer == pets.getAnimal(animal).size()){
                Animal cat = pets.getAnimal(animal).get(0);

                EmbedBuilder schritt1 = new EmbedBuilder();
                schritt1.setColor(Color.decode("#9914fa"));
                schritt1.setThumbnail(BotInfos.getBotInfos("logo_url"));
                schritt1.setDescription("Wähle nun das Tier aus welches du kaufen möchtest...");
                schritt1.setTitle("Tierhandlung");
                schritt1.setImage(cat.getUrl());
                schritt1.setFooter(cat.getId() + "");
                schritt1.addField(cat.getName() + ":", cat.getPoints() + " Points", true);

                event.editMessageEmbeds(schritt1.build()).queue();
            }else {
                Animal cat = pets.getAnimal(animal).get(footer + 1);

                EmbedBuilder schritt1 = new EmbedBuilder();
                schritt1.setColor(Color.decode("#9914fa"));
                schritt1.setThumbnail(BotInfos.getBotInfos("logo_url"));
                schritt1.setDescription("Wähle nun das Tier aus welches du kaufen möchtest...");
                schritt1.setTitle("Tierhandlung");
                schritt1.setImage(cat.getUrl());
                schritt1.setFooter(cat.getId() + "");
                schritt1.addField(cat.getName() + ":", cat.getPoints() + " Points", true);

                event.editMessageEmbeds(schritt1.build()).queue();
            }
    }
}

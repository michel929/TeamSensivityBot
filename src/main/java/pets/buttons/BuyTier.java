package pets.buttons;

import buttons.types.ServerButton;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.awt.*;

public class BuyTier implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {

        EmbedBuilder schritt1 = new EmbedBuilder();
        schritt1.setColor(Color.decode("#9914fa"));
        schritt1.setThumbnail(BotInfos.getBotInfos("logo_url"));
        schritt1.setDescription("Wähle eine Tier aus was du kaufen möchtest...");
        schritt1.setTitle("Tierhandlung");

        StringSelectMenu menu =  StringSelectMenu.create("animalshop")
                .addOption("Vogel", "bird-animalshop", "1000 Points")
                .addOption("Hamster", "hamster-animalshop", "2000 Points")
                .addOption("Hase", "rabbit-animalshop", "2000 Points")
                .addOption("Katze", "cat-animalshop", "4000 Points") // SelectOption with only the label, value, and description
                .addOption("Hund", "dog-animalshop", "5000 Points")
                .addOption("Dragon", "dragon-animalshop", "8000 Points")
                .addOption("Special", "special-animalshop", "10000 Points")
                .build();

        event.replyEmbeds(schritt1.build()).addActionRow(menu).setEphemeral(true).queue();
    }
}

package pets.buttons;

import buttons.types.ServerButton;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class BuyTier implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {

        EmbedBuilder schritt1 = new EmbedBuilder();
        schritt1.setColor(Color.decode("#9914fa"));
        schritt1.setThumbnail(BotInfos.getBotInfos("logo_url"));
        schritt1.setDescription("Wähle eine Tier aus was du kaufen möchtest...");
        schritt1.setTitle("Tierhandlung");

        event.replyEmbeds(schritt1.build()).addActionRow(
                Button.secondary("cat-animalshop", "Katze"),
                        Button.secondary("dog-animalshop", "Hund"),
                        Button.secondary("hamster-animalshop", "Hamster"),
                        Button.secondary("bird-animalshop", "Bird"),
                        Button.secondary("rabbit-animalshop", "Hase"),
                        Button.secondary("dragon-animalshop", "Dragon"),
                        Button.secondary("special-animalshop", "Special"))
                .setEphemeral(true).queue();
    }
}

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
                Button.secondary("cat-shop", "Katze"),
                        Button.secondary("dog-shop", "Hund"),
                        Button.secondary("hamster-shop", "Hamster"),
                        Button.secondary("fish-shop", "Fisch"),
                        Button.secondary("rabbit-shop", "Hase"))
                .setEphemeral(true).queue();
    }
}

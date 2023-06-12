package pets.buttons;

import buttons.types.ServerButton;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class BuyCat implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        EmbedBuilder schritt1 = new EmbedBuilder();
        schritt1.setColor(Color.decode("#9914fa"));
        schritt1.setThumbnail(BotInfos.getBotInfos("logo_url"));
        schritt1.setDescription("Wähle nun das Tier aus welches du kaufen möchtest...");
        schritt1.setTitle("Tierhandlung");

        event.getMessage().delete().queue();

        event.replyEmbeds(schritt1.build()).addActionRow(
                        net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("cat-shop", ""),
                        net.dv8tion.jda.api.interactions.components.buttons.Button.success("buy", "Kaufen"),
                        net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("hamster-shop", ""))
                .setEphemeral(true).queue();
    }
}

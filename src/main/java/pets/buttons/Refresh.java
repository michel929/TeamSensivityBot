package pets.buttons;

import buttons.types.ServerButton;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.joda.time.DateTime;
import pets.mysql.PetsManager;
import pets.tiere.Tier;

import java.awt.*;

public class Refresh implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        String discord_id = event.getComponentId().replace("-refresh", "");
        Tier t = PetsManager.getAnimal(discord_id);

        EmbedBuilder b = new EmbedBuilder();
        b.setImage(t.getType().getUrl());
        b.setColor(Color.decode("#9914fa"));
        b.setTitle(t.getName());

        DateTime footDate = t.getHunger().plusHours(3);
        long food = footDate.getMillis() / 1000;

        DateTime drinkDate = t.getDurst().plusHours(1);
        long drink = drinkDate.getMillis() / 1000;

        DateTime date = t.getBday();
        long now = date.getMillis() / 1000;

        b.addField("Level:", String.valueOf(t.getLevel()), true);
        b.addField("Bday:", "<t:" + now + ":D>", true);
        b.addField("Besitzer:", event.getGuild().getMemberById(discord_id).getAsMention(), true);
        b.addField("Happiness:", t.getHappy() + " von 100", false);
        b.addField("Essen:", "Kann <t:" + food + ":R> gefüttert werden", false);
        b.addField("Trinken:", "Kann <t:" + drink + ":R> gefüttert werden", false);

        event.editMessageEmbeds(b.build()).queue();
    }
}

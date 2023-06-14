package pets.buttons;

import buttons.types.ServerButton;
import mysql.BotInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.joda.time.DateTime;
import pets.mysql.PetsManager;
import pets.tiere.Tier;

import java.awt.*;

public class Drink implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        String discord_id = event.getComponentId().replace("-drink", "");

        Tier animal = PetsManager.getAnimal(discord_id);

        if(animal.getHunger().plusHours(3).isBeforeNow()){
            if(PunkteSystem.getPoints(event.getMember().getId()) >= 60){
                    PunkteSystem.uploadPoints(event.getMember().getId(), -60);
                    PunkteSystem.upload(event.getMember().getId(), 60, 0, "Haustier gefüttert.");

                    String s = String.valueOf(DateTime.now()).replace("T", " ");
                    s = s.substring(0, s.length() - 6);
                    PetsManager.update(s, "durst", discord_id);

                    animal = PetsManager.getAnimal(discord_id);

                    EmbedBuilder b = new EmbedBuilder();
                    b.setImage(animal.getType().getUrl());
                    b.setColor(Color.decode("#9914fa"));
                    b.setTitle(animal.getName());

                    DateTime footDate = animal.getHunger().plusHours(3);
                    long food = footDate.getMillis() / 1000;

                    DateTime drinkDate = animal.getDurst().plusHours(1);
                    long drink = drinkDate.getMillis() / 1000;

                    DateTime date = animal.getBday();
                    long now = date.getMillis() / 1000;

                    b.addField("Level:", "1", true);
                    b.addField("Bday:", "<t:" + now + ":D>", true);
                    b.addField("Besitzer:", event.getGuild().getMemberById(discord_id).getAsMention(), true);
                    b.addField("Happiness:", animal.getHappy() + " von 100", false);
                    b.addField("Essen:", "Kann <t:" + food + ":R> gefüttert werden", false);
                    b.addField("Trinken:", "Kann <t:" + drink + ":R> gefüttert werden", false);

                    event.editMessageEmbeds(b.build()).queue();
            }else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.RED);
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setDescription("Du hast nicht genug Points um diese Buchung zu tätigen.");
                builder.setTitle("Buchung fehlgeschlagen!");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Nicht Hungrig!");
            builder.setColor(Color.red);
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setDescription("Das Haustier hat keinen Durst. Du kannst es erst <t:" + animal.getDurst().plusHours(1).getMillis() / 1000 + ":R> füttern");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }
    }
}

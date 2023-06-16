package pets.buttons;

import buttons.types.ServerButton;
import functions.GetInfos;
import mysql.BotInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.joda.time.DateTime;
import pets.Function;
import pets.mysql.PetsManager;
import pets.tiere.Tier;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Food implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        String discord_id = event.getComponentId().replace("-food", "");
        Tier animal = PetsManager.getAnimal(discord_id);

        if(animal.getHunger().plusHours(3).isBeforeNow()){
            if(PunkteSystem.getPoints(event.getMember().getId()) >= 100){
                    PunkteSystem.uploadPoints(event.getMember().getId(), -100);
                    PunkteSystem.upload(event.getMember().getId(), 100, 0, "Haustier gefüttert.");

                    String url = "https://dashboard.sensivity.team/connect/discord/update-points.php?discord_id=" + event.getMember().getId();
                    String url2 = "https://dashboard.sensivity.team/connect/discord/refresh.php?id=" + event.getMember().getId();
                    try {
                        if(GetInfos.getPoints(new URL(url)).contains("Unauthorized")){
                            GetInfos.streamBOT(new URL(url2));
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    String s = String.valueOf(DateTime.now()).replace("T", " ");
                    s = s.substring(0, s.length() - 6);
                    PetsManager.update(s, "hunger", discord_id);

                    int hunger = animal.getHungerheute();
                    PetsManager.update(hunger + 1, "heute_food", discord_id);

                    hunger = animal.getAmount_drink();
                    PetsManager.update(hunger + 1, "amount_food", discord_id);

                    int level = Function.levelUpdate(hunger + 1 + animal.getAmount_drink());

                    if(level == -1){
                        if((hunger + 1 + animal.getAmount_drink()) % 100 == 0){
                            PetsManager.update(animal.getLevel() + 1, "level", discord_id);
                        }
                    }else {
                      PetsManager.update(level, "level", discord_id);
                    }

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
            builder.setDescription("Das Haustier hat keinen Hunger. Du kannst es erst <t:" + animal.getHunger().plusHours(3).getMillis() / 1000 + ":R> füttern");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }
    }
}
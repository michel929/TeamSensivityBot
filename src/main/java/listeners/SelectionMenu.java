package listeners;

import main.Main;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pets.tiere.Animal;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class SelectionMenu extends ListenerAdapter {

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().contains("animalshop")){
            String animal = event.getValues().get(0).replace("-animalshop", "");
            Animal cat = Main.INSTANCE.getPets().getAnimal(animal).get(0);

            EmbedBuilder schritt1 = new EmbedBuilder();
            schritt1.setColor(Color.decode("#9914fa"));
            schritt1.setThumbnail(BotInfos.getBotInfos("logo_url"));
            schritt1.setDescription("Wähle nun das Tier aus welches du kaufen möchtest...");
            schritt1.setTitle("Tierhandlung");
            schritt1.setFooter(String.valueOf(cat.getId()));
            schritt1.setImage(cat.getUrl());
            schritt1.addField(cat.getName() + ":", cat.getPoints() + " Points", true);

            event.replyEmbeds(schritt1.build()).addActionRow(
                            net.dv8tion.jda.api.interactions.components.buttons.Button.secondary(animal + "-pre", Emoji.fromFormatted("U+25C0")),
                            net.dv8tion.jda.api.interactions.components.buttons.Button.success(cat.getId() + "-kaufen", "Kaufen"),
                            net.dv8tion.jda.api.interactions.components.buttons.Button.secondary(animal + "-next", Emoji.fromFormatted("U+25B6")))
                    .setEphemeral(true).queue();
        }


        EmbedBuilder builder = new EmbedBuilder();
        if(event.getComponent().getId().equals("dbd_role")){
            for (int i = 0; i < event.getValues().size(); i++){
                switch (event.getValues().get(i)){
                    case "killer":
                        builder.setTitle("Deine MainRole: **Killer**");
                        builder.setDescription("Du hast als MainRolle **Killer** selected. Du kannst deine Auswahl jederzeit im Dashboard oder hier bearbeiten.");
                        builder.setColor(Color.decode("#d9cfa1"));
                        builder.setAuthor("Team Sensivity", "https://sensivity.team", "https://sensivity.team/bot/img/dbd_logo.jpg");
                        builder.setThumbnail("https://sensivity.team/bot/img/killer.png");
                        builder.setFooter("Scheibe einen CharacterName in den Chat um deinen MAIN_CHAR zu bestimmen... (Bsp.: Hillbilly)");
                        event.replyEmbeds(builder.build()).queue((message) -> message.deleteOriginal().queueAfter(10, TimeUnit.SECONDS));
                        break;
                    case "survivor":
                        builder.setTitle("Deine MainRole: **Survivor**");
                        builder.setDescription("Du hast als MainRolle **Survivor** selected. Du kannst deine Auswahl jederzeit im Dashboard oder hier bearbeiten.");
                        builder.setColor(Color.decode("#d9cfa1"));
                        builder.setFooter("Scheibe einen CharacterName in den Chat um deinen MAIN_CHAR zu bestimmen... (Bsp.: Dwight)");
                        builder.setAuthor("Team Sensivity", "https://sensivity.team", "https://sensivity.team/bot/img/dbd_logo.jpg");
                        builder.setThumbnail("https://sensivity.team/bot/img/surv.png");

                        event.replyEmbeds(builder.build()).queue((message) -> message.deleteOriginal().queueAfter(10, TimeUnit.SECONDS));
                        break;
                    default:
                        break;
                }
            }
        }
    }
}

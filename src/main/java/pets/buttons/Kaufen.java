package pets.buttons;

import buttons.types.ServerButton;
import functions.GetInfos;
import main.Main;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.joda.time.DateTime;
import pets.mysql.PetsManager;
import pets.tiere.Animal;
import pets.tiere.Pets;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Kaufen implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        int animal = Integer.parseInt(event.getComponentId().replace("-kaufen", ""));
        Pets pets = Main.INSTANCE.getPets();
        Animal tier = pets.getAnimalByID(animal);

        if(PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")){
            if(!PetsManager.isExist(event.getMember().getId())) {
                if (PunkteSystem.getPoints(event.getMember().getId()) >= tier.getPoints()) {
                    PunkteSystem.uploadPoints(event.getMember().getId(), -tier.getPoints());
                    PunkteSystem.upload(event.getMember().getId(), tier.getPoints(), 0, "Neues Haustier gekauft.");

                    String url = "https://dashboard.sensivity.team/connect/discord/update-points.php?discord_id=" + event.getMember().getId();
                    String url2 = "https://dashboard.sensivity.team/connect/discord/refresh.php?id=" + event.getMember().getId();
                    try {
                        if(GetInfos.getPoints(new URL(url)).contains("Unauthorized")){
                            GetInfos.streamBOT(new URL(url2));
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.decode("#2ecc71"));
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setDescription("Du hast erfolgreich einen " + tier.getName() + " gekauft. Alle Infos zu deinem Tier findest du unter #pets ...");
                    builder.setTitle("Juhu du hast ein neues Haustier!");

                    PetsManager.createPet(tier.getId(), event.getMember().getId(), tier.getName());

                    event.getGuild().getTextChannelById("1118147590064721970").createThreadChannel(event.getMember().getEffectiveName() + "'s Haustier").queue(threadChannel -> {
                        Animal t = Main.INSTANCE.getPets().getAnimalByID(PetsManager.getPet(event.getMember().getId()));

                        EmbedBuilder b = new EmbedBuilder();
                        b.setImage(t.getUrl());
                        b.setColor(Color.decode("#9914fa"));
                        b.setTitle(t.getName());

                        DateTime footDate = DateTime.now().plusHours(3);
                        long food = footDate.getMillis() / 1000;

                        DateTime drinkDate = DateTime.now().plusHours(1);
                        long drink = drinkDate.getMillis() / 1000;

                        DateTime date = DateTime.now();
                        long now = date.getMillis() / 1000;

                        b.addField("Level:", "1", true);
                        b.addField("Bday:", "<t:" + now + ":D>", true);
                        b.addField("Besitzer:", event.getMember().getAsMention(), true);
                        b.addField("Happiness:", "50 von 100", false);
                        b.addField("Essen:", "Kann <t:" + food + ":R> gefüttert werden", false);
                        b.addField("Trinken:", "Kann <t:" + drink + ":R> gefüttert werden", false);

                        threadChannel.sendMessageEmbeds(b.build()).addActionRow(Button.secondary(event.getMember().getId() + "-refresh", Emoji.fromFormatted("U+1F501")), Button.success(event.getMember().getId() + "-food", Emoji.fromFormatted("U+1F356")), Button.success(event.getMember().getId() + "-drink", Emoji.fromFormatted("U+1F6B0")), Button.secondary(event.getMember().getId() + "-rename", "Rename")).setSuppressedNotifications(true).queue();
                    });

                    event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.RED);
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setDescription("Du hast nicht genug Points um diese Buchung zu tätigen.");
                    builder.setTitle("Buchung fehlgeschlagen!");

                    event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                }
            }else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.RED);
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setDescription("Du hast bereits ein Haustier jeder kann nur eins haben... Du kannst jedoch das derzeitige verkaufen...");
                builder.setTitle("Buchung fehlgeschlagen!");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }
        }else {
            event.replyEmbeds(Main.INSTANCE.getEmbedMessages().getNoAccount()).setEphemeral(true).queue();
        }
    }
}

package listeners;

import main.Main;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class SelectionMenu extends ListenerAdapter {

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("choose-game")){
            if(PlayerInfos.isExist(event.getUser().getId(), "discord_id", "users")) {
                Main.INSTANCE.getSelectSave().addUserSelect(event.getMember().getId(), event.getValues().get(0));
            }else {
                event.replyEmbeds(Main.INSTANCE.getEmbedMessages().getNoAccount()).setEphemeral(true).queue();
            }
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

package commands.embeds;

import commands.types.ServerCommand;
import main.Main;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.json.simple.parser.ParseException;

import java.awt.*;

public class CreateLobby implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) throws ParseException {
        if(event.getMember().hasPermission(Permission.ADMINISTRATOR)){
            EmbedBuilder schritt1 = new EmbedBuilder();
            schritt1.setColor(Color.decode("#9914fa"));
            schritt1.setThumbnail(BotInfos.getBotInfos("logo_url"));
            schritt1.setDescription("Wähle hier ein Game aus und klicke dann unten auf Start.");
            schritt1.setTitle("Create GameLobby");

            StringSelectMenu menu = StringSelectMenu.create("choose-game")
                    .addOptions(SelectOption.of("Black Jack", "jack")
                                    .withDescription("Spiel gegen den Dealer")
                                    .withDefault(true)
                            )
                    .build();

            event.getChannel().sendMessageEmbeds(schritt1.build()).addActionRow(menu).queue();
            event.getChannel().sendMessage("\u1CBC").addActionRow(Button.success("start-game", "Start")).queue();

        }else {
            event.getChannel().sendMessageEmbeds(Main.INSTANCE.getEmbedMessages().getNoPermission()).queue();
        }
    }
}

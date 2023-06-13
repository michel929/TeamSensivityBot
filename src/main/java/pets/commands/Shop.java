package pets.commands;

import commands.types.ServerCommand;
import main.Main;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.json.simple.parser.ParseException;

import java.awt.*;

public class Shop implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) throws ParseException {
        if(event.getMember().hasPermission(Permission.ADMINISTRATOR)){
            EmbedBuilder schritt1 = new EmbedBuilder();
            schritt1.setColor(Color.decode("#9914fa"));
            schritt1.setThumbnail(BotInfos.getBotInfos("logo_url"));
            schritt1.setDescription("Hier kannst du ein Tier kaufen... Oder ein Accessoires für dein Tier kaufen.");
            schritt1.addField("Was bringt mir ein Haustier?", "Ein Haustier sammelt auch Points für dich wenn du Offline bist.", true);
            schritt1.setTitle("Tierhandlung");
            schritt1.setImage("https://sensivity.team/bot/pets/pets.png");

            event.getChannel().sendMessageEmbeds(schritt1.build()).addActionRow(Button.secondary("tier-shop", "Tier kaufen"), Button.secondary("asset-shop", "Accessoires kaufen")).queue();

        }else {
            event.getChannel().sendMessageEmbeds(Main.INSTANCE.getEmbedMessages().getNoPermission()).queue();
        }
    }
}

package slash;

import main.Main;
import main.Start;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.modals.Modal;
import slash.types.ServerSlash;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Setup implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {

        StringSelectMenu.Builder menu = StringSelectMenu.create("Select Games");
        List<Role> roles = event.getMember().getRoles();
        ConcurrentHashMap<String, Role> gamerole = Main.INSTANCE.getGameRoles().getRoles();

        if(roles.contains(gamerole.get("Valorant"))){
            menu.addOptions(
                    SelectOption.of("Valorant", "valo")
                    .withDescription("")
                    .withEmoji(event.getGuild().getEmojiById("1000822070613127169"))
                    .withDefault(true)
            );
        }else {
            menu.addOption("Valorant", "valo", "", event.getGuild().getEmojiById("1000822070613127169"));
        }

        if(roles.contains(gamerole.get("Battlefront II"))){
            menu.addOptions(
                    SelectOption.of("Battlefront II", "battlefront")
                            .withDescription("")
                            .withEmoji(event.getGuild().getEmojiById("1078630122254499840"))
                            .withDefault(true)
            );
        }else {
           menu.addOption("Battlefront II", "battlefront", "", event.getGuild().getEmojiById("1078630122254499840"));
        }

        if(roles.contains(gamerole.get("Dead by Daylight"))){
            menu.addOptions(
                    SelectOption.of("Dead by Daylight", "dbd")
                            .withDescription("")
                            .withEmoji(event.getGuild().getEmojiById("1000821375113637928"))
                            .withDefault(true)
            );
        }else {
            menu.addOption("Dead by Daylight", "dbd", "", event.getGuild().getEmojiById("1000821375113637928"));
        }

        if(roles.contains(gamerole.get("League of Legends"))){
            menu.addOptions(
                    SelectOption.of("League of Legends", "lol")
                            .withDescription("")
                            .withEmoji(event.getGuild().getEmojiById("1000820299551481939"))
                            .withDefault(true)
            );
        }else {
            menu.addOption("League of Legends", "lol", "", event.getGuild().getEmojiById("1000820299551481939"));
        }

        if(roles.contains(gamerole.get("ECO"))){
            menu.addOptions(
                    SelectOption.of("ECO", "eco")
                            .withDescription("")
                            .withEmoji(event.getGuild().getEmojiById("1000825105548582983"))
                            .withDefault(true)
            );
        }else {
            menu.addOption("ECO", "eco", "", event.getGuild().getEmojiById("1000825105548582983"));
        }

        if(roles.contains(gamerole.get("Genshin Impact"))){
            menu.addOptions(
                    SelectOption.of("Genshin Impact", "genshin")
                            .withDescription("")
                            .withEmoji(event.getGuild().getEmojiById("1078630989582061568"))
                            .withDefault(true)
            );
        }else {
            menu.addOption("Genshin Impact", "genshin", "", event.getGuild().getEmojiById("1078630989582061568"));
        }

        if(roles.contains(gamerole.get("Fall Guys"))){
            menu.addOptions(
                    SelectOption.of("Fall Guys", "fall")
                            .withDescription("")
                            .withEmoji(event.getGuild().getEmojiById("1000829620335149057"))
                            .withDefault(true)
            );
        }else {
            menu.addOption("Fall Guys", "fall", "", event.getGuild().getEmojiById("1000829620335149057"));
        }

        if(roles.contains(gamerole.get("Minecraft"))){
            menu.addOptions(
                    SelectOption.of("Minecraft", "minecraft")
                            .withDescription("")
                            .withEmoji(event.getGuild().getEmojiById("1000823430695878716"))
                            .withDefault(true)
            );
        }else {
            menu.addOption("Minecraft", "minecraft", "", event.getGuild().getEmojiById("1000823430695878716"));
        }

        if(roles.contains(gamerole.get("Sea of Thieves"))){
            menu.addOptions(
                    SelectOption.of("Sea of Thieves", "sot")
                            .withDescription("")
                            .withEmoji(event.getGuild().getEmojiById("1001078806506905622"))
                            .withDefault(true)
            );
        }else {
            menu.addOption("Sea of Thieves", "sot", "", event.getGuild().getEmojiById("1001078806506905622"));
        }

        if(roles.contains(gamerole.get("Space Engineers"))){
            menu.addOptions(
                    SelectOption.of("Space Engineers", "space")
                            .withDescription("")
                            .withEmoji(event.getGuild().getEmojiById("1000828140333056070"))
                            .withDefault(true)
            );
        }else {
            menu.addOption("Space Engineers", "space", "", event.getGuild().getEmojiById("1000828140333056070"));
        }

        Modal modal = Modal.create("setup", "Setup ur Account").addActionRows(ActionRow.of(menu.build())).build();

        event.replyModal(modal).queue();
    }
}
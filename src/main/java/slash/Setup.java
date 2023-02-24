package slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.modals.Modal;
import slash.types.ServerSlash;

public class Setup implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        StringSelectMenu menu = StringSelectMenu.create("Select Games")
                .addOption("Dead by Daylight", "dbd", "", event.getGuild().getEmojiById("1000821375113637928"))
                .addOption("Valorant", "valo", "", event.getGuild().getEmojiById("1000822070613127169"))
                .addOption("League of Legends", "lol", "", event.getGuild().getEmojiById("1000820299551481939"))
                .addOption("Battlefront II", "battlefront", "", event.getGuild().getEmojiById("1078630122254499840"))
                .addOption("ECO", "eco", "", event.getGuild().getEmojiById("1000825105548582983"))
                .addOption("Fall Guys", "fall", "", event.getGuild().getEmojiById("1000829620335149057"))
                .addOption("Genshin Impact", "genshin", "", event.getGuild().getEmojiById("1078630989582061568"))
                .addOption("Minecraft", "minecraft", "", event.getGuild().getEmojiById("1000823430695878716"))
                .addOption("Sea of Thieves", "sot", "", event.getGuild().getEmojiById("1001078806506905622"))
                .addOption("Space Engineers", "space", "", event.getGuild().getEmojiById("1000828140333056070"))
                .build();

        Modal modal = Modal.create("setup", "Setup ur Account").addActionRows(ActionRow.of(menu)).build();

        event.replyModal(modal).queue();
    }
}

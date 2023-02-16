package slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.modals.Modal;
import slash.types.ServerSlash;

public class Setup implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        StringSelectMenu menu = StringSelectMenu.create("Select Games").addOption("Dead by Daylight", "dbd", "", event.getGuild().getEmojiById("1075905157210066944"))
                .addOption("Valorant", "valo", "", event.getGuild().getEmojiById(""))
                .addOption("League of Legends", "lol", "", event.getGuild().getEmojiById(""))
                .addOption("Battlefront II", "battlefront", "", event.getGuild().getEmojiById(""))
                .addOption("ECO", "eco", "", event.getGuild().getEmojiById(""))
                .addOption("Fall Guys", "fall", "", event.getGuild().getEmojiById(""))
                .addOption("Genshin Impact", "genshin", "", event.getGuild().getEmojiById(""))
                .addOption("Minecraft", "minecraft", "", event.getGuild().getEmojiById(""))
                .addOption("Sea of Thieves", "sot", "", event.getGuild().getEmojiById(""))
                .addOption("Space Engineers", "space", "", event.getGuild().getEmojiById(""))
                .build();

        Modal modal = Modal.create("setup", "Setup ur Account").addActionRows(ActionRow.of(menu)).build();

        event.replyModal(modal).queue();
    }
}

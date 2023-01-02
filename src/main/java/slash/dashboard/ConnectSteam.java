package slash.dashboard;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import slash.types.ServerSlash;

public class ConnectSteam implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        TextInput steamid = TextInput.create("steamid", "Steam64 ID", TextInputStyle.SHORT)
                .setPlaceholder("Enter your Steam64ID")
                .setMinLength(17)
                .setMaxLength(17) // or setRequiredRange(10, 100)
                .build();



        Modal modal = Modal.create("steam", "Connect your SteamAccount")
                .addActionRows(ActionRow.of(steamid))
                .build();

        event.replyModal(modal).queue();
    }
}

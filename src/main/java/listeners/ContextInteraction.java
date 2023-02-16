package listeners;

import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class ContextInteraction extends ListenerAdapter {
    @Override
    public void onUserContextInteraction(UserContextInteractionEvent event) {
        if(event.getName().equals("Report User")){
            TextInput body = TextInput.create("reason", "Reason", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Gegen was genau verstößt der User...")
                    .setMinLength(30)
                    .setMaxLength(1000)
                    .build();

            Modal modal = Modal.create("report", "Report User" + event.getTargetMember().getUser().getName()).addActionRows(ActionRow.of(body)).build();

            event.replyModal(modal).queue();
        }
    }
}

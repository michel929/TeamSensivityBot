package types;

import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

public interface UserContextInteraction {
    public void performCommand(UserContextInteractionEvent event);
}

package modals.type;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

public interface ServerModal {
    public void performCommand(ModalInteractionEvent event);
}

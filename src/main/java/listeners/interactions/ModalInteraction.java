package listeners.interactions;

import main.Main;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ModalInteraction extends ListenerAdapter {
    @Override
    public void onModalInteraction(ModalInteractionEvent event) {

        if (!Main.INSTANCE.getModalMan().perform(event.getModalId(), event)) {
            event.reply("Unbekanntes Modal").setEphemeral(true).queue();
        }
    }
}

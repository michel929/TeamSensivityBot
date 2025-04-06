package listeners.interactions;

import main.Start;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (!Start.INSTANCE.getButtonMan().perform(event.getComponentId(), event)) {
            event.reply("Unbekannter Button").queue();
        }
    }
}


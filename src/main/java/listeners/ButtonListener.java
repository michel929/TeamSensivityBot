package listeners;

import main.Main;
import main.Start;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class ButtonListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if(event.getButton().getId().contains("swf")) {
            event.reply("*Befehl wird ausgeführt...*").queue((message) -> message.deleteOriginal().queueAfter(10, TimeUnit.SECONDS));
        }

        if (!Main.INSTANCE.getButtonMan().perform(event.getComponentId(), event)) {
            event.reply("Unbekannter Button").queue();
        }
    }
}


package ticket;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TicketSelect extends ListenerAdapter {
    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        String id = event.getChannelId();
        String name = event.getComponentId().replace(id, "");

        if(name.equals("choose-server")){
            for (int i = 0; i < event.getValues().size(); i++) {
                String value = event.getValues().get(i);

                
            }
        }

    }
}

package watchRoom.buttons;

import types.ServerButton;
import main.Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class WatchRoomAccept implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        Member m = Main.INSTANCE.getGuild().getMemberById(event.getUser().getId());

        if(m != null){
            m.mute(false).queue();
        }

        event.deferEdit().queue();
    }
}

package dbd.swf.buttons;

import buttons.types.ServerButton;
import dbd.swf.Functions;
import main.Start;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.io.File;

public class SWFYes implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        String uuid = event.getComponent().getId().replace("swfyes", "");
        Member m = Start.INSTANCE.getApi().getGuildById(Start.GUILD_ID).getMemberById(event.getUser().getId());

        Functions.createImage(m, uuid, event.getChannel());
    }
}

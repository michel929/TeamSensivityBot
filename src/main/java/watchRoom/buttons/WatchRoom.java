package watchRoom.buttons;

import types.ServerButton;
import listeners.MemberJoinChannel;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class WatchRoom implements ServerButton {
    @Override
    public void performCommand(ButtonInteractionEvent event) {
        if(MemberJoinChannel.watch.contains(event.getChannel())){
            MemberJoinChannel.watch.remove(event.getChannel());

            boolean finish = false;
            int x = 1;

            while(finish == false) {
                int w = 0;
                for (Channel v : MemberJoinChannel.channel) {
                    if(v.getName().contains("" + x)){
                        x++;
                    }else {
                        w++;
                    }
                }

                if(w == MemberJoinChannel.channel.size()){
                    finish = true;
                }
            }

            event.getChannel().asAudioChannel().getManager().setName("Chill | " + x).queue();
            event.deferEdit().queue();
        }else {
            MemberJoinChannel.watch.add(event.getChannel());

            boolean finish = false;
            int x = 1;

            while(finish == false) {
                int w = 0;
                for (Channel v : MemberJoinChannel.watch) {
                    if(v.getName().contains("" + x)){
                        x++;
                    }else {
                        w++;
                    }
                }

                if(w == MemberJoinChannel.watch.size()){
                    finish = true;
                }
            }

            event.getChannel().asAudioChannel().getManager().setName("Watch Room | " + x).queue();
            event.deferEdit().queue();

        }
    }
}

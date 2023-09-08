package unendlichkeit.listeners;

import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageUpdate extends ListenerAdapter {
    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
        if(event.getMessageId().equals(MessageRecived.messageid)){
            String message = event.getMessage().getContentDisplay();
            String newString = "";

            for (int i = 0; i < message.length(); i++) {
                char a = message.charAt(i);
                int ascii = (int) a;

                if(ascii > 47 && ascii < 58){
                    newString = newString + a;
                }
            }

            if(MessageRecived.zahl != Integer.parseInt(newString)){
                event.getMessage().delete().queue();
            }
        }
    }
}

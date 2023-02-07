package listeners;

import main.Main;
import main.Start;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class SlashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!Main.INSTANCE.getSlashMan().perform(event.getName(), event)) {
            event.reply("Unbekannter Command").queue();
        }else{
            if(event.getName().equals("swf")) {
                event.reply("*SWF wird erstellt...*").setEphemeral(true).queue();
            }
        }
    }
}
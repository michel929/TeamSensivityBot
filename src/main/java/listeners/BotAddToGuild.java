package listeners;

import main.Start;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotAddToGuild extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        if(!event.getGuild().getId().equals(Start.GUILD_ID)){
            event.getGuild().leave().queue();
        }
    }
}

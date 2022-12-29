package listeners.dashboard.tag;

import mysql.dashboard.Tag;
import net.dv8tion.jda.api.events.channel.forum.ForumTagAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TagCreate extends ListenerAdapter {
    @Override
    public void onForumTagAdd(ForumTagAddEvent event) {
        Tag.insertTag(event.getTag());
    }
}

package dashboard.system.listeners.tag;

import mysql.dashboard.Tag;
import net.dv8tion.jda.api.events.channel.forum.ForumTagRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TagRemove extends ListenerAdapter {
    @Override
    public void onForumTagRemove(ForumTagRemoveEvent event) {
        Tag.removeTag(event.getTag());
    }
}

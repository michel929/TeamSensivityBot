package listeners.dashboard.tag;

import mysql.dashboard.Tag;
import net.dv8tion.jda.api.events.channel.forum.update.ForumTagUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TagUpdate extends ListenerAdapter {
    @Override
    public void onForumTagUpdateName(ForumTagUpdateNameEvent event) {
        Tag.updateTag(event.getTag());
    }
}

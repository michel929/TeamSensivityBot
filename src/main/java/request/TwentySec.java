package request;

import main.Start;
import messenger.object.Message;
import mysql.dashboard.Messenger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.TimerTask;

public class TwentySec extends TimerTask {

    @Override
    public void run() {
        //Status
        Api.getAPI("http://10.10.1.2:3001/api/push/HKZLfIWfU7uH1ctW1dBXcBAIpiOa9fzG?status=up&msg=OK&ping=");

        //send Message
        ArrayList<Message> messages = Messenger.getMessages();

        for (Message m : messages) {
            User member = Start.INSTANCE.getGuild().getMemberById(m.getDiscord_id()).getUser();

            if (member != null) {
                member.openPrivateChannel().queue((channel) -> {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle(m.getTitel());
                    eb.setColor(Color.decode(m.getColor()));
                    eb.setDescription(m.getText());
                    eb.setThumbnail(m.getIcon());

                    if(m.getLink() != null){
                        channel.sendMessageEmbeds(eb.build()).addActionRow(Button.link(m.getLink(), m.getLink_text())).queue();
                    }else {
                        channel.sendMessageEmbeds(eb.build()).queue();
                    }
                });
            }

            Messenger.removeMessage(m.getId());
        }
    }
}

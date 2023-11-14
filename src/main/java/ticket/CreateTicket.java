package ticket;

import buttons.types.ServerButton;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.awt.*;

public class CreateTicket implements ServerButton {

    @Override
    public void performCommand(ButtonInteractionEvent event) {
        Member m = event.getMember();
        Guild g = event.getGuild();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Role r = g.getRoleById("1174048530390859916");

        textChannel.createThreadChannel(m.getEffectiveName() + " Ticket").queue(threadChannel -> {

            threadChannel.addThreadMember(m).queue();


            for (Member member: g.getMembers()) {
                if(member.getRoles().contains(r)){
                    threadChannel.addThreadMember(member).queue();
                }
            }

            EmbedBuilder builder = new EmbedBuilder();

            builder.setColor(Color.decode("#2ecc71"));
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setTitle("TicketSystem");
            builder.setDescription("Wähle einen Server aus um den es geht.");

            threadChannel.sendMessageEmbeds(builder.build()).addActionRow().queue();
        });
    }
}

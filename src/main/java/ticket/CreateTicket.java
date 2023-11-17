package ticket;

import buttons.types.ServerButton;
import hosting.Funktionen;
import main.Main;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.awt.*;
import java.util.Collection;

public class CreateTicket implements ServerButton {

    @Override
    public void performCommand(ButtonInteractionEvent event) {
        Member m = event.getMember();
        Guild g = event.getGuild();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Role r = g.getRoleById("1174048530390859916");

        int userid = Funktionen.userExist(m.getId());
        if(userid != 0) {
            Collection c = Funktionen.getServer(String.valueOf(userid));

            if(!c.isEmpty()) {

                textChannel.createThreadChannel(m.getEffectiveName() + " Ticket").queue(threadChannel -> {

                    threadChannel.addThreadMember(m).queue();


                    for (Member member : g.getMembers()) {
                        if (member.getRoles().contains(r)) {
                            threadChannel.addThreadMember(member).queue();
                        }
                    }

                    EmbedBuilder builder = new EmbedBuilder();

                    builder.setColor(Color.decode("#2ecc71"));
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setTitle("TicketSystem");
                    builder.setDescription("Wähle einen Server aus um den es geht.");

                    StringSelectMenu menu = StringSelectMenu.create("choose-server" + threadChannel.getId()).addOptions(c).build();

                    threadChannel.sendMessageEmbeds(builder.build()).addActionRow(menu).queue();
                });

                event.isAcknowledged();
            }else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.RED);
                builder.setTitle("Du hast zurzeit keine Server");
                builder.setDescription("Du hast zurzeit keine Gameserver deswegen kannst du kein Ticket erstellen.");
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }
        }else {
            event.replyEmbeds(Main.INSTANCE.getEmbedMessages().getNoAccount()).setEphemeral(true).queue();
        }
    }
}

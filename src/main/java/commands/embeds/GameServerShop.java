package commands.embeds;

import commands.types.ServerCommand;
import gameserver.Produkt;
import main.Main;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;

public class GameServerShop implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event){
        Member m = event.getMember();
        Guild g = event.getGuild();

        if(m.getId().equals("422148236875137059")){
            ForumChannel forumChannel = g.getForumChannelById("1178340611540127826");

            for (Produkt p: gameserver.mysql.getProduktList()) {

                if(!Main.INSTANCE.getProductID().contains(p.getId())) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle(p.getName());
                    builder.setDescription(p.getDescription());
                    builder.setImage(p.getImage());
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setColor(Color.decode("#2ecc71"));
                    builder.addField("Price:", ">> " + p.getPoints() + " Sensivity Points oder " + p.getPrice() + "€", false);
                    builder.setFooter("ID: " + p.getId());

                    MessageCreateData createData = MessageCreateData.fromEmbeds(builder.build());
                    forumChannel.createForumPost(p.getName(), createData).setTags(forumChannel.getAvailableTagById(p.getTag_id())).addActionRow(Button.link("https://sensivity.shop", "Miete den Server")).queue();
                }
            }

        }
    }
}

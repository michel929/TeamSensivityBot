package commands.forum;

import commands.types.ServerCommand;
import main.Start;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Maintenance implements ServerCommand {
    @Override
    public void performCommand(Member m, TextChannel channel, Message message) throws ParseException {
        ForumChannel forum = Start.INSTANCE.getApi().getGuildById(Start.GUILD_ID).getForumChannelById(BotInfos.getBotInfos("announcements"));

        String[] args = message.getContentDisplay().split(" ");
        String description = "";

        for (String s: args) {
            description = description + s + " ";
        }

        EmbedBuilder builders = new EmbedBuilder();
        builders.setColor(Color.red);
        builders.setDescription(description);
        builders.setThumbnail("https://sensivity.team/bot/img/logo-transparent2.png");
        builders.setTitle(args[1]);

        MessageCreateBuilder builder = new MessageCreateBuilder();
        builder.addEmbeds(builders.build());
        builder.addContent("# Wartungsarbeiten bis zum " + args[2]);


        forum.createForumPost(args[1], builder.build()).setTags(forum.getAvailableTagsByName("Maintenance", true)).queue();

        EmbedBuilder builderss = new EmbedBuilder();
        builderss.setColor(Color.decode("#2ecc71"));
        builderss.setDescription("Nachricht wurde erfolgreich gepostet.");
        builderss.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
        builderss.setTitle("Wartungsarbeiten wurden erfolgreich eingeleitet.");

        channel.sendMessageEmbeds(builderss.build()).queue((messages) -> {
            messages.delete().queueAfter(10, TimeUnit.SECONDS);
            message.delete().queueAfter(10, TimeUnit.SECONDS);
        });
    }
}

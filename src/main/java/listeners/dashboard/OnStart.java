package listeners.dashboard;

import functions.GetGameRoles;
import listeners.MemberJoinChannel;
import main.Main;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.Tag;
import mysql.dashboard.UploadRole;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.forums.ForumTag;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import request.EveryDay;
import request.TwentySec;
import unendlichkeit.listeners.MessageRecived;

import java.util.List;
import java.util.Timer;

public class OnStart extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {

        Guild g = event.getJDA().getGuildById(Main.GUILD_ID);
        Main.INSTANCE.setGuild(g);
        Main.INSTANCE.setGameRoles(new GetGameRoles(g.getRoles()));

        new Timer().schedule(new TwentySec(), 0, 1000 * 20);
        new Timer().schedule(new EveryDay(), 0, 1000 * 60 * 60 * 24);

        List<Role> rollen = g.getRoles();

        //Update Rollen in Datenbank
        String hex = "";

        UploadRole.dropTable("discord_role");
        UploadRole.dropTable("user_role");

        for (Member m: g.getMembers()) {
            for (Role r: m.getRoles()) {
                PlayerInfos.insertRole(m.getId(), r.getId());
            }
        }

        for (Role r: rollen) {

                if(!(r.getColor() == null)) {
                    hex = String.format("#%02x%02x%02x", r.getColor().getRed(), r.getColor().getGreen(), r.getColor().getBlue());
                }else {
                    hex = "#95a5a6";
                }

                UploadRole.insertRole(r.getId(), hex, r.getName(), r.getPositionRaw());
        }


        List<VoiceChannel> voice = g.getVoiceChannels();
        List<VoiceChannel> chillVoice = g.getCategoryById(BotInfos.getBotInfos("chill_cat")).getVoiceChannels();

        //Get Alle User in Channel and OnlinePlayer
        int OnlineUser = 0;
        for (VoiceChannel v: voice) {
            MemberJoinChannel.add(v.getMembers());
            for(Member member: v.getMembers()) {
                OnlineUser++;
            }
        }

        //Get All ChillChannel
        for (VoiceChannel v: chillVoice) {
            if(!v.getId().equals(BotInfos.getBotInfos("chill_channel"))){
                if(v.getMembers().size() == 0){
                    v.delete().queue();
                }else {
                    MemberJoinChannel.channel.add(v);
                }
            }
        }

        //OnlinePlayer
        BotInfos.updateInfoInt("user_online", OnlineUser);

        //UserCount
        BotInfos.updateInfoInt("user_count", g.getMemberCount());


        if(g.getForumChannels().size() > 0) {
            for (ForumTag tag : g.getForumChannels().get(0).getAvailableTags()) {
                if (Tag.isExist(tag)) {
                    Tag.updateTag(tag);
                } else {
                    Tag.insertTag(tag);
                }
            }
        }

        TextChannel textChannel = g.getTextChannelById("1144648374520402163");
        textChannel.getHistory().retrievePast(1).queue(messages -> {
            String message = messages.get(0).getContentDisplay();
            System.out.println(message);
            String newString = "";

            for (int i = 0; i < message.length(); i++) {
                char a = message.charAt(i);
                int ascii = (int) a;

                if(ascii > 47 && ascii < 58){
                    newString = newString + a;
                }
            }

            if(!newString.isEmpty()) {
                MessageRecived.zahl = Long.parseLong(newString);
                MessageRecived.userid = messages.get(0).getAuthor().getId();
                MessageRecived.messageid = messages.get(0).getId();
            }else {
                MessageRecived.zahl = 0;
            }

            System.out.println("Die Aktuelle Zahl ist: " + MessageRecived.zahl);
        });
    }
}

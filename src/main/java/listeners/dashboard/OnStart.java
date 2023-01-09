package listeners.dashboard;

import listeners.MemberJoinChannel;
import main.Start;
import mysql.BotInfos;
import mysql.dashboard.Tag;
import mysql.dashboard.UploadRole;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.forums.ForumTag;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import request.OneMin;
import request.TwentySec;

import java.util.List;
import java.util.Timer;

public class OnStart extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        Guild g = Start.INSTANCE.getApi().getGuildById(Start.GUILD_ID);

        Start.INSTANCE.setGuild(g);

        new Timer().schedule(new OneMin(), 0, 1000 * 60);
        new Timer().schedule(new TwentySec(), 0, 1000 * 20);

        List<Role> rollen = g.getRoles();

        //Update Rollen in Datenbank
        String hex = "";

        UploadRole.dropTable();

        for (Role r: rollen) {

                if(!(r.getColor() == null)) {
                    hex = String.format("#%02x%02x%02x", r.getColor().getRed(), r.getColor().getGreen(), r.getColor().getBlue());
                }else {
                    hex = "#95a5a6";
                }

                UploadRole.insertRole(r.getId(), hex, r.getName(), r.getPositionRaw());
        }


        List<VoiceChannel> voice = g.getVoiceChannels();

        //Get Alle User in Channel and OnlinePlayer
        int OnlineUser = 0;
        for (VoiceChannel v: voice) {
            MemberJoinChannel.add(v.getMembers());
            for(Member member: v.getMembers()) {
                OnlineUser++;
            }
        }

        //Get All ChillChannel
        for (VoiceChannel v: voice) {
            if(v.getName().contains("Chill") && !v.getId().equals(BotInfos.getBotInfos("chill_channel"))){
                if(v.getMembers().size() == 0){
                    v.delete().queue();
                }else {
                    MemberJoinChannel.channel.add(v);
                    MemberJoinChannel.addI();
                }
            }
        }

        //OnlinePlayer
        BotInfos.updateInfoInt("user_online", OnlineUser);

        //UserCount
        BotInfos.updateInfoInt("user_count", g.getMemberCount());


        for (ForumTag tag: g.getForumChannels().get(0).getAvailableTags()) {
            if(Tag.isExist(tag)){
                Tag.updateTag(tag);
            }else {
                Tag.insertTag(tag);
            }
        }

    }
}

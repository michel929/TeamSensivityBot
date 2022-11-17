package commands;

import commands.types.PrivateCommand;
import main.Start;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;

import java.util.List;

public class UpdateCommand implements PrivateCommand {
    @Override
    public void performCommand(User m, PrivateChannel channel, Message message) {
        if (m.getId().equals("422148236875137059")){
            Guild g = Start.INSTANCE.getApi().getGuildById(Start.GUILD_ID);
            List<Member> members = g.getMembers();

            for (Member member: members) {
                User.Profile p = member.getUser().retrieveProfile().complete();

                String banner = "";

                if(p.getBannerUrl() != null) {
                    banner = p.getBannerUrl();
                }else {
                    banner = p.getAccentColor().toString();
                }

                if(PlayerInfos.isExist(member.getId(), "discord_id", "users")){
                        //TODO
                }else {
                    PlayerInfos.createAccount(member.getId(), member.getEffectiveName(), member.getAvatarUrl(), banner);

                    for (Role role: member.getRoles()) {
                        if(!PlayerInfos.isExist(role.getId(), "discord_role", "user_role")) {
                            PlayerInfos.insertRole(member.getId(), role.getId());
                        }
                    }
                }
            }
        }
    }
}

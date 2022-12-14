package commands;

import commands.types.ServerCommand;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.json.simple.parser.ParseException;

public class CreateAccount implements ServerCommand {
    @Override
    public void performCommand(Member m, TextChannel channel, Message message) throws ParseException {
        for (Member member: channel.getGuild().getMembers()) {
            if(!PlayerInfos.isExist(member.getId(), "discord_id", "users")){
                User.Profile p = member.getUser().retrieveProfile().complete();
                String banner = "";

                if(p.getBannerUrl() != null) {
                    banner = p.getBannerUrl();
                }else {
                    banner = p.getAccentColor().toString();
                }

                PlayerInfos.createAccount(member.getId(), member.getEffectiveName(), member.getEffectiveAvatarUrl(), banner);

                for (Role role: member.getRoles()) {
                    PlayerInfos.insertRole(member.getId(), role.getId());
                }
            }
        }
    }
}

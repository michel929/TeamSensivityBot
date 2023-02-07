package commands;

import commands.types.PrivateCommand;
import main.Main;
import main.Start;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;

import java.util.List;

public class UpdateCommand implements PrivateCommand {
    @Override
    public void performCommand(User m, PrivateChannel channel, Message message) {
        if (m.getId().equals("422148236875137059")){
            Guild g = Main.INSTANCE.getGuild();
            List<Member> members = g.getMembers();

            for (Member member: members) {
                if(!member.isPending() && !member.getId().equals("917069851191816262")) {
                    System.out.println(member.getEffectiveName());
                    User.Profile p = member.getUser().retrieveProfile().complete();

                    String banner = "";

                    if (p.getBannerUrl() != null) {
                        banner = p.getBannerUrl();
                    } else {
                        banner ="#" + Integer.toHexString((p.getAccentColorRaw() & 0xffffff) | 0x1000000).substring(1);
                    }

                    if (PlayerInfos.isExist(member.getId(), "discord_id", "users")) {
                        //TODO
                    } else {

                        PlayerInfos.createAccount(member.getId(), member.getUser().getAsTag(), member.getEffectiveAvatarUrl(), banner);

                        for (Role role : member.getRoles()) {
                            if (!PlayerInfos.isExist(role.getId(), "discord_role", "user_role")) {
                                PlayerInfos.insertRole(member.getId(), role.getId());
                            }
                        }

                        Role re = Main.INSTANCE.getGuild().getRoleById(BotInfos.getBotInfos("dashboard_role"));
                        Main.INSTANCE.getGuild().addRoleToMember(member, re).queue();
                    }
                }
            }
        }
    }
}

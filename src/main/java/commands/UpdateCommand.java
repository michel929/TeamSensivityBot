package commands;

import commands.types.ServerCommand;
import main.Main;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class UpdateCommand implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) {
        if (event.getMember().getId().equals("422148236875137059")){
            Guild g = Main.INSTANCE.getGuild();
            List<Member> members = g.getMembers();

            for (Member member: members) {
                if(!member.isPending() && !member.getId().equals("917069851191816262")) {
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

                        String name = member.getUser().getName();

                        name = name.replace(">", "");
                        name = name.replace("<", "");
                        name = name.replace(";", "");

                        PlayerInfos.createAccount(member.getId(), name, member.getEffectiveAvatarUrl(), banner);

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

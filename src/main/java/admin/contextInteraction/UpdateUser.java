package admin.contextInteraction;

import types.UserContextInteraction;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

import java.awt.*;

public class UpdateUser implements UserContextInteraction {
    @Override
    public void performCommand(UserContextInteractionEvent event) {
        if(event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            Member m = event.getTargetMember();

            User.Profile p = m.getUser().retrieveProfile().complete();
            String banner = "";

            if (p.getBannerUrl() != null) {
                banner = p.getBannerUrl();
            } else {
                banner = "#" + Integer.toHexString((p.getAccentColorRaw() & 0xffffff) | 0x1000000).substring(1);
            }

            if (PlayerInfos.isExist(m.getId(), "discord_id", "users")) {
                //TODO Player Update
                event.reply("Alle Einträge wurden aktualisiert.").setEphemeral(true).queue();
            } else {
                PlayerInfos.createAccount(m.getId(), m.getUser().getAsTag(), m.getEffectiveAvatarUrl(), banner);

                for (Role role : m.getRoles()) {
                    PlayerInfos.insertRole(m.getId(), role.getId());
                }

                event.reply("User wurde in der Datenbank angelegt.").setEphemeral(true).queue();
            }
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Keine Berechtigung dafür!");
            builder.setDescription("Du hast keine Berechtigung Daten zu verändern.");
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setColor(Color.RED);

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }
    }
}

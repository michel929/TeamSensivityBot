package slash.dashboard;

import main.Main;
import main.Start;
import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import slash.types.ServerSlash;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Connect implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if(BotInfos.getBotInfos("cmd_connect_on").equals("1")) {
            User.Profile p = event.getUser().retrieveProfile().complete();
            String banner = "";

            if (p.getBannerUrl() != null) {
                banner = p.getBannerUrl();
            } else {
                banner = p.getAccentColor().toString();
            }

            if (!PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {
                PlayerInfos.createAccount(event.getMember().getId(), event.getMember().getEffectiveName(), event.getMember().getEffectiveAvatarUrl(), banner);

                for (Role role : event.getMember().getRoles()) {
                    if (!PlayerInfos.isExist(role.getId(), "discord_role", "user_role")) {
                        PlayerInfos.insertRole(event.getMember().getId(), role.getId());
                    }
                }

                EmbedBuilder builder = new EmbedBuilder();
                builder.setAuthor("Team Sensivity");
                builder.setFooter("https://team-sensivity.de");
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setColor(Color.decode("#2ecc71"));
                builder.setTitle("Account wurde verknüpft.");
                builder.setDescription("Du hast erfolgreich deinen Account verknüpft.");

                event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                    message.delete().queueAfter(10, TimeUnit.SECONDS);
                });
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.red);
                builder.setDescription("Dein Account Existiert schon falls du du dein Passwort vergessen hast benutze folgenden Link. Falls du kein Account hast und dies ein Fehler vom System erstelle bitte ein Ticket.");
                builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
                builder.setTitle("Account existiert schon.");

                event.getChannel().sendMessageEmbeds(builder.build()).setActionRow(Button.link("https://sensivity.team/dashboard/passwort-vergessen.php", "Passwort vergessen..."), Button.link("https://ticketsystem.sensivity.team", "TicketSystem")).queue((message) -> {
                    message.delete().queueAfter(10, TimeUnit.SECONDS);
                });
            }
        }else {
            event.replyEmbeds(Main.INSTANCE.getEmbedMessages().getNotActive()).setEphemeral(true).queue();
        }
    }
}

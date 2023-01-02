package slash.dashboard;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import slash.types.ServerSlash;


import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Token implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {

        if(BotInfos.getBotInfos("cmd_token_on").equals("1")) {
            if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {
                String link = mysql.Token.uploadToken(event.getMember().getId());

                EmbedBuilder builder = new EmbedBuilder();
                builder.setAuthor("Team Sensivity");
                builder.setFooter("https://team-sensivity.de");
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setColor(Color.decode("#2ecc71"));
                builder.setTitle("Login Token");
                builder.setDescription("Benutze folgenden Token um dich anzumelden.");
                builder.addField("Token:", ">> " + link, false);

                event.replyEmbeds(builder.build()).setActionRow(Button.link("https://sensivity.team/dashboard/discord-login.php", "Zum LoginPanel")).setEphemeral(true).queue();

            }else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(Color.red);
                builder.setDescription("Dir konnte kein TeamSensivityAccount zugeordnet werden. Benutze **/connect** um einen Account zu erstellen.");
                builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
                builder.setTitle("Kein TeamSensivityAccount.");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();

            }
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.red);
            builder.setDescription("Dieser Befehl ist zurzeit deaktiviert. Versuche es später erneut.");
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setTitle("Befel ist deaktiviert.");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }
    }
}

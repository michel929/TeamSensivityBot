package slash;

import mysql.BotInfos;
import mysql.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import slash.types.ServerSlash;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Login implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {

        if(BotInfos.getBotInfos("cmd_login_on").equals("1")) {
            String link = Token.uploadTokenLink(event.getMember().getId());

            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor("Team Sensivity");
            builder.setFooter("https://team-sensivity.de");
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setColor(Color.decode("#2ecc71"));
            builder.setTitle("Login Link");
            builder.setDescription("Benutze folgenden Link oder Button um dich im Dashboard anzumelden.");
            builder.addField("Link", ">> " + link, false);

            event.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessageEmbeds(builder.build()).setActionRow(Button.link(link, "Login")).queue();
            });
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.red);
            builder.setDescription("Dieser Befehl ist zurzeit deaktiviert. Versuche es später erneut.");
            builder.setThumbnail("https://sensivity.team/bot/img/logo-transparent.png");
            builder.setTitle("Befel ist deaktiviert.");

            event.getChannel().sendMessageEmbeds(builder.build()).queue((message) -> {
                message.delete().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }
}

package slash.dashboard;

import main.Start;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import types.ServerSlash;

import java.awt.*;
public class Login implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {

        if(BotInfos.getBotInfos("cmd_login_on").equals("1")) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Team Sensivity Dashboard");
            builder.setColor(Color.decode("#2ecc71"));
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setDescription("Benutze den Button um dich im Team Sensivity Dashboard anzumelden.");
            event.replyEmbeds(builder.build()).setEphemeral(true).setActionRow(Button.link("https://discord.com/api/oauth2/authorize?client_id=917069851191816262&redirect_uri=https%3A%2F%2Fdashboard.sensivity.team%2Fconnect%2Fregister.php&response_type=code&scope=identify%20connections", "Zum Login")).queue();
        }else {
            event.replyEmbeds(Start.INSTANCE.getEmbedMessages().getNotActive()).setEphemeral(true).queue();
        }
    }
}

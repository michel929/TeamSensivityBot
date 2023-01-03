package slash.dashboard;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import slash.types.ServerSlash;

public class Profil implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if(PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {
            if (BotInfos.getBotInfos("cmd_account_on").equals("1")) {

                String steam = PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "steam_id","users");
                String riot = PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "summoner_name","users");

                TextInput steamid;
                TextInput riotid;

                if(steam != null) {
                    steamid = TextInput.create("steamid", "Steam64 ID", TextInputStyle.SHORT)
                            .setPlaceholder("Enter your Steam64ID")
                            .setMinLength(17)
                            .setValue(steam)
                            .setMaxLength(17) // or setRequiredRange(10, 100)
                            .build();
                }else {
                    steamid = TextInput.create("steamid", "Steam64 ID", TextInputStyle.SHORT)
                            .setPlaceholder("Enter your Steam64ID")
                            .setMinLength(17)
                            .setMaxLength(17) // or setRequiredRange(10, 100)
                            .build();
                }

                if(riot != null) {
                    riotid = TextInput.create("riotID", "Riot SummonerName", TextInputStyle.SHORT)
                            .setPlaceholder("Enter your SummonerName")
                            .setValue(riot)
                            .setMinLength(3)
                            .setMaxLength(16)
                            .build();
                }else {
                    riotid = TextInput.create("riotID", "Riot SummonerName", TextInputStyle.SHORT)
                            .setPlaceholder("Enter your SummonerName")
                            .setMinLength(3)
                            .setMaxLength(16)
                            .build();
                }

                Modal modal = Modal.create("steam", "Your TeamSensivityAccount")
                        .addActionRows(ActionRow.of(steamid, riotid))
                        .build();

                event.replyModal(modal).queue();
            }
        }
    }
}

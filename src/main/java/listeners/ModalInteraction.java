package listeners;

import mysql.BotInfos;
import mysql.Minecraft;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import riot.RiotAPI;
import steam.SteamApi;

import java.awt.*;

public class ModalInteraction extends ListenerAdapter {
    @Override
    public void onModalInteraction(ModalInteractionEvent event) {

        //SteamID
        if(event.getModalId().equals("steam")){
            String steamID = event.getValue("steamid").getAsString();

            if(SteamApi.isInGroup(steamID)){

                if(!PlayerInfos.isExist(steamID, "steam_id", "users")) {
                    PlayerInfos.updatePlayerInfos(event.getUser().getId(), "steam_id", steamID);

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Steam Account erfolgreich verknüpft...");
                    builder.setColor(Color.decode("#2ecc71"));
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setDescription("Du hast erfolgreich deinen SteamAccount verknüpft.");

                    event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                }else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Fehler beim Verbinden des Steam Accounts...");
                    builder.setColor(Color.red);
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setDescription("Deine SteamID hat schon jemand anderes hinterlegt. Erstellen Sie ein Ticket um dies zu beheben...");

                    event.replyEmbeds(builder.build()).addActionRow(Button.link("https://ticketsystem.sensivity.team", "TicketSystem")).setEphemeral(true).queue();
                }
            }else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Fehler beim Verbinden des Steam Accounts...");
                builder.setColor(Color.red);
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setDescription("Du bist nicht in der SteamGruppe. Du musst in der Team Sensivity Steam Gruppe sein...");

                event.replyEmbeds(builder.build()).addActionRow(Button.link("https://steamcommunity.com/groups/TeamSensivityy", "SteamGruppe")).setEphemeral(true).queue();
            }

            //Riot
        }else if(event.getModalId().equals("riot")){
            String puuid = RiotAPI.getPuuid(event.getValue("riotID").getAsString());
            String summonerid = RiotAPI.getSummonerID(event.getValue("riotID").getAsString());

            if(puuid != null && summonerid != null){
                if(!PlayerInfos.isExist(puuid, "riot_puuid", "users")) {
                    PlayerInfos.updatePlayerInfos(event.getUser().getId(), "riot_summoner_id", summonerid);
                    PlayerInfos.updatePlayerInfos(event.getUser().getId(), "summoner_name", event.getValue("riotID").getAsString());
                    PlayerInfos.updatePlayerInfos(event.getUser().getId(), "riot_puuid", puuid);

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Riot Account erfolgreich verknüpft...");
                    builder.setColor(Color.decode("#2ecc71"));
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setDescription("Du hast erfolgreich deinen RiotAccount verknüpft.");

                    event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                }else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Fehler beim Verbinden des Riot Accounts...");
                    builder.setColor(Color.red);
                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                    builder.setDescription("Deine RiotID hat schon jemand anderes hinterlegt. Erstellen Sie ein Ticket um dies zu beheben...");

                    event.replyEmbeds(builder.build()).addActionRow(Button.link("https://ticketsystem.sensivity.team", "TicketSystem")).setEphemeral(true).queue();
                }

            }else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Fehler beim Verbinden des Riot Accounts...");
                builder.setColor(Color.red);
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setDescription("Gib einen gültigen SummonerName ein...");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }
        }else if(event.getModalId().equals("minecraft")){
            String code = event.getValue("minecraftCode").getAsString();

            if(PlayerInfos.isExist(code, "code", "minecraft")) {
                String uuid = PlayerInfos.getInfo(code, "code", "uuid", "minecraft");

                PlayerInfos.updatePlayerInfos(event.getUser().getId(), "minecraft_uuid", uuid);

                if(!PlayerInfos.isExist(event.getUser().getId(), "discord_id", "hardcore")){
                    Minecraft.createHardcore(event.getUser().getId(), uuid);
                }else {
                    Minecraft.updatePlayer(event.getUser().getId(), "uuid", uuid);
                }

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Minecraft Account erfolgreich verknüpft...");
                builder.setColor(Color.decode("#2ecc71"));
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setDescription("Du hast erfolgreich deinen Minecraft Account verknüpft.");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Fehler beim Verbinden des Minecraft Accounts...");
                builder.setColor(Color.red);
                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                builder.setDescription("Gib einen gültigen Code ein...");

                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
            }
        }
    }
}

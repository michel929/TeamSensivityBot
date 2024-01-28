package request;

import main.Main;
import minecraft.Hardcore;
import mysql.Minecraft;
import mysql.dashboard.PlayerInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.TimerTask;

public class TwentySec extends TimerTask {
    @Override
    public void run() {
        //UserDeadInMinecraft
        TextChannel channel = Main.INSTANCE.getGuild().getTextChannelById("1060471251858100295");
        List<Member> members = Minecraft.getPlayer();

        for (Member m: members) {
            LocalDate localDate = Minecraft.getDate(m.getId());

            if(Hardcore.deadPlayer.containsKey(m.getId())){
                if (!localDate.equals(Hardcore.deadPlayer.get(m.getId()))){
                    Minecraft.updatePlayer(m.getId(),"dead",0);
                    Hardcore.deadPlayer.remove(m.getId());
                }
            }else {
                Hardcore.deadPlayer.put(m.getId(), localDate);

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle(m.getUser().getAsTag() + " ist gestorben!");
                builder.setDescription("Er kann erst Morgen wieder joinen. Er kann jedoch wiederbelebt werden... Das hat jedoch seinen Preis!!");
                builder.setThumbnail("https://mc-heads.net/avatar/" + PlayerInfos.getInfo(m.getId(), "discord_id", "uuid", "hardcore") + "/nohelm.png");
                builder.setColor(Color.RED);

                channel.sendMessageEmbeds(builder.build()).addActionRow(Button.success("revive" + m.getId(), "Spieler wiederbeleben")).queue();
            }
        }

        //Status
        Api.getAPI("https://status.sensivity.team/api/push/G6tfgGgM1q?status=up&msg=OK&ping=");
    }
}

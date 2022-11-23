package main;

import geheim.BotToken;
import listeners.*;
import listeners.dashboard.*;
import listeners.dashboard.role.*;
import mysql.BotInfos;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Start {

    public static Start INSTANCE;
    public static String GUILD_ID = BotInfos.getBotInfos("guild_id"), VERSION_ID = "2.1";

    private JDA api;
    private CommandManager cmdMan;
    private SlashManager slashMan;
    private ButtonManager buttonMan;

    public static void main(String[] args) {
        try {
            new Start();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public Start() throws LoginException, IllegalArgumentException {

        INSTANCE = this;

        api = JDABuilder.create(BotToken.token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)).build();
        api.getPresence().setStatus(OnlineStatus.ONLINE);
        api.getPresence().setActivity(Activity.competing("Version " + VERSION_ID));

        listeners();
        commands();

        System.out.println("Bot ist online!");

        this.cmdMan = new CommandManager();
        this.slashMan = new SlashManager();
        this.buttonMan = new ButtonManager();

        shutdown();
        BotToken.setToken();
    }

    public void shutdown(){
        new Thread(() -> {

            String line = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try{
                while ((line = reader.readLine()) != null){
                    if(line.equalsIgnoreCase("exit")){
                        if(api != null){
                            api.getPresence().setStatus(OnlineStatus.OFFLINE);
                            System.out.println("Bot ist offline!");
                        }
                    }else if (line.equalsIgnoreCase("start")){
                        if (api != null){
                            api.getPresence().setStatus(OnlineStatus.ONLINE);
                            System.out.println("Bot ist online!");
                        }
                    }else if(line.equalsIgnoreCase("shutdown")){
                        if (api != null){
                            api.shutdown();
                            System.out.println("Bot ist aus!");
                        }
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }

    private void listeners(){
        api.addEventListener(new SlashCommand());
        api.addEventListener(new CommandListener());
        api.addEventListener(new SelectionMenu());
        api.addEventListener(new ButtonListener());
        api.addEventListener(new ChannelRemove());

        api.addEventListener(new PlayerJoin());
        api.addEventListener(new PlayerLeave());
        api.addEventListener(new AcceptRules());

        api.addEventListener(new NameChange());
        api.addEventListener(new AvatarChange());
        api.addEventListener(new StatusChange());
        api.addEventListener(new BannerChange());

        api.addEventListener(new RoleCreate());
        api.addEventListener(new RoleDelete());
        api.addEventListener(new RoleUpdateName());
        api.addEventListener(new RoleUpdateColor());
        api.addEventListener(new RoleUpdatePosition());

        api.addEventListener(new OnStart());

        api.addEventListener(new MemberJoinChannel());
    }

    private void commands(){
        api.upsertCommand("connect", "Hiermit verbindest du deinen DiscordAccount mit dem Dashboard.").queue();
        api.upsertCommand("login", "Hiermit kannst du dich im Dashboard anmelden.").queue();
        api.upsertCommand("swf", "Hiermit kannst du eine SWF erstellen").addOption(OptionType.USER, "player2", "Hier kannst du einen Patz in der Gruppe für jemanden bestimmten reservieren.", false).addOption(OptionType.USER, "player3", "Hier kannst du einen Patz in der Gruppe für jemanden bestimmten reservieren.", false).addOption(OptionType.USER, "player4", "Hier kannst du einen Patz in der Gruppe für jemanden bestimmten reservieren.", false).queue();
        api.upsertCommand("token", "Hiermit kannst du ein Token für den Login beantragen.").queue();

        api.upsertCommand("play", "Hiermit kannst du Musik abspielen.").addOption(OptionType.STRING, "song", "Damit der Bot weiß was für ein Lied du hören möchtest...", true).queue();
        api.upsertCommand("volume", "Hiermit kannst du die Lautstärke einstellen.").addOption(OptionType.INTEGER, "volume", "z.B. 100, 10, 0", true).queue();
        api.upsertCommand("stop", "Hiermit kannst du den aktuellen Song stoppen.").queue();
    }

    public JDA getApi() {
        return api;
    }

    public CommandManager getCmdMan() {
        return cmdMan;
    }

    public SlashManager getSlashMan(){return slashMan;}

    public ButtonManager getButtonMan() {
        return buttonMan;
    }
}

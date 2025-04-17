package main;

import createChill.listeners.ChannelRemove;
import createChill.listeners.MemberJoinChannel;
import dashboard.system.listeners.*;
import functions.GetGameRoles;
import geheim.BotToken;
import listeners.*;
import dashboard.system.listeners.role.*;
import dashboard.system.listeners.role.user.UserGetRole;
import dashboard.system.listeners.role.user.UserRemoveRole;
import listeners.interactions.*;
import listeners.system.OnStart;
import main.manager.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import pointsSystem.listeners.onMessageReceived;
import templates.EmbedMessages;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TimeZone;

public class Start {

    public static String VERSION_ID = "3.0";
    public static Start INSTANCE;
    public static String DATABASE;
    public static String GUILD_ID;
    public static JDA api;

    private CommandManager cmdMan;
    private UserContextInteractionManager userManager;
    private SlashManager slashMan;
    private ButtonManager buttonMan;
    private ModalManager modalMan;
    private EmbedMessages embedMessages;
    private Guild guild;
    private GetGameRoles gameRoles;
    private ArrayList<String> ProductID;
    private Collection<VoiceChannel> voiceChannels;

    public static void main(String[] args) {
        DATABASE = "TeamSensivity";
        GUILD_ID = "773995277840941067";
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));

        INSTANCE = new Start();
    }

    public Start() {

        api = JDABuilder.create(BotToken.token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)).build();
        api.getPresence().setPresence(Activity.customStatus("STARTE BOT"), true);

        api.getPresence().setStatus(OnlineStatus.IDLE);

        System.out.println("Bot ist online!");

        this.cmdMan = new CommandManager();
        this.slashMan = new SlashManager();
        this.buttonMan = new ButtonManager();
        this.embedMessages = new EmbedMessages();
        this.userManager = new UserContextInteractionManager();
        this.modalMan = new ModalManager();
        this.ProductID = new ArrayList<>();
        this.voiceChannels = new ArrayList<>();

        api.setAutoReconnect(true);

        listeners();
    }

    private static void listeners() {
        api.addEventListener(new SlashCommand());
        api.addEventListener(new CommandListener());
        api.addEventListener(new ButtonListener());
        api.addEventListener(new ChannelRemove());
        api.addEventListener(new ModalInteraction());
        api.addEventListener(new UserContextInteraction());
        api.addEventListener(new UserRename());

        api.addEventListener(new PlayerJoin());
        api.addEventListener(new PlayerLeave());

        api.addEventListener(new UserUpdateName());
        api.addEventListener(new AvatarChange());
        api.addEventListener(new StatusChange());
        api.addEventListener(new BannerChange());

        //RoleManager
        api.addEventListener(new RoleCreate());
        api.addEventListener(new RoleDelete());
        api.addEventListener(new RoleUpdateName());
        api.addEventListener(new RoleUpdateColor());
        api.addEventListener(new RoleUpdatePosition());

        api.addEventListener(new UserGetRole());
        api.addEventListener(new UserRemoveRole());

        //System
        api.addEventListener(new OnStart());
        api.addEventListener(new OnShutdown());

        api.addEventListener(new MemberJoinChannel());
        //api.addEventListener(new OnBotDisconnect());

        //Watchroom
        api.addEventListener(new watchRoom.listeners.MemberJoinChannel());


        //PointSystem
        api.addEventListener(new pointsSystem.listeners.MemberJoinChannel());
        api.addEventListener(new onMessageReceived());
    }

    public void commands(Guild server) {

        System.out.println("Initialisiere Commands");

        //CONNECT
        Collection<SubcommandData> connect = new ArrayList<>();
        connect.add(new SubcommandData("steam", "Verbindet deinen Steam Account mit deinem TeamSensivity Account."));
        connect.add(new SubcommandData("riot", "Verbindet deinen Riot Account mit deinem TeamSensivity Account."));

        //BDAY
        Collection<SubcommandData> bday = new ArrayList<>();
        bday.add(new SubcommandData("info", "Zeigt dir deinen Bday an."));
        bday.add(new SubcommandData("add", "Füge deinen eigenen Bday hinzu.")
                .addOption(OptionType.INTEGER, "tag", "Wähle einen Tag.", true)
                .addOption(OptionType.INTEGER, "monat", "Wähle einen Monat", true)
                .addOption(OptionType.INTEGER, "jahr", "Wähle ein Jahr", true));
        bday.add(new SubcommandData("remove", "Entferne deinen Geburtstag."));
        bday.add(new SubcommandData("next", "Zeigt dir an wer als nächstes Geburtstag hat."));


        //PUNKTESYSTEM
        Collection<SubcommandData> subcommands = new ArrayList<>();
        subcommands.add(new SubcommandData("add", "Fügt dem User Punkte dazu.").addOption(OptionType.USER, "member", "Wähle hiermit einen anderen User aus.", true).addOption(OptionType.INTEGER, "punkte", "Die Anzahl an Punkten.", true));
        subcommands.add(new SubcommandData("remove", "Entfernt dem User Punkte.").addOption(OptionType.USER, "member", "Wähle hiermit einen anderen User aus.", true).addOption(OptionType.INTEGER, "punkte", "Die Anzahl an Punkten.", true));
        subcommands.add(new SubcommandData("set", "Setze eine bestimmte anzahl an Punktem einem User.").addOption(OptionType.USER, "member", "Wähle hiermit einen anderen User aus.", true).addOption(OptionType.INTEGER, "punkte", "Die Anzahl an Punkten.", true));
        subcommands.add(new SubcommandData("info", "Finde heraus wie viele Punkte du hast.").addOption(OptionType.USER, "member", "Wähle hiermit einen anderen User aus.", false));

        server.updateCommands().addCommands(
             Commands.slash("login", "Hiermit kannst du dich im Dashboard anmelden."),
             Commands.slash("token", "Hiermit kannst du ein Token für den Login beantragen."),
             Commands.slash("revoke", "Hiermit kannst du deinen TeamSensivityAccount löschen."),
             Commands.slash("lock", "Sorgt dafür das keiner dich mehr umbenennen."),
             Commands.slash("daily", "Hiermit sammelst du die Tägliche belohnung ein."),

             Commands.slash("connect", "Verbindet deinen Steam Account mit deinem TeamSensivity Account.").addSubcommands(connect),
             Commands.slash("bday", "Hiermit kannst du das Geburtstag Feature benutzen.").addSubcommands(bday),
             Commands.slash("points", "Hiermit kannst du deine Punkte einsehen.").addSubcommands(subcommands),

             Commands.context(Command.Type.USER, "Muten (1000 Points)"),
             Commands.context(Command.Type.USER, "Kick (1000 Points)"),
             //Commands.context(Command.Type.USER, "Move (1000 Points)"),
             Commands.context(Command.Type.USER, "Rename (500 Points)"),
             Commands.context(Command.Type.USER, "Create TeamSensivity Account").setDefaultPermissions(DefaultMemberPermissions.DISABLED)
        ).queue();
    }


    public JDA getApi() {
        return api;
    }

    public Guild getGuild() {
        return guild;
    }

    public EmbedMessages getEmbedMessages() {
        return embedMessages;
    }

    public CommandManager getCmdMan() {
        return cmdMan;
    }

    public SlashManager getSlashMan() {
        return slashMan;
    }

    public ButtonManager getButtonMan() {
        return buttonMan;
    }

    public ModalManager getModalMan() {
        return modalMan;
    }

    public UserContextInteractionManager getUserManager() {
        return userManager;
    }

    public void setGameRoles(GetGameRoles gameRoles) {
        this.gameRoles = gameRoles;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public ArrayList<String> getProductID() {
        return ProductID;
    }

    public void addProductID(String id){
        ProductID.add(id);
    }


    public Collection<VoiceChannel> getVoiceChannels() {
        return voiceChannels;
    }

    public void setVoiceChannels(Collection<VoiceChannel> voiceChannels) {
        this.voiceChannels = voiceChannels;
    }
}

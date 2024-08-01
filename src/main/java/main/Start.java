package main;

import com.lukaspradel.steamapi.webapi.client.SteamWebApiClient;
import createChill.listeners.ChannelRemove;
import createChill.listeners.MemberJoinChannel;
import dashboard.system.listeners.*;
import functions.GetGameRoles;
import geheim.BotToken;
import geheim.Steam;
import listeners.*;
import dashboard.system.listeners.role.*;
import dashboard.system.listeners.role.user.UserGetRole;
import dashboard.system.listeners.role.user.UserRemoveRole;
import dashboard.system.listeners.tag.TagRemove;
import dashboard.system.listeners.tag.TagCreate;
import dashboard.system.listeners.tag.TagUpdate;
import listeners.interactions.*;
import listeners.system.OnStart;
import main.manager.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.joda.time.LocalDateTime;
import pointsSystem.listeners.onMessageReceived;
import templates.EmbedMessages;
import unendlichkeit.listeners.MessageDelete;
import unendlichkeit.listeners.MessageRecived;
import unendlichkeit.listeners.MessageUpdate;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collection;

public class Start {

    public static String VERSION_ID = "2.7";

    private JDA api;
    private CommandManager cmdMan;
    private UserContextInteractionManager userManager;
    private SlashManager slashMan;
    private ButtonManager buttonMan;
    private ModalManager modalMan;
    private SteamWebApiClient steamApi;
    private EmbedMessages embedMessages;
    private Guild guild;
    private GetGameRoles gameRoles;
    private ArrayList<String> ProductID;

    public Start(boolean demo) throws LoginException, IllegalArgumentException {

        Main.INSTANCE = this;

        if (!demo) {
            api = JDABuilder.create(BotToken.token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)).build();
            api.getPresence().setPresence(Activity.customStatus("VERSION " + VERSION_ID), true);
        } else {
            api = JDABuilder.create(BotToken.demoToken, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)).build();
            api.getPresence().setActivity(Activity.customStatus("DEMO " + VERSION_ID));
        }

        api.getPresence().setStatus(OnlineStatus.ONLINE);

        listeners();

        System.out.println("Bot ist online!");

        this.cmdMan = new CommandManager();
        this.slashMan = new SlashManager();
        this.buttonMan = new ButtonManager();
        this.steamApi = new SteamWebApiClient.SteamWebApiClientBuilder(Steam.apiKey).build();
        this.embedMessages = new EmbedMessages();
        this.userManager = new UserContextInteractionManager();
        this.modalMan = new ModalManager();
        this.ProductID = new ArrayList<>();

        api.setAutoReconnect(true);
    }

    private void listeners() {
        api.addEventListener(new SlashCommand());
        api.addEventListener(new CommandListener());
        api.addEventListener(new SelectionMenu());
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

        //TagSystem
        api.addEventListener(new TagRemove());
        api.addEventListener(new TagCreate());
        api.addEventListener(new TagUpdate());

        //System
        api.addEventListener(new OnStart());
        api.addEventListener(new OnShutdown());

        api.addEventListener(new MessageRecived());
        api.addEventListener(new MessageDelete());
        api.addEventListener(new MessageUpdate());

        api.addEventListener(new MemberJoinChannel());
        api.addEventListener(new OnBotDisconnect());

        //Watchroom
        api.addEventListener(new watchRoom.listeners.MemberJoinChannel());


        //PointSystem
        api.addEventListener(new pointsSystem.listeners.MemberJoinChannel());
        api.addEventListener(new onMessageReceived());
    }

    public void commands() {

        api.upsertCommand("login", "Hiermit kannst du dich im Dashboard anmelden.").queue();
        api.upsertCommand("token", "Hiermit kannst du ein Token für den Login beantragen.").queue();
        api.upsertCommand("revoke", "Hiermit kannst du deinen TeamSensivityAccount löschen.").queue();
        api.upsertCommand("lock", "Sorgt dafür das keiner dich mehr umbenennen.").queue();

        //Connect
        Collection<SubcommandData> connect = new ArrayList<>();
        connect.add(new SubcommandData("steam", "Verbindet deinen Steam Account mit deinem TeamSensivity Account."));
        connect.add(new SubcommandData("riot", "Verbindet deinen Riot Account mit deinem TeamSensivity Account."));
        api.upsertCommand("connect", "Hiermit verbindest du deine Socials mit deinem Account.").addSubcommands(connect).queue();

        //BDAY
        Collection<SubcommandData> bday = new ArrayList<>();
        bday.add(new SubcommandData("info", "Zeigt dir deinen Bday an."));
        bday.add(new SubcommandData("add", "Füge deinen eigenen Bday hinzu.")
                .addOption(OptionType.INTEGER, "tag", "Wähle einen Tag.", true)
                .addOption(OptionType.INTEGER, "monat", "Wähle einen Monat", true)
                .addOption(OptionType.INTEGER, "jahr", "Wähle ein Jahr", true));
        bday.add(new SubcommandData("remove", "Entferne deinen Geburtstag."));
        bday.add(new SubcommandData("next", "Zeigt dir an wer als nächstes Geburtstag hat."));
        api.upsertCommand("bday", "Hiermit kannst du das Geburtstag Feature benutzen.").addSubcommands(bday).queue();

        //PunkteSystem
        Collection<SubcommandData> subcommands = new ArrayList<>();
        subcommands.add(new SubcommandData("add", "Fügt dem User Punkte dazu.").addOption(OptionType.USER, "member", "Wähle hiermit einen anderen User aus.", true).addOption(OptionType.INTEGER, "punkte", "Die Anzahl an Punkten.", true));
        subcommands.add(new SubcommandData("remove", "Entfernt dem User Punkte.").addOption(OptionType.USER, "member", "Wähle hiermit einen anderen User aus.", true).addOption(OptionType.INTEGER, "punkte", "Die Anzahl an Punkten.", true));
        subcommands.add(new SubcommandData("set", "Setze eine bestimmte anzahl an Punktem einem User.").addOption(OptionType.USER, "member", "Wähle hiermit einen anderen User aus.", true).addOption(OptionType.INTEGER, "punkte", "Die Anzahl an Punkten.", true));
        subcommands.add(new SubcommandData("info", "Finde heraus wie viele Punkte du hast.").addOption(OptionType.USER, "member", "Wähle hiermit einen anderen User aus.", false));
        api.upsertCommand("points", "Hiermit kannst du deine Punkte einsehen.").addSubcommands(subcommands).queue();

        api.upsertCommand("daily", "Hiermit sammelst du die Tägliche belohnung ein.").queue();

        //MusikBot
        api.upsertCommand("play", "Hiermit kannst du Musik abspielen.").addOption(OptionType.STRING, "song", "Damit der Bot weiß was für ein Lied du hören möchtest...", true).queue();
        api.upsertCommand("volume", "Hiermit kannst du die Lautstärke einstellen.").addOption(OptionType.INTEGER, "volume", "z.B. 100, 10, 0", true).queue();
        api.upsertCommand("stop", "Hiermit kannst du den aktuellen Song stoppen.").queue();
        api.upsertCommand("skip", "Hiermit kannst du den aktuellen Song skippen.").queue();

        //UserCommands
        api.updateCommands().addCommands(
                Commands.context(Command.Type.USER, "Muten (1000 Points)"),
                Commands.context(Command.Type.USER, "Kick (1000 Points)"),
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


}

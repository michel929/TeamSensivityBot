package main;

import com.lukaspradel.steamapi.webapi.client.SteamWebApiClient;
import functions.GetGameRoles;
import games.lobby.SelectSave;
import geheim.BotToken;
import geheim.Steam;
import listeners.*;
import listeners.dashboard.*;
import listeners.dashboard.role.*;
import listeners.dashboard.role.user.UserGetRole;
import listeners.dashboard.role.user.UserRemoveRole;
import listeners.dashboard.tag.TagRemove;
import listeners.dashboard.tag.TagCreate;
import listeners.dashboard.tag.TagUpdate;
import main.manager.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import pets.tiere.Pets;
import templates.EmbedMessages;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collection;

public class Start {

    public static String VERSION_ID = "2.5";

    private JDA api;
    private Pets pets;
    private CommandManager cmdMan;
    private UserContextInteractionManager userManager;
    private SlashManager slashMan;
    private ButtonManager buttonMan;
    private ModalManager modalMan;
    private SteamWebApiClient steamApi;
    private EmbedMessages embedMessages;
    private SelectSave selectSave;
    private Guild guild;
    private GetGameRoles gameRoles;

    public Start(boolean demo) throws LoginException, IllegalArgumentException {

        Main.INSTANCE = this;

        if (!demo) {
            api = JDABuilder.create(BotToken.token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)).build();
            api.getPresence().setActivity(Activity.competing("Version " + VERSION_ID));
        } else {
            api = JDABuilder.create(BotToken.demoToken, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)).build();
            api.getPresence().setActivity(Activity.competing("DEMO " + VERSION_ID));
        }

        api.getPresence().setStatus(OnlineStatus.ONLINE);

        listeners();
        commands();

        System.out.println("Bot ist online!");

        this.cmdMan = new CommandManager();
        this.slashMan = new SlashManager();
        this.buttonMan = new ButtonManager();
        this.steamApi = new SteamWebApiClient.SteamWebApiClientBuilder(Steam.apiKey).build();
        this.embedMessages = new EmbedMessages();
        this.userManager = new UserContextInteractionManager();
        this.modalMan = new ModalManager();
        this.selectSave = new SelectSave();
        this.pets = new Pets();

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

        api.addEventListener(new RoleCreate());
        api.addEventListener(new RoleDelete());
        api.addEventListener(new RoleUpdateName());
        api.addEventListener(new RoleUpdateColor());
        api.addEventListener(new RoleUpdatePosition());

        api.addEventListener(new UserGetRole());
        api.addEventListener(new UserRemoveRole());

        api.addEventListener(new TagRemove());
        api.addEventListener(new TagCreate());
        api.addEventListener(new TagUpdate());

        api.addEventListener(new OnStart());
        api.addEventListener(new OnShutdown());

        api.addEventListener(new MemberJoinChannel());
        // api.addEventListener(new PlayerMoved());
        //api.addEventListener(new PlayerMute());
    }

    private void commands() {
        api.upsertCommand("connect", "Hiermit verbindest du deinen DiscordAccount mit dem Dashboard.").queue();
        api.upsertCommand("login", "Hiermit kannst du dich im Dashboard anmelden.").queue();
        api.upsertCommand("swf", "Hiermit kannst du eine SWF erstellen").addOption(OptionType.USER, "player2", "Hier kannst du einen Patz in der Gruppe für jemanden bestimmten reservieren.", false).addOption(OptionType.USER, "player3", "Hier kannst du einen Patz in der Gruppe für jemanden bestimmten reservieren.", false).addOption(OptionType.USER, "player4", "Hier kannst du einen Patz in der Gruppe für jemanden bestimmten reservieren.", false).queue();
        api.upsertCommand("token", "Hiermit kannst du ein Token für den Login beantragen.").queue();
        api.upsertCommand("revoke", "Hiermit kannst du deinen TeamSensivityAccount löschen.").queue();
        api.upsertCommand("lock", "Sorgt dafür das keiner dich mehr umbenennen.").queue();


        api.upsertCommand("minecraft", "Hiermit kannst du deinen MinecraftAccount verbinden").queue();

        Collection<SubcommandData> bday = new ArrayList<>();
        bday.add(new SubcommandData("info", "Zeigt dir eine Liste aller Geburtstage an."));
        bday.add(new SubcommandData("add", "Füge deinen eigenen Bday hinzu.")
                .addOption(OptionType.INTEGER, "tag", "Wähle einen Tag.", true)
                .addOption(OptionType.INTEGER, "monat", "Wähle einen Monat", true)
                .addOption(OptionType.INTEGER, "jahr", "Wähle ein Jahr", true));
        bday.add(new SubcommandData("remove", "Entferne deinen Geburtstag."));
        bday.add(new SubcommandData("next", "Zeigt dir an wer als nächstes Geburtstag hat."));
        api.upsertCommand("bday", "Hiermit kannst du das Geburtstag Feature benutzen.").addSubcommands(bday).queue();

        Collection<SubcommandData> subcommands = new ArrayList<>();
        subcommands.add(new SubcommandData("add", "Fügt dem User Punkte dazu.").addOption(OptionType.USER, "member", "Wähle hiermit einen anderen User aus.", true).addOption(OptionType.INTEGER, "punkte", "Die Anzahl an Punkten.", true));
        subcommands.add(new SubcommandData("remove", "Entfernt dem User Punkte.").addOption(OptionType.USER, "member", "Wähle hiermit einen anderen User aus.", true).addOption(OptionType.INTEGER, "punkte", "Die Anzahl an Punkten.", true));
        subcommands.add(new SubcommandData("set", "Setze eine bestimmte anzahl an Punktem einem User.").addOption(OptionType.USER, "member", "Wähle hiermit einen anderen User aus.", true).addOption(OptionType.INTEGER, "punkte", "Die Anzahl an Punkten.", true));
        subcommands.add(new SubcommandData("info", "Finde heraus wie viele Punkte du hast.").addOption(OptionType.USER, "member", "Wähle hiermit einen anderen User aus.", false));
        api.upsertCommand("points", "Hiermit kannst du deine Punkte einsehen.").addSubcommands(subcommands).queue();

        api.upsertCommand("daily", "Hiermit sammelst du die Tägliche belohnung ein.").queue();

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

    public SteamWebApiClient getSteamApi() {
        return steamApi;
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

    public GetGameRoles getGameRoles() {
        return gameRoles;
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

    public SelectSave getSelectSave() {
        return selectSave;
    }

    public Pets getPets() {
        return pets;
    }
}

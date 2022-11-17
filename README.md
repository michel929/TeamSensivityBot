# TeamSensivityBot [Entwurf]
[![image](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)](https://discord.gg/eC7Jcg7Nzt) [![Website](https://img.shields.io/badge/website-000000?style=for-the-badge&logo=hyperledger&logoColor=white)](https://sensivity.team)

## Plugin Support
### Vorraussetzungen
Dein Plugin wird in Java programmiert und du benutzt die [Java Discord API](https://github.com/DV8FromTheWorld/JDA)
### Schritt 1:
Du brauchst die [JDA Plugin Datei](https://github.com/Team-Sensivity/TeamSensivityBot/tree/Version-2.0/pluginsystem) (Die findest du in der Repo in dem Ordner Pluginsystem)
Zudem brauchst du die [Java Discord API](https://github.com/DV8FromTheWorld/JDA). Diese Datei bindest du so ein als würdest du einen DiscordBot mit Java programmieren.

### Schritt 2:
Du fügst die JDA Plugin Datei als Modul hinzu.

![image](https://github.com/Team-Sensivity/TeamSensivityBot/blob/main/pluginsystem/module.png)

### Schritt 3:
Jetzt erstelle deine MainClasse, welche wie folgt aussehen sollte:
```java
import interfaces.PluginInterface;
import interfaces.PluginManager;

public class Main implements PluginInterface {
    private PluginManager pluginManager;
    @Override
    public boolean start() {
        System.out.println("Plugin wurde geladen");
        <Dein Code (Am besten eine Methode)>
        return true;
    }

    @Override
    public boolean stop() {
        System.out.println("Plugin wurde gestoppt");
        return true;
    }

    @Override
    public void setPluginManager(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }
}
```

### Schritt 4:
Dein Projekt exportieren und auf dem Discordserver im Channel PluginSystem einreichen.


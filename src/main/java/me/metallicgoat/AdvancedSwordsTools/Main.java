package me.metallicgoat.AdvancedSwordsTools;

import me.metallicgoat.AdvancedSwordsTools.tweaks.*;
import me.metallicgoat.AdvancedSwordsTools.utils.ConfigManager;
import me.metallicgoat.AdvancedSwordsTools.utils.ConfigUpdater;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main extends JavaPlugin {

    private static Main instance;
    private static ConfigManager configManager;

    private final ConsoleCommandSender console = Bukkit.getConsoleSender();
    private final Server server = getServer();
    public String sversion;

    public void onEnable() {

        registerEvents();
        instance = this;
        configManager = new ConfigManager();
        PluginDescriptionFile pdf = this.getDescription();

        int pluginId = 11780;
        Metrics metrics = new Metrics(this, pluginId);

        loadConfig();

        log(
                "------------------------------",
                pdf.getName() + " For MBedwars",
                "By: " + pdf.getAuthors(),
                "Version: " + pdf.getVersion(),
                "------------------------------"
        );

    }

    private void registerEvents() {
        PluginManager manager = this.server.getPluginManager();
        manager.registerEvents(new AlwaysSword(), this);
        manager.registerEvents(new AntiChest(), this);
        manager.registerEvents(new AntiDrop(), this);
        manager.registerEvents(new ReplaceSwordOnBuy(), this);
        manager.registerEvents(new SwordDrop(), this);
        manager.registerEvents(new ToolBuy(), this);
        manager.registerEvents(new OrderedSwordBuy(), this);
    }


    public static Main getInstance() {
        return instance;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public ConsoleCommandSender getConsole() {
        return console;
    }

    private void log(String ...args) {
        for(String s : args)
            getLogger().info(s);
    }

    private void loadConfig(){
        saveDefaultConfig();
        File configFile = new File(getDataFolder(), "config.yml");

        try {
            ConfigUpdater.update(this, "config.yml", configFile, Arrays.asList("Nothing", "here"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        reloadConfig();
    }
}

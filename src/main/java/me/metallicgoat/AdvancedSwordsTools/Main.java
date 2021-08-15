package me.metallicgoat.AdvancedSwordsTools;

import me.metallicgoat.AdvancedSwordsTools.tweaks.*;
import me.metallicgoat.AdvancedSwordsTools.utils.ConfigManager;
import me.metallicgoat.AdvancedSwordsTools.utils.ConfigUpdater;
import me.metallicgoat.AdvancedSwordsTools.versionsupport.Legacy;
import me.metallicgoat.AdvancedSwordsTools.versionsupport.Newer;
import me.metallicgoat.AdvancedSwordsTools.versionsupport.Versions;
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
    public Versions versions;

    public void onEnable() {
        if(!setupManager()){
            log("Failed to start. Unsupported server version.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

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
        manager.registerEvents(new SwordBuy(), this);
        manager.registerEvents(new SwordDrop(), this);
        manager.registerEvents(new ToolBuy(), this);
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

    private boolean setupManager(){
        sversion = "N/A";
        try {
            sversion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }

        switch (sversion) {
            case "v1_8_R3":
                versions = new Legacy();
                break;
            case "v1_9_R2":
                versions = new Legacy();
                break;
            case "v1_10_R1":
                versions = new Legacy();
                break;
            case "v1_11_R1":
                versions = new Legacy();
                break;
            case "v1_12_R1":
                versions = new Legacy();
                break;
            case "v1_13_R2":
                versions = new Newer();
                break;
            case "v1_14_R1":
                versions = new Newer();
                break;
            case "v1_15_R1":
                versions = new Newer();
                break;
            case "v1_16_R3":
                versions = new Newer();
                break;
            case "v1_17_R1":
                versions = new Newer();
                break;
        }
        return versions != null;
    }
}

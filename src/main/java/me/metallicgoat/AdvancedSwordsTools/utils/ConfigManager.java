package me.metallicgoat.AdvancedSwordsTools.utils;

import me.metallicgoat.AdvancedSwordsTools.Main;

import java.util.List;

public class ConfigManager implements Config {

    Main plugin = Main.getInstance();

    @Override
    public List<String> getAntiChest() {
        return plugin.getConfig().getStringList("Anti-Chest");
    }

    @Override
    public List<String> getAntiDropList() {
        return plugin.getConfig().getStringList("Anti-Drop.list");
    }

    @Override
    public boolean getAntiDrop() {
        return plugin.getConfig().getBoolean("Anti-Drop.enabled");
    }

    @Override
    public boolean getAdvancedSwordDrop() {
        return plugin.getConfig().getBoolean("Advanced-Sword-Drop.enabled");
    }

    @Override
    public List<String> getSwordDropList() {
        return plugin.getConfig().getStringList("Advanced-Sword-Drop.list");
    }

    @Override
    public boolean getSwordReplace() {
        return plugin.getConfig().getBoolean("Replace-Sword-On-Buy.enabled");
    }

    @Override
    public boolean getSwordReplaceType() {
        return plugin.getConfig().getBoolean("Replace-Sword-On-Buy.all-type");
    }


    @Override
    public boolean getToolBuy() {
        return plugin.getConfig().getBoolean("Advanced-Tool-Replacement.enabled");
    }

    @Override
    public String getToolBuyProblem() {
        return plugin.getConfig().getString("Advanced-Tool-Replacement.problem");
    }
}

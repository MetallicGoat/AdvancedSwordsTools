package me.metallicgoat.AdvancedSwordsTools.versionsupport;

import org.bukkit.Material;

public class Legacy implements Versions {
    @Override
    public Material getWoodSword() {
        return Material.valueOf("WOOD_SWORD");
    }
}
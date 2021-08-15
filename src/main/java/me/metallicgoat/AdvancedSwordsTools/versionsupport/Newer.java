package me.metallicgoat.AdvancedSwordsTools.versionsupport;

import org.bukkit.Material;

public class Newer implements Versions {
    @Override
    public Material getWoodSword() {
        return Material.valueOf("WOODEN_SWORD");
    }
}
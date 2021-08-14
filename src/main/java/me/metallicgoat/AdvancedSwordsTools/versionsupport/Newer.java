package me.metallicgoat.AdvancedSwordsTools.versionsupport;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class Newer implements Versions {
    @Override
    public Material getWoodSword() {
        return Material.valueOf("WOODEN_SWORD");
    }
}
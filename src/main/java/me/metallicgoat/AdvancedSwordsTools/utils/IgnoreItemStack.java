package me.metallicgoat.AdvancedSwordsTools.utils;

import me.metallicgoat.AdvancedSwordsTools.Main;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class IgnoreItemStack {

    public static boolean isNotToIgnore(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        AtomicBoolean isNotToIgnore = new AtomicBoolean(true);
        if(meta != null) {
            getIgnoreList().forEach(s -> {
                if(s.equals(ChatColor.stripColor(meta.getDisplayName())) && !s.equals("")) {
                    isNotToIgnore.set(false);
                }
            });
        }
        return isNotToIgnore.get();
    }

    public static boolean isNotToIgnore(String name){
        AtomicBoolean isNotToIgnore = new AtomicBoolean(true);
        getIgnoreList().forEach(s -> {
            String formatted = ChatColor.translateAlternateColorCodes('&', s);
            if(formatted.equals(name) && !s.equals("")){
                isNotToIgnore.set(false);
            }
        });
        return isNotToIgnore.get();
    }

    private static List<String> getIgnoreList(){
        return Main.getConfigManager().getIgnoreList();
    }
}

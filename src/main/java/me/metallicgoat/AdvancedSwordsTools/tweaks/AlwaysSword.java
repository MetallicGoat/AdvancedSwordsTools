package me.metallicgoat.AdvancedSwordsTools.tweaks;

import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.arena.ArenaStatus;
import me.metallicgoat.AdvancedSwordsTools.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AlwaysSword implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Main plugin = Main.getInstance();
        final Player p = (Player) e.getWhoClicked();
        Arena arena = BedwarsAPI.getGameAPI().getArenaByPlayer(p);
        if (arena != null && enabled()) {
            if(p.getGameMode() != GameMode.SPECTATOR && arena.getStatus() == ArenaStatus.RUNNING) {
                Bukkit.getServer().getScheduler().runTaskLater((plugin), () -> {
                    final Inventory pi = p.getInventory();
                    final int i = getSwords(p);
                    if (i >= 2) {
                        //if someone has an extra wood sword, remove it
                        if (hasGoodSword(p)) {
                            if (pi.contains(plugin.versions.getWoodSword())) {
                                pi.remove(plugin.versions.getWoodSword());
                            }
                            // If someone somehow gets two wood swords, only remove one
                        } else {
                            for (ItemStack s : pi.getContents()) {
                                if (s != null && s.getType().equals(plugin.versions.getWoodSword())) {
                                    s.setType(Material.AIR);
                                    break;
                                }
                            }
                        }
                        // Give player a wood sword if they don't have one
                    } else if (i == 0 &&
                            !e.getCurrentItem().getType().name().endsWith("SWORD") &&
                            !e.getCursor().getType().name().endsWith("SWORD")) {
                        pi.addItem(new ItemStack(plugin.versions.getWoodSword()));
                    }
                }, 25L);
            }
        }
    }

    private boolean enabled() {
        return Main.getConfigManager().getAdvancedSwordDrop();
    }
    // checks how many swords a player has
    private int getSwords(Player player) {
        int count = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if(item != null)
                if (item.getType().name().endsWith("SWORD")){
                    count++;
                }
        }
        return count;
    }
    // returns true if a player has a sword that is better than wood
    private boolean hasGoodSword(Player player){
        Main plugin = Main.getInstance();
        final Inventory pi = player.getInventory();
        for(ItemStack s:pi.getContents()){
            if(s != null){
                if(s.getType().name().endsWith("SWORD")){
                    if(!s.equals(new ItemStack(plugin.versions.getWoodSword()))){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

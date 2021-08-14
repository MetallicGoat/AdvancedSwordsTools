package me.metallicgoat.AdvancedSwordsTools.tweaks;

import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.arena.Arena;
import me.metallicgoat.AdvancedSwordsTools.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SwordDrop implements Listener {

    @EventHandler
    public void giveSwordOnDrop(PlayerDropItemEvent e){
        Main plugin = Main.getInstance();
        Player p = e.getPlayer();
        Arena arena = BedwarsAPI.getGameAPI().getArenaByPlayer(p);
        PlayerInventory pi = p.getInventory();
        if(enabled()) {
            if (arena != null) {
                ItemStack is = new ItemStack(plugin.versions.getWoodSword());
                if (e.getItemDrop().getItemStack().getType() == plugin.versions.getWoodSword()) {
                    e.setCancelled(true);
                }
                if (getSwords(e.getPlayer()) == 0 && e.getItemDrop().getItemStack().getType() != plugin.versions.getWoodSword()) {
                    final ItemStack item = e.getItemDrop().getItemStack();
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("Wooden Sword");
                    is.setItemMeta(meta);
                    //tries to put sword in slot player dropped sword from
                    if(pi.getItem(pi.getHeldItemSlot()) == null) {
                        pi.setItem(pi.getHeldItemSlot(), is);
                    }else{
                        pi.addItem(is);
                    }
                }
            }
        }
    }

    @EventHandler
    public void replaceSwordOnCollect(PlayerPickupItemEvent e){
        Main plugin = Main.getInstance();
        Player p = e.getPlayer();
        Arena arena = BedwarsAPI.getGameAPI().getArenaByPlayer(p);
        PlayerInventory pi = p.getInventory();
        ItemStack sword = e.getItem().getItemStack();
        if(enabled()) {
            if(arena != null) {
                if(swordList().contains(e.getItem().getItemStack().getType().name())) {
                    if(pi.contains(plugin.versions.getWoodSword())) {
                        pi.remove(plugin.versions.getWoodSword());
                    }
                    e.setCancelled(true);
                    pi.addItem(sword);
                    e.getItem().remove();
                }
            }
        }
    }

    private int getSwords(Player player) {
        int count = 0;
        for(ItemStack item : player.getInventory().getContents()) {
            if(item != null)
                if(item.getType().name().endsWith("SWORD")){
                    count++;
                }
        }
        return count;
    }

    private boolean enabled() {
        return Main.getConfigManager().getAdvancedSwordDrop();
    }
    private List<String> swordList() {
        return Main.getConfigManager().getSwordDropList();
    }
}

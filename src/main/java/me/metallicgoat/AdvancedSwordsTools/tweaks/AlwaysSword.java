package me.metallicgoat.AdvancedSwordsTools.tweaks;

import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.arena.ArenaStatus;
import me.metallicgoat.AdvancedSwordsTools.Main;
import me.metallicgoat.AdvancedSwordsTools.utils.IgnoreItemStack;
import me.metallicgoat.AdvancedSwordsTools.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class AlwaysSword implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        assert XMaterial.WOODEN_SWORD.parseMaterial() != null;

        Main plugin = Main.getInstance();
        final Player p = (Player) e.getWhoClicked();
        Arena arena = BedwarsAPI.getGameAPI().getArenaByPlayer(p);


        if (arena != null && enabled()) {
            if(p.getGameMode() != GameMode.SPECTATOR && arena.getStatus() == ArenaStatus.RUNNING) {
                Bukkit.getServer().getScheduler().runTaskLater((plugin), () -> {
                    final Inventory pi = p.getInventory();
                    final int i = getSwords(p);
                    //TODO: OPTIMIZE
                    if (i >= 2) {
                        //if someone has an extra wood sword, remove it
                        if (hasGoodSword(p)) {
                            for (ItemStack s : pi.getContents()) {
                                if (s != null && s.getType().equals(XMaterial.WOODEN_SWORD.parseMaterial()) && IgnoreItemStack.isNotToIgnore(s)) {
                                    pi.remove(s);
                                }
                            }
                            // If someone somehow gets two wood swords, only remove one
                        } else {
                            for (ItemStack s : pi.getContents()) {
                                if (s != null && s.getType().equals(XMaterial.WOODEN_SWORD.parseMaterial()) && IgnoreItemStack.isNotToIgnore(s)) {
                                    s.setType(Material.AIR);
                                    break;
                                }
                            }
                        }
                        // Give player a wood sword if they don't have one
                    } else if (i == 0 &&
                            (e.getCurrentItem() == null || e.getCurrentItem() != null && !e.getCurrentItem().getType().name().endsWith("SWORD") && IgnoreItemStack.isNotToIgnore(e.getCurrentItem())) &&
                            (e.getCursor() == null || e.getCursor() != null && !e.getCursor().getType().name().endsWith("SWORD") && IgnoreItemStack.isNotToIgnore(e.getCursor()))) {
                        pi.addItem(new ItemStack(XMaterial.WOODEN_SWORD.parseMaterial()));
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

        final Inventory pi = player.getInventory();
        for(ItemStack s:pi.getContents()){
            if(s != null){
                if(s.getType().name().endsWith("SWORD")){
                    if(!s.equals(new ItemStack(Objects.requireNonNull(XMaterial.WOODEN_SWORD.parseItem())))){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

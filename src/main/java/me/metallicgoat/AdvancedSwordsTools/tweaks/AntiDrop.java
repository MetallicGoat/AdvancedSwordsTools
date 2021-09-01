package me.metallicgoat.AdvancedSwordsTools.tweaks;

import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.arena.Arena;
import me.metallicgoat.AdvancedSwordsTools.Main;
import me.metallicgoat.AdvancedSwordsTools.utils.IgnoreItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.List;

public class AntiDrop implements Listener {
    @EventHandler
    public void onToolDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        Arena arena = BedwarsAPI.getGameAPI().getArenaByPlayer(p);
        // If player is trying to dop a tool he shouldn't, cancel event
        if(arena != null) {
            if (antiDropList().contains(e.getItemDrop().getItemStack().getType().name()) &&
                    antiDrop() &&
                    IgnoreItemStack.isNotToIgnore(e.getItemDrop().getItemStack())) {
                e.setCancelled(true);
            }
        }
    }
    private boolean antiDrop() {
        return Main.getConfigManager().getAntiDrop();
    }
    private List<String> antiDropList() {
        return Main.getConfigManager().getAntiDropList();
    }
}

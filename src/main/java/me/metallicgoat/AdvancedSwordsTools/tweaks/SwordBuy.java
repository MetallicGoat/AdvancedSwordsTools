package me.metallicgoat.AdvancedSwordsTools.tweaks;

import de.marcely.bedwars.api.event.player.PlayerBuyInShopEvent;
import de.marcely.bedwars.api.game.shop.product.ItemShopProduct;
import de.marcely.bedwars.api.game.shop.product.ShopProduct;
import me.metallicgoat.AdvancedSwordsTools.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SwordBuy implements Listener {
    @EventHandler
    public void onSwordBuy(PlayerBuyInShopEvent e){
        Main plugin = Main.getInstance();
        Player p = e.getPlayer();
        PlayerInventory pi = p.getInventory();
        if(e.getProblems().isEmpty() && replace()){
            // Checks if player bought a sword
            for(ShopProduct rawProduct:e.getItem().getProducts()){
                if(rawProduct instanceof ItemShopProduct){
                    final ItemStack[] is = ((ItemShopProduct) rawProduct).getItemStacks();
                    for(ItemStack item:is){
                        if(item.getType().name().endsWith("SWORD")){
                            //Clear Wooden Swords
                            if(allType()){
                                pi.forEach(itemStack -> {
                                    if(itemStack != null) {
                                        if (itemStack.getType().name().contains("SWORD")) {
                                            pi.remove(itemStack);
                                        }
                                    }
                                });
                            }else {
                                pi.remove(plugin.versions.getWoodSword());
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
    private boolean replace() {
        return Main.getConfigManager().getSwordReplace();
    }
    private boolean allType() {
        return Main.getConfigManager().getSwordReplaceType();
    }
}
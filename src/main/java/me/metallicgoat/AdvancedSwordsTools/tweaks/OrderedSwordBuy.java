package me.metallicgoat.AdvancedSwordsTools.tweaks;

import de.marcely.bedwars.api.event.player.PlayerBuyInShopEvent;
import de.marcely.bedwars.api.game.shop.product.ItemShopProduct;
import de.marcely.bedwars.api.game.shop.product.ShopProduct;
import me.metallicgoat.AdvancedSwordsTools.Main;
import me.metallicgoat.AdvancedSwordsTools.utils.IgnoreItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.atomic.AtomicBoolean;

public class OrderedSwordBuy implements Listener {
    @EventHandler
    public void onSwordBuy(PlayerBuyInShopEvent e){
        Player p = e.getPlayer();
        PlayerInventory pi = p.getInventory();
        if(swordBuy()) {
            for (ShopProduct rawProduct : e.getItem().getProducts()) {
                if (rawProduct instanceof ItemShopProduct) {
                    final ItemStack[] is = ((ItemShopProduct) rawProduct).getItemStacks();
                    for (ItemStack item : is) {
                        if (item.getType().name().contains("SWORD") && IgnoreItemStack.isNotToIgnore(item)) {
                            if (!isPurchasable(item, pi)) {
                                addShopProblem(e);
                            } else {
                                if (e.getProblems().isEmpty()) {
                                    clearOld(item.getType(), p);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void clearOld(Material tool, Player p){
        p.getInventory().forEach(itemStack -> {
            if(itemStack != null){
                if(getSwordLevel(tool.name()) > getSwordLevel(itemStack.getType().name())){
                    p.getInventory().remove(itemStack.getType());
                }
            }
        });
    }

    private boolean isPurchasable(ItemStack product, PlayerInventory pi){
        AtomicBoolean purchasable = new AtomicBoolean(true);

        if(pi.contains(product.getType())){
            return false;
        }
        pi.forEach(itemStack -> {
            if(itemStack != null) {
                if (itemStack.getType().name().contains("SWORD")) {
                    if (getSwordLevel(itemStack.getType().name()) > getSwordLevel(product.getType().name())) {
                        purchasable.set(false);
                    }
                }
            }
        });

        return purchasable.get();
    }

    private int getSwordLevel(String tool){
        if(tool.contains("WOOD")){
            return 1;
        }else if(tool.contains("STONE")){
            return 2;
        }else if(tool.contains("IRON")){
            return 3;
        }else if(tool.contains("GOLD")){
            return 4;
        }else if(tool.contains("DIAMOND")){
            return 5;
        }else if(tool.contains("NETHERITE")){
            return 6;
        }else{
            return 0;
        }
    }

    private void addShopProblem(PlayerBuyInShopEvent e){
        e.addProblem(new PlayerBuyInShopEvent.Problem() {
            @Override
            public Plugin getPlugin() {
                return Main.getInstance();
            }

            @Override
            public void handleNotification(PlayerBuyInShopEvent e) {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', getOrderedSwordBuyProblem()));
            }
        });
    }

    private boolean swordBuy() {
        return Main.getConfigManager().getOrderedSwordBuy();
    }

    private String getOrderedSwordBuyProblem() {
        return Main.getConfigManager().getOrderedSwordBuyProblem();
    }
}
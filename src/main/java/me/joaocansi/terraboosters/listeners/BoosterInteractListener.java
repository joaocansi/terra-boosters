package me.joaocansi.terraboosters.listeners;

import me.joaocansi.terraboosters.Main;
import me.joaocansi.terraboosters.entities.BoosterProduct;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BoosterInteractListener implements Listener {
    @EventHandler
    public void onBoosterInteractEvent(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        Player p = e.getPlayer();

        if (item == null)
            return;

        BoosterProduct boosterProduct = Main.getBoosterProductManager().getBoosterProductByItem(item);
        if (boosterProduct == null)
            return;

        e.setCancelled(true);
        if (Main.getBoosterManager().addBooster(p, boosterProduct))
            item.setAmount(item.getAmount() - 1);
    }
}

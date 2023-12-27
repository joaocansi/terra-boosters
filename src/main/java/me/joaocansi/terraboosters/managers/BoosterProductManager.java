package me.joaocansi.terraboosters.managers;

import lombok.Getter;
import lombok.Setter;
import me.joaocansi.terraboosters.Main;
import me.joaocansi.terraboosters.entities.Booster;
import me.joaocansi.terraboosters.entities.BoosterProduct;
import me.joaocansi.terraboosters.utils.console.Console;
import me.joaocansi.terraboosters.utils.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Objects;

@Getter @Setter
public class BoosterProductManager {
    private HashMap<String, BoosterProduct> boostersProducts;

    public BoosterProductManager() {
        boostersProducts = new HashMap<>();
        load();
    }

    public void load() {
        FileConfiguration config = Main.getPlugin().getConfig();
        ConfigurationSection section = config.getConfigurationSection("boosters");

        if (section == null) {
            Console.warning("'boosters' section not found in config.yml. 0 boosters loaded.");
            return;
        }

        for (String key : section.getKeys(false)) {
            String path = "boosters." + key;

            String name = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString(path + ".name")));
            String skill = config.getString(path + ".skill");
            float multiplication = (float)config.getDouble(path + ".multiplication");
            long duration = config.getLong(path + ".duration");
            ItemStack item = new ItemBuilder(path + ".item").toItemStack();

            BoosterProduct boosterProduct = new BoosterProduct(key, name, skill, item, duration, multiplication);
            boostersProducts.put(key, boosterProduct);
        }

        Console.info(boostersProducts.size() + " boosters loaded from config.yml");
    }

    public boolean giveBooster(CommandSender from, Player to, String boosterId, int amount) {
        if (to == null || !to.isOnline()) {
            Main.getMessageManager().getMessage("player_not_online").send(from, message -> message
                    .replace("{boosterId}", boosterId)
                    .replace("{boosterAmount}", String.valueOf(amount)));
            return false;
        }

        if (!boostersProducts.containsKey(boosterId)) {
            Main.getMessageManager().getMessage("booster_not_found").send(from, message -> message
                    .replace("{boosterId}", boosterId)
                    .replace("{boosterAmount}", String.valueOf(amount))
                    .replace("{receiverName}", to.getName()));
            return false;
        }

        BoosterProduct booster = boostersProducts.get(boosterId);
        ItemStack item = booster.getItem();
        item.setAmount(amount);

        int freeSpace = 0;
        int maxItemStack = item.getMaxStackSize();

        for (ItemStack inventoryItem : to.getInventory().getContents())
            if (inventoryItem == null)
                freeSpace += maxItemStack;
            else if (inventoryItem.isSimilar(item))
                freeSpace += maxItemStack - inventoryItem.getAmount();

        if (freeSpace < amount) {
            Main.getMessageManager().getMessage("player_inventory_full").send(from, message -> message
                    .replace("{boosterName}", booster.getName())
                    .replace("{boosterSkill}", booster.getSkill())
                    .replace("{boosterDuration}", String.valueOf(booster.getDuration()))
                    .replace("{boosterId}", boosterId)
                    .replace("{boosterAmount}", String.valueOf(amount))
                    .replace("{receiverName}", to.getName()));
            return false;
        }

        to.getInventory().addItem(item);
        Main.getMessageManager().getMessage("booster_gave").send(from, message -> message
                .replace("{boosterName}", booster.getName())
                .replace("{boosterSkill}", booster.getSkill())
                .replace("{boosterDuration}", String.valueOf(booster.getDuration()))
                .replace("{boosterId}", boosterId)
                .replace("{boosterAmount}", String.valueOf(amount))
                .replace("{receiverName}", to.getName()));
        return true;
    }

    public BoosterProduct getBoosterProductByItem(ItemStack item) {
        for (BoosterProduct boosterProduct : boostersProducts.values())
            if (boosterProduct.getItem().isSimilar(item))
                return boosterProduct;
        return null;
    }

    public float getBoosterMultiplicationById(String boosterId) {
        return boostersProducts.get(boosterId).getMultiplication();
    }

    public String getBoosterSkillById(String boosterId) {
        return boostersProducts.get(boosterId).getSkill();
    }
}

package me.joaocansi.terraboosters.managers;

import com.google.common.collect.HashBasedTable;
import lombok.Getter;
import lombok.Setter;
import me.joaocansi.terraboosters.Main;
import me.joaocansi.terraboosters.database.BoosterDatabase;
import me.joaocansi.terraboosters.entities.Booster;
import me.joaocansi.terraboosters.entities.BoosterProduct;
import me.joaocansi.terraboosters.utils.console.Console;
import me.joaocansi.terraboosters.utils.date.DateFormatter;
import me.joaocansi.terraboosters.utils.skill.SkillTranslator;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter @Setter
public class BoosterManager {
    private BoosterDatabase boosterDatabase;
    private HashBasedTable<String, String, Booster> boosters;

    public BoosterManager(BoosterDatabase boosterDatabase) {
        this.boosterDatabase = boosterDatabase;
        this.boosters = HashBasedTable.create();

        int deletedBoosters = 0;
        int foundBoosters = 0;

        for (Booster booster : Objects.requireNonNull(this.boosterDatabase.getAll())) {
            if (booster.hasExpired()) {
                deletedBoosters += 1;
                boosterDatabase.deleteById(booster.getId());
                continue;
            }

            foundBoosters += 1;
            String boosterSkill = Main.getBoosterProductManager().getBoosterSkillById(booster.getBoosterId());
            this.boosters.put(booster.getPlayerId(), boosterSkill, booster);
        }

        Console.info(deletedBoosters + " boosters expirados foram deletados.");
        Console.info(foundBoosters + " boosters em uso encontrados.");
    }

    public boolean addBooster(Player player, BoosterProduct boosterProduct) {
        String playerId = player.getUniqueId().toString();
        Booster newBooster = new Booster();

        newBooster.setBoosterId(boosterProduct.getId());
        Date expirationDate = new Date(new Date().getTime() + (boosterProduct.getDuration() * 1000));
        newBooster.setExpiresIn(expirationDate.getTime());
        newBooster.setPlayerId(playerId);

        Booster findBooster = boosters.get(playerId, boosterProduct.getSkill());
        if (findBooster != null) {
            Main.getMessageManager().getMessage("booster_already_in_use").send(player, message -> message
                    .replace("{boosterName}", boosterProduct.getName())
                    .replace("{boosterSkill}", SkillTranslator.translate(boosterProduct.getSkill()))
                    .replace("{boosterExpiration}", DateFormatter.format(expirationDate)));
            return false;
        }

        if (!boosterDatabase.insert(newBooster)) {
            Main.getMessageManager().getMessage("booster_consuming_error").send(player, message -> message
                    .replace("{boosterName}", boosterProduct.getName())
                    .replace("{boosterSkill}", SkillTranslator.translate(boosterProduct.getSkill()))
                    .replace("{boosterExpiration}", DateFormatter.format(expirationDate)));
            return false;
        }

        Main.getMessageManager().getMessage("booster_consuming_success").send(player, message -> message
                .replace("{boosterName}", boosterProduct.getName())
                .replace("{boosterSkill}", SkillTranslator.translate(boosterProduct.getSkill()))
                .replace("{boosterExpiration}", DateFormatter.format(expirationDate)));
        boosters.put(playerId, boosterProduct.getSkill(), newBooster);
        return true;
    }

    public boolean viewPlayerBoosters(Player player) {
        FileConfiguration config = Main.getPlugin().getConfig();
        String playerId = player.getUniqueId().toString();

        List<String> text = new ArrayList<>(config.getStringList("messages.booster_view.booster_list_header"));

        List<String> itemMessage = config.getStringList("messages.booster_view.booster_list_item");
        int count = 0;

        for (String key : boosters.row(playerId).keySet()) {
            Booster booster = boosters.get(playerId, key);
            if (booster == null || booster.hasExpired())
                continue;

            count++;
            BoosterProduct boosterProduct = Main.getBoosterProductManager().getBoosterProductById(booster.getBoosterId());
            text.addAll(itemMessage.stream().map(line -> line
                    .replace("{boosterName}", boosterProduct.getName())
                    .replace("{boosterSkill}", SkillTranslator.translate(boosterProduct.getSkill()))
                    .replace("{boosterExpiration}", DateFormatter.format(new Date(booster.getExpiresIn())))).collect(Collectors.toList()));
        }

        if (count == 0)
            text.addAll(config.getStringList("messages.booster_view.booster_list_empty"));
        text.addAll(config.getStringList("messages.booster_view.booster_list_footer"));

        for (String line : text)
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', line));

        return true;
    }

    public Booster getBoosterByPlayerSkill(Player player, String skill) {
        Booster booster = boosters.get(player.getUniqueId().toString(), skill);
        if (booster != null && booster.hasExpired())
            return null;

        return boosters.get(player.getUniqueId().toString(), skill);
    }
}
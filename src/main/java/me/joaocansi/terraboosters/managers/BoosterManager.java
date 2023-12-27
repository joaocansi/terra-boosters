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
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Objects;

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
            long currentDate = new Date().getTime();

            if (booster.getDuration() <= currentDate) {
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
        newBooster.setDuration(expirationDate.getTime());
        newBooster.setPlayerId(playerId);

        Booster findBooster = boosters.get(playerId, boosterProduct.getSkill());
        if (findBooster != null) {
            Main.getMessageManager().getMessage("booster_already_in_use").send(player, message -> message
                    .replace("{boosterName}", boosterProduct.getName())
                    .replace("{boosterSkill}", boosterProduct.getSkill())
                    .replace("{boosterExpiration}", expirationDate.toString()));
            return false;
        }

        if (!boosterDatabase.insert(newBooster)) {
            return false;
        }

        Main.getMessageManager().getMessage("booster_consumed").send(player, message -> message
                .replace("{boosterName}", boosterProduct.getName())
                .replace("{boosterSkill}", boosterProduct.getSkill())
                .replace("{boosterExpiration}", DateFormatter.format(expirationDate)));
        boosters.put(playerId, boosterProduct.getSkill(), newBooster);
        return true;
    }

    public boolean viewPlayerBoosters(Player player) {
        String playerId = player.getUniqueId().toString();
        return true;
    }

    public Booster getBoosterByPlayerSkill(Player player, String skill) {
        return boosters.get(player.getUniqueId().toString(), skill);
    }
}

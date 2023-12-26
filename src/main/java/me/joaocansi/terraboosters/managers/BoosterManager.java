package me.joaocansi.terraboosters.managers;

import com.google.common.collect.HashBasedTable;
import lombok.Getter;
import lombok.Setter;
import me.joaocansi.terraboosters.database.BoosterDatabase;
import me.joaocansi.terraboosters.entities.Booster;
import me.joaocansi.terraboosters.entities.BoosterProduct;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Map;

@Getter @Setter
public class BoosterManager {
    private BoosterDatabase boosterDatabase;
    private HashBasedTable<String, String, Booster> boosters;

    public BoosterManager(BoosterDatabase boosterDatabase) {
        this.boosterDatabase = boosterDatabase;
        this.boosters = HashBasedTable.create();
    }

    public boolean addBooster(Player player, BoosterProduct boosterProduct) {
        String playerId = player.getUniqueId().toString();
        Booster newBooster = new Booster();

        newBooster.setBoosterId(boosterProduct.getId());
        newBooster.setDuration(new Date().getTime() + (boosterProduct.getDuration() * 1000));
        newBooster.setPlayerId(playerId);

        if (boosters.get(playerId, boosterProduct.getSkill()) != null) {
            player.sendMessage("You already have a booster with the same skill in use.");
            return false;
        }

        if (!boosterDatabase.insertBooster(newBooster)) {
            player.sendMessage("Something wrong occured.");
            return false;
        }

        System.out.println(playerId + " " + boosterProduct.getSkill() + " " + newBooster.getDuration());

        boosters.put(playerId, boosterProduct.getSkill(), newBooster);
        player.sendMessage("Booster added successfully.");
        return true;
    }

    public boolean viewPlayerBoosters(Player player) {
        String playerId = player.getUniqueId().toString();
        Map<String, Booster> playerBoosters = boosters.row(playerId);

        player.sendMessage("Your boosters:");
        for (String skill : playerBoosters.keySet()) {
            Booster booster = playerBoosters.get(skill);
            Date expiresDate = new Date(booster.getDuration());
            player.sendMessage(" - " + skill + ": Working until " + expiresDate);
        }

        return true;
    }

    public Booster getBoosterByPlayerSkill(Player player, String skill) {
        return boosters.get(player.getUniqueId().toString(), skill);
    }
}
